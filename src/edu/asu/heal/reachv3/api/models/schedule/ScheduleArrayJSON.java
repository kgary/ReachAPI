package edu.asu.heal.reachv3.api.models.schedule;

import java.util.ArrayList;

public class ScheduleArrayJSON {
	
	private int day;
	private ArrayList<ActivityScheduleJSON> activitySchedule;
	
	public ScheduleArrayJSON() {}
	
	public ScheduleArrayJSON(int day,String module) {
		this.day=day;
		activitySchedule = new ArrayList<ActivityScheduleJSON>();
	}

	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public ArrayList<ActivityScheduleJSON> getActivitySchedule() {
		return activitySchedule;
	}

	public void setActivitySchedule(ArrayList<ActivityScheduleJSON> activitySchedule) {
		this.activitySchedule = activitySchedule;
	}

	@Override
	public String toString() {
		return "ScheduleArrayJSON{" +
				", day='" + day + '\'' +
				", activitySchedule=" + activitySchedule.toString() +
				'}';
	}
}
