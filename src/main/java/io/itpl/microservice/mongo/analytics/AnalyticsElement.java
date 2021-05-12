package io.itpl.microservice.mongo.analytics;

import com.google.common.base.Strings;
import io.itpl.microservice.common.*;
import io.itpl.microservice.exceptions.InvalidInputException;
import io.itpl.microservice.mongo.DataFilter;
import io.itpl.microservice.mongo.document.AggregationDocument;
import io.itpl.microservice.mongo.document.ReportField;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document("analytics-element")
public class AnalyticsElement {


    @Id private String id;
    /**
     *  domain will be autoPopulated by the Action Class.
     */
    @Transient private String domain;

    private String alias;
    private boolean systemGenerated;
    private Date createdOn;
    private String realm;
    private boolean deleted;
    private boolean inactive;

    /**
     *  to be used in future when filters are saved in db.
     *  or this information can be used to send email with content.
     */
    private UserObject userId;
    /**
     *  X- Axis or Main Axis Data Labels
     */
    private List<String> labels;
    /**
     *  Y-Axis values for each Data Series
     */
    private List<DataSeries> dataSeries;
    /**
     *  Api will return row documents when tableView is true;
     */

    private List<Map<String,Object>> rows;
    private List<Column> columns;
    /**
     * i.e "createdOn"
     */
    private String dateFilterId;
    @Transient private Date startDate;
    @Transient private Date finishDate;

    /**
     *  i.e. Last 30 days.
     *  if == 0 means All records.
     *  if == -1 : date filter not
     */
    private int defaultPreviousDuration;

    private List<DataFilter> filters;
    private List<DocumentSelector> documentSelectors;

    /**
     *  i.e. orderedOnDate, createdOnDate etc.
     */
    private String primarySeriesId;
    private String primarySeriesDataType;
    private String primarySeriesFormat;
    /**
     *  i.e. "Transaction Date"
     */
    private String primarySeriesCaption;
    /**
     *  i.e. DeliveryModeId
     */
    private String secondarySeriesId;
    /**
     *  i.e. "Delivery Mode"
     */
    private String secondarySeriesCaption;

    /**
     *  i.e. field name which holds a number value to sum
     *  For example "netAmount" in case of Order
     */
    private String unwindId;
    /**
     *  i.e. netAmount, quantity
     */
    private String accumulatorId;
    /**
     *  i.e. Top 20 or 10 or etc.
     */
    private int limit;
    /**
     *  "i.e. "count"
     *  if order amount : "amount"
     */
    private String sortBy;

    @Indexed private String title;
    /**
     * i.e. Total Customers
     */
    private int primaryCount;
    /**
     *  i.e.
     *  {
     *       "Active" : ${count},
     *       "Inactive" :${count}
     *  }
     */
    private Map<String,Integer> counters;


    private String serviceId;
    private String executorClassName;
    private String className;

    private int minimumCount;
    private int maximumCount;
    private double averageCount;
    private double minimumValue;
    private double maximumValue;
    private double averageValue;
    private int totalCount;
    private double totalValue;
    /**
     *  i.e. Bar Chart, Line Chart etc.
     *  Exact value to be defined in the App
     *  and then to be used here.
     */
    private String outputFormat;

    @Transient
    String errorMessage;
    @Transient boolean error;

