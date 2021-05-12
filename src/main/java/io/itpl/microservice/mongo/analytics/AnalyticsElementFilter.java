package io.itpl.microservice.mongo.analytics;

import com.google.common.base.Strings;
import io.itpl.microservice.filters.BasicPageFilter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


public class AnalyticsElementFilter extends BasicPageFilter {

    private String className;
    private String serviceId;
    private String outputFormat;
    private String report;


    @Override
    public Query toQuery(){
        Query query = super.toQuery();

        if(!Strings.isNullOrEmpty(className)){
            query.addCriteria(Criteria.where("className").is(className));
        }
        if(!Strings.isNullOrEmpty(serviceId)){
            query.addCriteria(Criteria.where("serviceId").is(serviceId));
        }
        if(!Strings.isNullOrEmpty(outputFormat)){
            query.addCriteria(Criteria.where("outputFormat").is(outputFormat));
        }
        if(!Strings.isNullOrEmpty(report)){
            query.addCriteria(Criteria.where("report").is(Boolean.valueOf(report)));
        }
        return query;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }


}
