package io.itpl.microservice.exceptions;

public class ItemNotFoundException extends Exception {

    public ItemNotFoundException() {
        this("Requested Item Not Found", -404);
    }

    private int errorCode;

    public ItemNotFoundException(String message) {

        this("Requested Item ["+message+"] Not Found",-404);
    }

    public ItemNotFoundException(String message, int code) {

        super(message);
        this.errorCode = code;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
