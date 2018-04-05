package edu.asu.heal.core.api.models;

import java.util.List;

public class HEALResponse{

    // Message Type Captions
    public static final String INFO_MESSAGE_TYPE = "info";
    public static final String SUCCESS_MESSAGE_TYPE = "success";
    public static final String WARNING_MESSAGE_TYPE = "warning";
    public static final String ERROR_MESSAGE_TYPE = "error";

    private int statusCode;
    private String message;
    private String messageType;
    private Object data;

    private HEALResponse(){}

    // getters and setters
    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageType() {
        return messageType;
    }

    public Object getData() {
        return data;
    }

    // custom functions
    public static HEALResponse getInfoMessage(int statusCode, String message, List data){
//        return new HEALResponse(statusCode, message, INFO_MESSAGE_TYPE, data);
        return null;
    }

    public static HEALResponse getSuccessMessage(int statusCode, String message, List data){
//        return new HEALResponse(statusCode, message, SUCCESS_MESSAGE_TYPE, data);
        return null;
    }

    public static HEALResponse getWarningMessage(int statusCode, String message, List data){
//        return new HEALResponse(statusCode, message, WARNING_MESSAGE_TYPE, data);
        return null;
    }

    public static HEALResponse getErrorMessage(int statusCode, String message, List data){
//        return new HEALResponse(statusCode, message, ERROR_MESSAGE_TYPE, data);
        return null;
    }

    public static class HEALResponseBuilder {
        private HEALResponse _response;

        public HEALResponseBuilder(){
                _response = new HEALResponse();
        }

        public HEALResponseBuilder setStatusCode(int statusCode) {
            this._response.statusCode = statusCode;
            return this;
        }

        public HEALResponseBuilder setMessage(String message) {
            this._response.message = message;
            return this;
        }

        public HEALResponseBuilder setData(Object data) {
            this._response.data = data;
            return this;
        }

        public HEALResponseBuilder setMessageType(String messageType) {
            this._response.messageType = messageType;
            return this;
        }

        public HEALResponse build(){
            return this._response;
        }
    }
}