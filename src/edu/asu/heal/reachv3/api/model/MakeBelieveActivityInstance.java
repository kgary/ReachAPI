package edu.asu.heal.reachv3.api.model;

import edu.asu.heal.core.api.models.ActivityInstance;

import java.util.List;

public class MakeBelieveActivityInstance extends ActivityInstance{
    private String name;
    private int situationId;
    private String situationTitle;
    private List<MakeBelieveQuestion> questions;

    public int getSituationId() {
        return situationId;
    }

    public void setSituationId(int situationId) {
        this.situationId = situationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSituationTitle() {
        return situationTitle;
    }

    public void setSituationTitle(String situationTitle) {
        this.situationTitle = situationTitle;
    }

    public List<MakeBelieveQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<MakeBelieveQuestion> questions) {
        this.questions = questions;
    }

}
