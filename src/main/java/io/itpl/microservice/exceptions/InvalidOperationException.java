package io.itpl.microservice.exceptions;

public class InvalidOperationException extends Exception{

    private int code;
    private String message;

    public InvalidOperationException(){
        this("Invalid Operation !!",-503);
    }
    public InvalidOperationException(String message){
        this(message,-503);
    }
    public InvalidOperationException(String message,int code){
        super(message);
        this.code = code;
        this.message = message;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
