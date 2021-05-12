package io.itpl.microservice.mongo.pipeline;

import io.itpl.microservice.common.NumericRange;
import io.itpl.microservice.utils.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public abstract class AbstractPipeline implements PipelineBuilder{
    private static final Logger logger = LoggerFactory.getLogger(AbstractPipeline.class);

    protected String elementId;
    protected Map<String,String> textFilters;
    protected Map<String, List<String>> listFilters;
    protected Map<String,Boolean> booleanFilters;
    protected Map<String, NumericRange> rangeFilters;
    protected String unwindId;
    protected String sortBy;
    protected int limit;
    protected String dateFilterKey;
    protected Date startDate;
    protected Date finishDate;

    protected boolean applyBooleanFilter;
    protected boolean applyListFilters;
    protected boolean applyDateFilter;
    protected boolean applyTextFilters;
    protected boolean applyRangeFilters;
    protected boolean applyUnwind;
    protected boolean applyLimit;
    protected boolean includeSum;
    protected boolean multipleSeries;
    protected boolean excludeDeleted;

    protected Class<?> collectionClass;
    protected Class<?> outputClass;

    protected List<AggregationOperation> pipeline;

    public List<AggregationOperation> init(){

        pipeline = new ArrayList<>();

        if(applyTextFilters){
            textFilters.keySet().stream().forEach(key->{
                String value = textFilters.get(key);
                pipeline.add(match(Criteria.where(key).is(value)));
                logger.trace("[AbstractPipeline:{}] Match Added: {}:{}",elementId,key,value);
            });
        }
        if(applyListFilters){
            listFilters.keySet().stream().forEach(key->{
                List<String> values = listFilters.get(key);
                pipeline.add(match(Criteria.where(key).in(values)));
                logger.trace("[AbstractPipeline:{}] Match Added: {}:{}",elementId,key,values);
            });
        }
        if(applyBooleanFilter){
            booleanFilters.keySet().stream().forEach(key->{
                Boolean value = booleanFilters.get(key);
                pipeline.add(match(Criteria.where(key).is(value)));
                logger.trace("[AbstractPipeline:{}] Match Added: {}:{}",elementId,key,value);
            });
        }
        if(applyRangeFilters){
            rangeFilters.keySet().stream().forEach(key->{
                NumericRange range = rangeFilters.get(key);
                pipeline.add(match(Criteria.where(key).gte(range.getMinimum()).lte(range.getMaximum())));
                logger.trace("[AbstractPipeline:{}] Match Added: {}:{}",elementId,key,range.getMinimum()+"-"+range.getMaximum());
            });
        }
        if(applyDateFilter){
            pipeline.add(match(Criteria.where(this.dateFilterKey).gte(this.startDate).lt(this.finishDate)));
            logger.trace("[AbstractPipeline:{}] Match Added: {}:{}",elementId,dateFilterKey,startDate+"-"+finishDate);
        }
        if(this.excludeDeleted){
            pipeline.add(match(Criteria.where("deleted").is(false)));
            logger.trace("[AbstractPipeline:{}] Match Added: deleted:false",elementId);
        }
        if(applyUnwind){
            pipeline.add((unwind(unwindId)));
            logger.trace("[AbstractPipeline:{}] Unwind Added: {}:",elementId,unwindId);
        }

        //
        return pipeline;

    }
    public Aggregation build(){
        pipeline.add(sort(CommonHelper.sortBy(sortBy)));
        logger.trace("[AbstractPipeline:{}] Sort Added: {}:",elementId,sortBy);
        if(applyLimit){
            pipeline.add(limit(limit));
            logger.trace("[AbstractPipeline:{}] Limit Added: {}:",elementId,limit);
        }
        return newAggregation(pipeline);
    }
    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public Map<String, String> getTextFilters() {
        return textFilters;
    }

    public void setTextFilters(Map<String, String> textFilters) {
        this.textFilters = textFilters;
    }

    public Map<String, List<String>> getListFilters() {
        return listFilters;
    }

    public void setListFilters(Map<String, List<String>> listFilters) {
        this.listFilters = listFilters;
    }

    public Map<String, Boolean> getBooleanFilters() {
        return booleanFilters;
    }

    public void setBooleanFilters(Map<String, Boolean> booleanFilters) {
        this.booleanFilters = booleanFilters;
    }

    public Map<String, NumericRange> getRangeFilters() {
        return rangeFilters;
    }

    public void setRangeFilters(Map<String, NumericRange> rangeFilters) {
        this.rangeFilters = rangeFilters;
    }

    public String getUnwindId() {
        return unwindId;
    }

    public void setUnwindId(String unwindId) {
        this.unwindId = unwindId;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getDateFilterKey() {
        return dateFilterKey;
    }

    public void setDateFilterKey(String dateFilterKey) {
        this.dateFilterKey = dateFilterKey;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public boolean isApplyBooleanFilter() {
        return applyBooleanFilter;
    }

    public void setApplyBooleanFilter(boolean applyBooleanFilter) {
        this.applyBooleanFilter = applyBooleanFilter;
    }

    public boolean isApplyListFilters() {
        return applyListFilters;
    }

    public void setApplyListFilters(boolean applyListFilters) {
        this.applyListFilters = applyListFilters;
    }

    public boolean isApplyDateFilter() {
        return applyDateFilter;
    }

    public void setApplyDateFilter(boolean applyDateFilter) {
        this.applyDateFilter = applyDateFilter;
    }

    public boolean isApplyTextFilters() {
        return applyTextFilters;
    }

    public void setApplyTextFilters(boolean applyTextFilters) {
        this.applyTextFilters = applyTextFilters;
    }

    public boolean isApplyRangeFilters() {
        return applyRangeFilters;
    }

    public void setApplyRangeFilters(boolean applyRangeFilters) {
        this.applyRangeFilters = applyRangeFilters;
    }

    public boolean isApplyUnwind() {
        return applyUnwind;
    }

    public void setApplyUnwind(boolean applyUnwind) {
        this.applyUnwind = applyUnwind;
    }

    public boolean isApplyLimit() {
        return applyLimit;
    }

    public void setApplyLimit(boolean applyLimit) {
        this.applyLimit = applyLimit;
    }

    public boolean isIncludeSum() {
        return includeSum;
    }

    public void setIncludeSum(boolean includeSum) {
        this.includeSum = includeSum;
    }

    public boolean isMultipleSeries() {
        return multipleSeries;
    }

    public void setMultipleSeries(boolean multipleSeries) {
        this.multipleSeries = multipleSeries;
    }

    public boolean isExcludeDeleted() {
        return excludeDeleted;
    }

    public void setExcludeDeleted(boolean excludeDeleted) {
        this.excludeDeleted = excludeDeleted;
    }

    public Class<?> getCollectionClass() {
        return collectionClass;
    }

    public void setCollectionClass(Class<?> collectionClass) {
        this.collectionClass = collectionClass;
    }

    public Class<?> getOutputClass() {
        return outputClass;
    }

    public void setOutputClass(Class<?> outputClass) {
        this.outputClass = outputClass;
    }
}
