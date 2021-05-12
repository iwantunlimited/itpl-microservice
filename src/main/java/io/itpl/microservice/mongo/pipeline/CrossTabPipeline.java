package io.itpl.microservice.mongo.pipeline;

import com.google.common.base.Strings;
import io.itpl.microservice.common.NumericRange;
import io.itpl.microservice.mongo.DataFilter;
import io.itpl.microservice.mongo.FilterOption;
import io.itpl.microservice.mongo.analytics.AnalyticsElement;
import io.itpl.microservice.mongo.analytics.DocumentSelector;
import io.itpl.microservice.mongo.document.AggregationDocument;
import io.itpl.microservice.utils.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 *
 *  _id: The id of the group.
 *   fieldN: The first field name.
 *
 {
         _id:"$orderedOnDate",
         "deliveryModes":{$addToSet:"$deliveryModeName"},
         "count":{$sum:1},
         "amount":{$sum:"$netAmount"}


 }


 {
     _id:{
        "orderDate" :"$orderedOnDate",
        "deliveryModes" : "$deliveryModeName"
     },
     "count" : {$sum : 1},
     "amount" : {$sum : "$netAmount"}
 }
 */
public class CrossTabPipeline extends AbstractPipeline implements PipelineBuilder{
    private static final Logger logger = LoggerFactory.getLogger(CrossTabPipeline.class);

    /**
     *  i.e.
     */
    private String primarySeriesKey;
    private String secondarySeriesKey;
    private String sumKey;
    private String dateFormat;
    private String primarySeriesDataType;

