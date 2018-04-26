package edu.asu.heal.reachv3.api.models;

import java.util.Date;
import java.util.HashMap;

public class MakeBelieveResponse {
    private Date timeStamp;
    private int optionId;

    public MakeBelieveResponse() {}

    public MakeBelieveResponse(Date timeStamp, int optionId) {
        this.timeStamp = timeStamp;
        this.optionId = optionId;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }
}
