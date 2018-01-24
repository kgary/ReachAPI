package edu.asu.heal.reachv3.api.model;

import java.util.List;

public class MakeBelieveSituation {
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
