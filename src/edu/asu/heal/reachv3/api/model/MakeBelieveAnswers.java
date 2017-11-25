package edu.asu.heal.reachv3.api.model;

public class MakeBelieveAnswers {
    private int situationId;
    private int howResponseId;
    private int whenResponseId;

    public int getSituationId() {
        return situationId;
    }

    public void setSituationId(int situationId) {
        this.situationId = situationId;
    }

    public int getHowResponseId() {
        return howResponseId;
    }

    public void setHowResponseId(int howResponseId) {
        this.howResponseId = howResponseId;
    }

    public int getWhenResponseId() {
        return whenResponseId;
    }

    public void setWhenResponseId(int whenResponseId) {
        this.whenResponseId = whenResponseId;
    }
}
