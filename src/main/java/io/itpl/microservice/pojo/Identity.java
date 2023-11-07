package io.itpl.microservice.pojo;

import org.springframework.data.mongodb.core.index.Indexed;

public class Identity {
    private String type;
    @Indexed private String number;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
