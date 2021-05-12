package io.itpl.microservice.mongo.pipeline;

import com.google.common.base.Strings;
import io.itpl.microservice.mongo.analytics.AnalyticsElement;
import io.itpl.microservice.mongo.document.AggregationDocument;
import io.itpl.microservice.mongo.document.ReportField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;

import java.util.List;
import java.util.TimeZone;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class TabularPipeline extends AbstractPipeline implements PipelineBuilder{
    private static final Logger logger = LoggerFactory.getLogger(TabularPipeline.class);
    /**
     *  {
     *      "property" : "Caption"
     *  }
     */
    private List<ReportField> fields;
    private String timeZoneId;
    private boolean error;
    private String errorMessage;
    private int pageSize;
    private int page;

    public static TabularPipeline of(AnalyticsElement req){
        TabularPipeline pipeline = new TabularPipeline();
        if(!req.isReport()){
            pipeline.setError(true);
            pipeline.setErrorMessage("Requested Element is not report");
        }
        List<ReportField> fields = req.getReportFields();
        if(fields==null || fields.isEmpty()){
            pipeline.setError(true);
            pipeline.setErrorMessage("Requested Report Element Does not have any fields");
        }
        pipeline.setFields(fields);
        pipeline.setSortBy("id");
        pipeline.setTimeZoneId(req.getTimeZoneId());
        pipeline.setElementId(req.getId());
        return pipeline;
    }

    @Override
    public Aggregation build() {
        if(Strings.isNullOrEmpty(timeZoneId)){
            timeZoneId = TimeZone.getDefault().getID();
            logger.trace("Default Timezone [{}] added:",timeZoneId);
        }

        super.init();
        ProjectionOperation projectStage = project().and("id").as("id");
        for(ReportField field:fields){
            String type = field.getDataType();
            String key = field.getKey();
            String format = field.getDateFormat();
            if(format==null){
                format = AggregationDocument.DATE_FORMAT_DEFAULT;
            }
            if(Strings.isNullOrEmpty(type)){
                type = "string";
            }
            switch (type.toUpperCase()){
                case "DATE":
                    boolean isLocale = isLocaleId(timeZoneId);
                    AggregationExpression dateExpression;
                    if(isLocale){
                        dateExpression = DateOperators.DateToString.dateOf(key).toString(format);
                        logger.trace("Pipeline - Project Stage, timezoneId {} is invalid",timeZoneId);
                    }else{
                        dateExpression = DateOperators.DateToString.dateOf(key).toString(format).withTimezone(DateOperators.Timezone.valueOf(timeZoneId));
                        logger.trace("Pipeline - Project with timeZone -{}",timeZoneId);
                    }
                    projectStage = projectStage.and(dateExpression).as(key);
                    break;
                default:
                    projectStage = projectStage.and("$"+field.getKey()).as(field.getAlias());
            }

        }
        if(this.pageSize>0){
            pipeline.add(skip((long)pageSize*page));
            pipeline.add(limit((long)pageSize));
        }

        pipeline.add(projectStage);
        return super.build();
    }

    public List<ReportField> getFields() {
        return fields;
    }

    public void setFields(List<ReportField> fields) {
        this.fields = fields;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isLocaleId(String timeZoneId){
        if(Strings.isNullOrEmpty(timeZoneId))
            return false;
        boolean _exists = timeZoneId.contains("_");
        int length = timeZoneId.length();
        if(_exists && length==5){
            //i.e. "en_IN"
            String []tokens = timeZoneId.split("_");
            int tokensCount = tokens.length;
            if(tokensCount==2){
                int part1 = tokens[0].length();
                int part2 = tokens[1].length();
                if(part1 == 2 && part2 == 2){
                    return true;
                }
            }
        }
        // i.e. "eng"
        if(!_exists && length==3){
            return true;
        }
        return false;
    }
}
