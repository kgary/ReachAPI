package edu.asu.heal.reachv3.api.models;

public class WorryHeadsModel {
    private int situationId;
    private String name;
    private String sText, tText, pText;
    private int wrongO;
    private String[] o;

    WorryHeadsModel(){}

    public WorryHeadsModel(int situationId, String name, String sText, String tText, String pText, int wrongO, String[] o) {
        this.situationId = situationId;
        this.name = name;
        this.sText = sText;
        this.tText = tText;
        this.pText = pText;
        this.wrongO = wrongO;
        this.o = o;
    }

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

    public String getsText() {
        return sText;
    }

    public void setsText(String sText) {
        this.sText = sText;
    }

    public String gettText() {
        return tText;
    }

    public void settText(String tText) {
        this.tText = tText;
    }

    public String getpText() {
        return pText;
    }

    public void setpText(String pText) {
        this.pText = pText;
    }

    public int getWrongO() {
        return wrongO;
    }

    public void setWrongO(int wrongO) {
        this.wrongO = wrongO;
    }

    public String[] getO() {
        return o;
    }

    public void setO(String[] o) {
        this.o = o;
    }
}