    private List<ReportField> reportFields;
    private boolean report;
    @Transient private String timeZoneId;
    @Transient int pageSize;
    @Transient int page;
    public void validate(){
        if(Strings.isNullOrEmpty(this.className)){
            throw new InvalidInputException("Missing className");
        }
        if(Strings.isNullOrEmpty(this.serviceId)){
            throw new InvalidInputException("Missing ServiceId");
        }
        if(!Strings.isNullOrEmpty(dateFilterId) && this.startDate== null && this.finishDate==null){
            if(defaultPreviousDuration >= 0){
                if(defaultPreviousDuration==0){
                    defaultPreviousDuration = 30;
                }
                Calendar timer = Calendar.getInstance();
                this.finishDate = new Date(timer.getTimeInMillis());
                timer.add(Calendar.DAY_OF_YEAR,~defaultPreviousDuration);
                this.startDate = new Date(timer.getTimeInMillis());
            }
        }
        if(this.reportFields != null && !this.reportFields.isEmpty()){
            this.setReport(true);
        }else{
            if(!Strings.isNullOrEmpty(this.primarySeriesId)){
                this.setReport(false);
            }
        }
        if(!this.isReport()){
            /**
             *  Validation for the CrossTab.
             */
            if(Strings.isNullOrEmpty(this.primarySeriesId)){
                throw new InvalidInputException("primarySeriesId is missing");
            }

            if(Strings.isNullOrEmpty(primarySeriesDataType)){
                primarySeriesDataType = "string";
            }

            if(Strings.isNullOrEmpty(primarySeriesDataType)){
                primarySeriesDataType = "String";
            }else{
                if(primarySeriesDataType.equalsIgnoreCase("date")){
                    if(Strings.isNullOrEmpty(primarySeriesFormat)){
                        primarySeriesFormat = AggregationDocument.DATE_FORMAT_DEFAULT;
                    }
                }else{
                    primarySeriesFormat = "Default";
                }
            }
        }else{
            /**
             *  Validation for Reports.
             */
            if(this.reportFields==null || this.reportFields.isEmpty()){
                throw new InvalidInputException("Report does not have any fields");
            }else{
                for(ReportField field:reportFields){
                    String key = field.getKey();
                    String caption = field.getCaption();
                    String dataType = field.getDataType();
                    String dateFormat = field.getDateFormat();
                    String fieldAlias = field.getAlias();
                    if(Strings.isNullOrEmpty(key)){
                        throw new InvalidInputException("Invalid Report Field, missing key");
                    }
                    if(Strings.isNullOrEmpty(fieldAlias)){
                        field.setAlias(key);
                    }
                    if(Strings.isNullOrEmpty(caption)){
                        field.setCaption(key);
                    }
                    if(Strings.isNullOrEmpty(dataType)){
                        field.setDataType("string");
                    }
                    if(Strings.isNullOrEmpty(dateFormat)){
                        field.setDateFormat(AggregationDocument.DATE_FORMAT_DEFAULT);
                    }
                }
            }
        }

    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<DataSeries> getDataSeries() {
        return dataSeries;
    }

    public void setDataSeries(List<DataSeries> dataSeries) {
        this.dataSeries = dataSeries;
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

    public List<DataFilter>  getFilters() {
        return filters;
    }

    public void setFilters(List<DataFilter>  filters) {
        this.filters = filters;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getPrimaryCount() {
        return primaryCount;
    }

    public void setPrimaryCount(int primaryCount) {
        this.primaryCount = primaryCount;
    }

    public Map<String, Integer> getCounters() {
        return counters;
    }

    public void setCounters(Map<String, Integer> counters) {
        this.counters = counters;
    }

    public String getPrimarySeriesId() {
        return primarySeriesId;
    }

    public void setPrimarySeriesId(String primarySeriesId) {
        this.primarySeriesId = primarySeriesId;
    }

    public String getPrimarySeriesCaption() {
        return primarySeriesCaption;
    }

    public void setPrimarySeriesCaption(String primarySeriesCaption) {
        this.primarySeriesCaption = primarySeriesCaption;
    }

    public String getSecondarySeriesId() {
        return secondarySeriesId;
    }

    public void setSecondarySeriesId(String secondarySeriesId) {
        this.secondarySeriesId = secondarySeriesId;
    }

    public String getSecondarySeriesCaption() {
        return secondarySeriesCaption;
    }

    public void setSecondarySeriesCaption(String secondarySeriesCaption) {
        this.secondarySeriesCaption = secondarySeriesCaption;
    }

    public String getAccumulatorId() {
        return accumulatorId;
    }

    public void setAccumulatorId(String accumulatorId) {
        this.accumulatorId = accumulatorId;
    }

    public UserObject getUserId() {
        return userId;
    }

    public void setUserId(UserObject userId) {
        this.userId = userId;
    }



    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }



    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getUnwindId() {
        return unwindId;
    }

    public void setUnwindId(String unwindId) {
        this.unwindId = unwindId;
    }

    public int getMinimumCount() {
        return minimumCount;
    }

    public void setMinimumCount(int minimumCount) {
        this.minimumCount = minimumCount;
    }

    public int getMaximumCount() {
        return maximumCount;
    }

    public void setMaximumCount(int maximumCount) {
        this.maximumCount = maximumCount;
    }

    public double getAverageCount() {
        return averageCount;
    }

    public void setAverageCount(double averageCount) {
        this.averageCount = averageCount;
    }

    public double getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(double minimumValue) {
        this.minimumValue = minimumValue;
    }

    public double getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(double maximumValue) {
        this.maximumValue = maximumValue;
    }

    public double getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(double averageValue) {
        this.averageValue = averageValue;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }


    public String getDateFilterId() {
        return dateFilterId;
    }

    public void setDateFilterId(String dateFilterId) {
        this.dateFilterId = dateFilterId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getExecutorClassName() {
        return executorClassName;
    }

    public void setExecutorClassName(String executorClassName) {
        this.executorClassName = executorClassName;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<DocumentSelector> getDocumentSelectors() {
        return documentSelectors;
    }

    public void setDocumentSelectors(List<DocumentSelector> documentSelectors) {
        this.documentSelectors = documentSelectors;
    }

    @Override
    public String toString() {
        return "AnalyticsElement{" +
                "primarySeriesId='" + primarySeriesId + '\'' +
                ", secondarySeriesId='" + secondarySeriesId + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", className='" + className + '\'' +
                '}';
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isSystemGenerated() {
        return systemGenerated;
    }

    public void setSystemGenerated(boolean systemGenerated) {
        this.systemGenerated = systemGenerated;
    }

    public int getDefaultPreviousDuration() {
        return defaultPreviousDuration;
    }

    public void setDefaultPreviousDuration(int defaultPreviousDuration) {
        this.defaultPreviousDuration = defaultPreviousDuration;
    }

    public String getPrimarySeriesDataType() {
        return primarySeriesDataType;
    }

    public void setPrimarySeriesDataType(String primarySeriesDataType) {
        this.primarySeriesDataType = primarySeriesDataType;
    }

    public String getPrimarySeriesFormat() {
        return primarySeriesFormat;
    }

    public void setPrimarySeriesFormat(String primarySeriesFormat) {
        this.primarySeriesFormat = primarySeriesFormat;
    }

    public List<ReportField> getReportFields() {
        return reportFields;
    }

    public void setReportFields(List<ReportField> reportFields) {
        this.reportFields = reportFields;
    }

    public boolean isReport() {
        return report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
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
}
