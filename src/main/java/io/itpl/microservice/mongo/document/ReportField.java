package io.itpl.microservice.mongo.document;

public class ReportField {
    private String key;
    private String alias;
    private String caption;
    private String dataType;
    private String dateFormat;
    private boolean generateSum;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isGenerateSum() {
        return generateSum;
    }

    public void setGenerateSum(boolean generateSum) {
        this.generateSum = generateSum;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
