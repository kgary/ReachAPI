package edu.asu.heal.reachv3.api.models.schedule;

import java.util.ArrayList;

public class PatientScheduleJSON {

    public static String PATIENTPIN_ATTRIBUTE = "pin";
    public static String SCHEDULE_ATTRIBUTE = "schedule";

    private int pin;
    private ArrayList<ModuleJSON> schedule;

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public ArrayList<ModuleJSON> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<ModuleJSON> schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "PatientScheduleJSON{" +
                ", pin='" + pin + '\'' +
                ", schedule=" + schedule.toString() +
                '}';
    }

}
