package edu.asu.heal.reachv3.api.models;

public class MakeBelieveOption {
    private int optionId;
    private String title;

    public MakeBelieveOption() {}

    public MakeBelieveOption(int optionId, String title) {
        this.optionId = optionId;
        this.title = title;
    }

    public int getOptionId() {

        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
