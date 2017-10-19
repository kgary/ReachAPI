package edu.asu.heal.reachv3.api.model;

public class ScheduleModel {
    private int weekNumber;
    private int day;
    private boolean relaxation;
    private boolean diaryEvent1;
    private boolean diaryEvent2;
    private boolean stop;
    private boolean stopWorryheads;
    private int stic;
    private boolean sudScaleEvent;
    private boolean rtu;
    private boolean blob;
    private boolean safe;

    public ScheduleModel() {
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isRelaxation() {
        return relaxation;
    }

    public void setRelaxation(boolean relaxation) {
        this.relaxation = relaxation;
    }

    public boolean isDiaryEvent1() {
        return diaryEvent1;
    }

    public void setDiaryEvent1(boolean diaryEvent1) {
        this.diaryEvent1 = diaryEvent1;
    }

    public boolean isDiaryEvent2() {
        return diaryEvent2;
    }

    public void setDiaryEvent2(boolean diaryEvent2) {
        this.diaryEvent2 = diaryEvent2;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isStopWorryheads() {
        return stopWorryheads;
    }

    public void setStopWorryheads(boolean stopWorryheads) {
        this.stopWorryheads = stopWorryheads;
    }

    public int getStic() {
        return stic;
    }

    public void setStic(int stic) {
        this.stic = stic;
    }

    public boolean isSudScaleEvent() {
        return sudScaleEvent;
    }

    public void setSudScaleEvent(boolean sudScaleEvent) {
        this.sudScaleEvent = sudScaleEvent;
    }

    public boolean isRtu() {
        return rtu;
    }

    public void setRtu(boolean rtu) {
        this.rtu = rtu;
    }

    public boolean isBlob() {
        return blob;
    }

    public void setBlob(boolean blob) {
        this.blob = blob;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getWeekNumber()) + "\n" +
                String.valueOf(this.getDay()) + "\n" +
                String.valueOf(this.getStic()) + "\n" +
                String.valueOf(this.isRelaxation()) + "\n" +
                String.valueOf(this.isStop()) + "\n" +
                String.valueOf(this.isSafe()) + "\n" +
                String.valueOf(this.isStopWorryheads());
    }
}
