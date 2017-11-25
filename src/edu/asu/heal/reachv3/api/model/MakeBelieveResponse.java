package edu.asu.heal.reachv3.api.model;

import java.util.Date;
import java.util.HashMap;

public class MakeBelieveResponse {
    private int situationId;
    private HashMap<Long, Integer> userAnswers;

    public int getSituationId() {
        return situationId;
    }

    public void setSituationId(int situationId) {
        this.situationId = situationId;
    }

    public HashMap<Long, Integer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(HashMap<Long, Integer> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
