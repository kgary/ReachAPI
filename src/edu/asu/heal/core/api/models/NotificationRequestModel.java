package edu.asu.heal.core.api.models;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.*;

@SuppressWarnings("unused")
@JsonFormat(shape=JsonFormat.Shape.OBJECT )
public class NotificationRequestModel implements IHealModelType {

    @JsonProperty private NotificationData data;
    @JsonProperty private String to;
    @JsonProperty private String priority;
//    @JsonProperty private String notification;

    public NotificationRequestModel() {
        priority = "high";
//        notification = "{title\": \"Notification Title\"," +
//                "\"text\": \"Notification Text\"," +
//                "\"click_action\": \"OPEN_ACTIVITY_1\"}";
    }

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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}