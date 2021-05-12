package io.itpl.microservice.mongo.analytics;

public class Column {
    private int id;
    private String field;
    private String headerName;

    public static Column of(int index,String field,String headerName){
        Column column = new Column();
        column.setId(index);
        column.setField(field);
        column.setHeaderName(headerName);
        return column;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }
}
