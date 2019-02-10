package edu.asu.heal.reachv3.api.models.schedule;

import java.util.ArrayList;
import java.util.Date;

public class ModuleJSON {

	private String module;
	private Date startDate;
	private Date endDate;
	private ArrayList<ScheduleArrayJSON> schedule;
	
	public ModuleJSON() {
		
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ArrayList<ScheduleArrayJSON> getSchedule() {
		return schedule;
	}

	public void setSchedule(ArrayList<ScheduleArrayJSON> schedule) {
		this.schedule = schedule;
	}

	@Override
	public String toString() {
		return "ModuleJSON{" +
				", module='" + module + '\'' +
				", startDate=" + startDate +
				", endDate=" + endDate +
				", schedule=" + schedule.toString() +
				'}';
	}

}
