package edu.asu.heal.core.api.models;

import javax.annotation.Generated;

@SuppressWarnings("unused")
public class NotificationData implements IHealModelType {

    private String detail;
    private int id;
    private String title;

    public NotificationData() {
        super();
        id = 555;
    }

    public NotificationData(String detail, String title) {
        super();
        id = 555;
        this.detail = detail;
        this.title = title;
    }

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
