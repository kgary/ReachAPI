package edu.asu.heal.reachv3.api.models;

public class WorryHeadsResponse {
    private long timeStamp;
    private int optionId;

    public WorryHeadsResponse() {}

    public WorryHeadsResponse(long timeStamp, int optionId) {
        this.timeStamp = timeStamp;
        this.optionId = optionId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }
}
