package io.itpl.microservice.common;

import java.util.List;

public class KeyValueGroup {
    private String name;
    private List<KeyValuePair> content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<KeyValuePair> getContent() {
        return content;
    }

    public void setContent(List<KeyValuePair> content) {
        this.content = content;
    }
}
