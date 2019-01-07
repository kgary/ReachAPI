package edu.asu.heal.core.api.models;

import javax.annotation.Generated;

@SuppressWarnings("unused")
public class NotificationRequestModel implements IHealModelType {

    private NotificationData data;
    private String to;

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}