package edu.asu.heal.reachv3.api.models;

public class SudsOption {
    private int optionId;
    private String title;

    public SudsOption(int optionId, String title) {
        this.optionId = optionId;
        this.title = title;
    }

    public SudsOption() {
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

    @Override
    public String toString() {
        return "SudsOption{" +
                ", optionId='" + optionId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
