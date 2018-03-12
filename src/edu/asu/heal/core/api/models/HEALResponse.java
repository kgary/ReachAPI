package edu.asu.heal.core.api.models;

import java.util.List;

public class HEALResponse {

    // Message Type Captions
    public static final String INFO_MESSAGE_TYPE = "info";
    public static final String SUCCESS_MESSAGE_TYPE = "success";
    public static final String WARNING_MESSAGE_TYPE = "warning";
    public static final String ERROR_MESSAGE_TYPE = "error";

    private int statusCode;
    private String message;
    private String messageType;
    private List data;

    public HEALResponse(int statusCode, String message, String messageType, List data){
        this.statusCode = statusCode;
        this.message = message;
        this.messageType = messageType;
        this.data = data;
    }

    // getters and setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    // custom functions
    public static HEALResponse getInfoMessage(int statusCode, String message, List data){
        return new HEALResponse(statusCode, message, INFO_MESSAGE_TYPE, data);
    }

    public static HEALResponse getSuccessMessage(int statusCode, String message, List data){
        return new HEALResponse(statusCode, message, SUCCESS_MESSAGE_TYPE, data);
    }

    public static HEALResponse getWarningMessage(int statusCode, String message, List data){
        return new HEALResponse(statusCode, message, WARNING_MESSAGE_TYPE, data);
    }

    public static HEALResponse getErrorMessage(int statusCode, String message, List data){
        return new HEALResponse(statusCode, message, ERROR_MESSAGE_TYPE, data);
    }

}