package edu.asu.heal.reachv3.api.model;

import java.util.List;

public class MakeBelieveQuestion {
    private String type;

    public MakeBelieveQuestion(String type, List<MakeBelieveOption> options) {
        this.type = type;
        this.options = options;
    }

    private List<MakeBelieveOption> options;

    public List<MakeBelieveOption> getOptions() {
        return options;
    }

    public void setOptions(List<MakeBelieveOption> options) {
        this.options = options;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
