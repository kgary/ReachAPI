package edu.asu.heal.reachv3.api.models.schedule;

import java.util.ArrayList;
import java.util.Date;

public class PatientScheduleJSON {

    public static String PATIENTPIN_ATTRIBUTE = "pin";
    public static String SCHEDULE_ATTRIBUTE = "schedule";

    private int pin;
    private ArrayList<ModuleJSON> schedule;
    private Date worryHeadsResetDate;
    private Date standUpResetDate;
    private Date makeBelieveResetDate;
    private Date worryHeadsStartDate;
    private Date standUpStartDate;
    private Date makeBelieveStartDate;


    
    public Date getWorryHeadsStartDate() {
		return worryHeadsStartDate;
	}

	public void setWorryHeadsStartDate(Date worryHeadsStartDate) {
		this.worryHeadsStartDate = worryHeadsStartDate;
	}

	public Date getStandUpStartDate() {
		return standUpStartDate;
	}

	public void setStandUpStartDate(Date standUpStartDate) {
		this.standUpStartDate = standUpStartDate;
	}

	public Date getMakeBelieveStartDate() {
		return makeBelieveStartDate;
	}

	public void setMakeBelieveStartDate(Date makeBelieveStartDate) {
		this.makeBelieveStartDate = makeBelieveStartDate;
	}

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

    public Date getWorryHeadsResetDate() {
        return worryHeadsResetDate;
    }

    public void setWorryHeadsResetDate(Date worryHeadsResetDate) {
        this.worryHeadsResetDate = worryHeadsResetDate;
    }

    public Date getStandUpResetDate() {
        return standUpResetDate;
    }

    public void setStandUpResetDate(Date standUpResetDate) {
        this.standUpResetDate = standUpResetDate;
    }

    public Date getMakeBelieveResetDate() {
        return makeBelieveResetDate;
    }

    public void setMakeBelieveResetDate(Date makeBelieveResetDate) {
        this.makeBelieveResetDate = makeBelieveResetDate;
    }

    @Override
    public String toString() {
        return "PatientScheduleJSON{" +
                ", pin='" + pin + '\'' +
                ", schedule=" + schedule.toString() +
                '}';
    }

}
