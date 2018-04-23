package edu.asu.heal.reachv3.api.models;

public class MakeBelieveOption {
    private int id;
    private String title;

    public MakeBelieveOption(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
