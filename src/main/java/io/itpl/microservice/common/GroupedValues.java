package io.itpl.microservice.common;

import java.util.List;

public class GroupedValues {
    private String name;
    private List<String> content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
