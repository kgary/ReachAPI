package edu.asu.heal.reachv3.api.service.schedule;

import java.util.Date;

public class ModuleJSON {

	private String module;
	private Date startDate;
	private Date endDate;
	private ScheduleArrayJSON schedule;
	
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

	public ScheduleArrayJSON getSchedule() {
		return schedule;
	}

	public void setSchedule(ScheduleArrayJSON schedule) {
		this.schedule = schedule;
	}
	
}
