package edu.asu.heal.reachv3.api.service.schedule;

import java.util.ArrayList;

public class ScheduleArrayJSON {
	
	private int day;
	private ArrayList<ActivityScheduleJSON> schedule;
	
	public ScheduleArrayJSON() {}
	
	public ScheduleArrayJSON(int day,String module) {
		this.day=day;
		schedule = new ArrayList<ActivityScheduleJSON>();
	}

	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public ArrayList<ActivityScheduleJSON> getSchedule() {
		return schedule;
	}

	public void setSchedule(ArrayList<ActivityScheduleJSON> schedule) {
		this.schedule = schedule;
	}

	
}
