package edu.asu.heal.reachv3.api.service.schedule;

public class AvailableTime {

	private int to;
	private int from;
	
	public AvailableTime() {}
	
	public AvailableTime(int to, int from) {
		this.to = to;
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}
	
	
}
