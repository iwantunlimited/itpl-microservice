package io.itpl.microservice.mongo;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class DataFilter {
    public static final String TYPE_LIST = "LIST";
    public static final String TYPE_SINGLE = "SINGLE";
    public static final String TYPE_BOOLEAN = "BOOLEAN";
    public static final String TYPE_RANGE = "RANGE";

    /**
     * i.e. LIST, SINGLE, BOOLEAN, RANGE
     */
    private String valueType;
    /**
     *   field name which will be used in Query or Match (i.e. Criteria.where("${key}") statement.
     *   For example, DataFilter represents a Department Object, then "departmentId"
     */
    private String key;
    /**
     *  i.e. "Departments", "Order Status" etc.
     */
    private String caption;
    /**
     *  List of Options with caption, id etc.
     *  This can produce -> Criteria.where("${key}").in([])
     */
    private List<FilterOption> options;
    /**
     *  In Case its just single value
     *  for example
     *  This can produce -> Criteria.where("${key}").is(option.id)
     */
    private FilterOption option;
    /**
     *  "true" or "false",
     *  This can produce -> Criteria.where("${key}").is(true)
     */
    private String booleanValue;
    /**
     *  number,
     *  The output Criteria can be -> Criteria.where("${key}").gte(minimumRange).lte(maximumRange)
     */
    private double minimumRange;
    private double maximumRange;
    private String className;
    private Map<String,Object> filters;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<FilterOption> getOptions() {
        return options;
    }

    public void setOptions(List<FilterOption> options) {
        this.options = options;
    }

    public FilterOption getOption() {
        return option;
    }

    public void setOption(FilterOption option) {
        this.option = option;
    }

    public String getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(String booleanValue) {
        this.booleanValue = booleanValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public double getMinimumRange() {
        return minimumRange;
    }

    public void setMinimumRange(double minimumRange) {
        this.minimumRange = minimumRange;
    }

    public double getMaximumRange() {
        return maximumRange;
    }

    public void setMaximumRange(double maximumRange) {
        this.maximumRange = maximumRange;
    }
}
