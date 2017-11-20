package com.logistimo.social.core.exception;

/**
 * Created by kumargaurav on 17/07/17.
 */
public class ErrorResource {

    private String message;

    private String code;

    private int statusCode;

    public ErrorResource(String message, String code, int statusCode) {
        this.message = message;
        this.code = code;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
