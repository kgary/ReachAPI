package edu.asu.heal.core.api.models;

import javax.annotation.Generated;

@SuppressWarnings("unused")
public class NotificationData implements IHealModelType {

    private String detail;

    private String title;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