    public boolean isValid(){
        if(!Strings.isNullOrEmpty(this.dateFilterKey) && this.startDate!=null && this.finishDate!=null){
            this.applyDateFilter = true;
            super.setApplyDateFilter(true);
        }
        if(textFilters!= null && !textFilters.isEmpty()){
            this.applyTextFilters = true;
        }
        if(!Strings.isNullOrEmpty(sumKey)){
            this.includeSum = true;
        }
        if(!Strings.isNullOrEmpty(this.secondarySeriesKey)){
            this.multipleSeries = true;

        }else{
            this.multipleSeries = false;

        }
        if(this.listFilters != null && !this.listFilters.isEmpty()){
            this.applyListFilters = true;
        }
        if(this.booleanFilters != null && !this.booleanFilters.isEmpty()){
            this.applyBooleanFilter = true;
        }
        if(this.rangeFilters != null && !this.rangeFilters.isEmpty()){
            this.applyBooleanFilter = true;
        }
        if(!Strings.isNullOrEmpty(this.unwindId)){
            applyUnwind = true;
        }
        if(Strings.isNullOrEmpty(this.sortBy)){
            sortBy = this.multipleSeries? "_id."+primarySeriesKey:"_id";
        }
        if(this.limit > 0){
            applyLimit = true;
        }
        if(this.collectionClass == null){
            return false;
        }
        if(Strings.isNullOrEmpty(primarySeriesDataType)){
            primarySeriesDataType = "string";
        }
        if(Strings.isNullOrEmpty(dateFormat) ){
            this.dateFormat = AggregationDocument.DATE_FORMAT_DEFAULT;
        }
        return true;
    }
    public static CrossTabPipeline of(AnalyticsElement req){
        CrossTabPipeline pipeline = new CrossTabPipeline();


        Map<String,String> textFilters = new HashMap<>();
        Map<String, List<String>> listFilters = new HashMap<>();
        Map<String,Boolean> booleanFilters = new HashMap<>();
        Map<String, NumericRange> rangeFilters = new HashMap<>();
        textFilters.put("domain",req.getDomain());
        pipeline.setElementId(req.getId());
        String primarySeriesDataType = req.getPrimarySeriesDataType();
        String primarySeriesFormat = req.getPrimarySeriesFormat();
        pipeline.setPrimarySeriesDataType(primarySeriesDataType);

        if(primarySeriesDataType.equalsIgnoreCase("date")){
            if(Strings.isNullOrEmpty(primarySeriesFormat)){
                primarySeriesFormat = AggregationDocument.DATE_FORMAT_DEFAULT;
            }
            pipeline.setDateFormat(primarySeriesFormat);
        }
        List<DocumentSelector> documentSelectors = req.getDocumentSelectors();
        if(documentSelectors!=null && !documentSelectors.isEmpty()){
            documentSelectors.forEach(validator->{
                if(validator.getValue() != null) {
                    booleanFilters.put(validator.getKey(), validator.getValue());
                }else{
                    if(!Strings.isNullOrEmpty(validator.getTextValue())){
                        textFilters.put(validator.getKey(),validator.getTextValue());
                    }else{
                        if(!Strings.isNullOrEmpty(validator.getNumberValue()) && CommonHelper.isInteger(validator.getNumberValue())){
                            textFilters.put(validator.getKey(),validator.getNumberValue());
                        }
                    }
                }
            });
        }
        /**
         *  Load All filters
         *  1. textFilters
         *  2. listFilters
         *  3. booleanFilters (skipped for now..)
         */
        List<DataFilter> filters = req.getFilters();
        if(filters != null && !filters.isEmpty()){
            filters.forEach(filter->{
                DataFilter filterGroup = filter;
                String valueType = filterGroup.getValueType();
                String fieldName = filterGroup.getKey();
                FilterOption option = filterGroup.getOption();
                List<FilterOption> options = filterGroup.getOptions();
                String booleanValue = filterGroup.getBooleanValue();
                double minimum = filterGroup.getMinimumRange();
                double maximum = filterGroup.getMaximumRange();

                switch (valueType.toUpperCase()){
                    case "LIST":
                        if(options!=null && !options.isEmpty()){
                            List<String> values = new ArrayList<>();
                            options.forEach(item-> {
                                if(item.isSelected()) {
                                    values.add(item.getId());
                                }
                            });
                            listFilters.put(fieldName,values);
                        }
                        break;
                    case "BOOLEAN":
                        if(!Strings.isNullOrEmpty(booleanValue)){
                            booleanFilters.put(fieldName,Boolean.valueOf(booleanValue));
                        }
                        break;
                    case DataFilter.TYPE_RANGE:
                        if(minimum > 0 && maximum > minimum){
                            rangeFilters.put(fieldName,NumericRange.of(minimum,maximum));
                        }
                    case "SINGLE":
                    default:
                        if(option != null && option.isSelected()){
                            textFilters.put(fieldName,option.getId());
                        }
                }

            });

        }//End of Filters

        pipeline.setPrimarySeriesKey(req.getPrimarySeriesId());
        pipeline.setTextFilters(textFilters);
        pipeline.setListFilters(listFilters);
        pipeline.setBooleanFilters(booleanFilters);
        pipeline.setRangeFilters(rangeFilters);

        if(!Strings.isNullOrEmpty(req.getSecondarySeriesId())){
            pipeline.setSecondarySeriesKey(req.getSecondarySeriesId());
        }
        Date startDate = req.getStartDate();
        Date finishDate = req.getFinishDate();
        String dateFilterId = req.getDateFilterId();
        if(startDate!=null && finishDate!=null){
            pipeline.setStartDate(startDate);
            pipeline.setFinishDate(finishDate);
            pipeline.setDateFilterKey(dateFilterId);
        }
        String accumulatorId = req.getAccumulatorId();
        if(!Strings.isNullOrEmpty(accumulatorId)){
            pipeline.setSumKey(accumulatorId);
        }
        String unwindId = req.getUnwindId();
        if(!Strings.isNullOrEmpty(unwindId)){
            pipeline.setUnwindId(unwindId);
        }
        String sortBy = req.getSortBy();
        if(!Strings.isNullOrEmpty(sortBy)){
            pipeline.setSortBy(sortBy);
        }
        int limit = req.getLimit();
        if(limit>0){
            pipeline.setLimit(limit);
        }

        return pipeline;
    }
    public Aggregation build(){

        super.init();
        /**
         *  We have initial Pipeline with All Basic Match Clauses.
         *  Now we will append Group By Clause.
         */
        pipeline.add(match(Criteria.where(this.primarySeriesKey).exists(true)));
        logger.trace("[CrossTabPipeline:{}] Match [$exists] Added : {}:true",elementId,primarySeriesKey);
        if(!Strings.isNullOrEmpty(this.secondarySeriesKey)){
            pipeline.add(match(Criteria.where(this.secondarySeriesKey).exists(true)));
            logger.trace("[CrossTabPipeline:{}] Match [$exists] Added : {}:true",elementId,secondarySeriesKey);
        }
        /**
         *  Project Phase : Before the group by.
         *  This can be used to reformat the fields mainly trimming date or converting date to ISO date or month or year only.
         *  For the rest of the fields there won't be any transformation.
         *  We are not applying any login even on the secondary series.
         */
        AggregationOperation projectStage = null;
        logger.trace("[{}]- primarySeriesDataType : {},format:{}",primarySeriesKey,primarySeriesDataType,dateFormat);
        switch (this.primarySeriesDataType.toUpperCase()){
            case "DATE":
                /**
                 *  dateExpression has not been used and it has been kept just for future reference.
                 *  Since it was tested successfully, do not want to remove the tested snippet.
                 *  It may be useful in some other future cases.
                 *
                 *  AggregationExpression dateExpression = DateOperators.DateToString.dateOf(primarySeriesKey).toString(MONTH_YEAR);
                 *
                 */

                if(Strings.isNullOrEmpty(this.sumKey)){
                    // sumKey not exists
                    if(!Strings.isNullOrEmpty(this.secondarySeriesKey) ){
                        projectStage = project()
                                .and(primarySeriesKey).dateAsFormattedString(dateFormat).as(primarySeriesKey)
                                .and(secondarySeriesKey).as(secondarySeriesKey);

                    }else{
                        projectStage = project()
                                .and(primarySeriesKey).dateAsFormattedString(dateFormat).as(primarySeriesKey);
                    }
                }else{
                    // sumKey exists - means need to include it in project.
                    logger.trace("Included sumKey:{} in Project Phase",sumKey);
                    if(!Strings.isNullOrEmpty(this.secondarySeriesKey) ){
                        projectStage = project()
                                .and(primarySeriesKey).dateAsFormattedString(dateFormat).as(primarySeriesKey)
                                .and(secondarySeriesKey).as(secondarySeriesKey)
                                .and(sumKey).as(sumKey);

                    }else{
                        projectStage = project()
                                .and(primarySeriesKey).dateAsFormattedString(dateFormat).as(primarySeriesKey)
                                .and(sumKey).as(sumKey);
                    }
                }

                break;
            default:
                /**
                 *  We assume to continue with default format of the primary and secondary series field.
                 *  Just include them in project phase.
                 */
                if(Strings.isNullOrEmpty(this.sumKey)){
                    // sumKey not exists
                    if(!Strings.isNullOrEmpty(this.secondarySeriesKey)){
                        projectStage = project()
                                .and(primarySeriesKey).as(primarySeriesKey)
                                .and(secondarySeriesKey).as(secondarySeriesKey);

                    }else{
                        projectStage = project()
                                .and(primarySeriesKey).as(primarySeriesKey);
                    }
                }else{
                    // sumKey exists - means need to include it in project.
                    logger.trace("Included sumKey:{} in Project Phase",sumKey);
                    if(!Strings.isNullOrEmpty(this.secondarySeriesKey)){
                        projectStage = project()
                                .and(primarySeriesKey).as(primarySeriesKey)
                                .and(secondarySeriesKey).as(secondarySeriesKey)
                                .and(sumKey).as(sumKey);

                    }else{
                        projectStage = project()
                                .and(primarySeriesKey).as(primarySeriesKey)
                                .and(sumKey).as(sumKey);
                    }
                }

        }
        pipeline.add(projectStage);
        /**
         *  Group By Stage.
         */
        Fields fields = Aggregation.bind(primarySeriesKey,"$"+this.primarySeriesKey);

        if(!Strings.isNullOrEmpty(this.secondarySeriesKey)){
            fields = fields.and((Aggregation.bind(secondarySeriesKey,"$"+this.secondarySeriesKey)));

        }

        if(includeSum){
            pipeline.add(group(fields).count().as(AggregationDocument.PROPERTY_COUNT).sum(this.sumKey).as(AggregationDocument.PROPERTY_AMOUNT));
        }else{
            pipeline.add(group(fields).count().as(AggregationDocument.PROPERTY_COUNT));
        }

        return super.build();
    }

    public String getPrimarySeriesKey() {
        return primarySeriesKey;
    }

    public void setPrimarySeriesKey(String primarySeriesKey) {
        this.primarySeriesKey = primarySeriesKey;
    }

    public String getSecondarySeriesKey() {
        return secondarySeriesKey;
    }

    public void setSecondarySeriesKey(String secondarySeriesKey) {
        this.secondarySeriesKey = secondarySeriesKey;
    }

    public String getSumKey() {
        return sumKey;
    }

    public void setSumKey(String sumKey) {
        this.sumKey = sumKey;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getPrimarySeriesDataType() {
        return primarySeriesDataType;
    }

    public void setPrimarySeriesDataType(String primarySeriesDataType) {
        this.primarySeriesDataType = primarySeriesDataType;
    }
}
