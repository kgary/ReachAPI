package edu.asu.heal.reachv3.api.models;

public class BlobTricks {
	
	private int patientPin;
	private int count;
	
	public BlobTricks() {}
	
	public BlobTricks(int patientPin, int count) {
		this.patientPin=patientPin;
		this.count=count;
	}

	public int getPatientPin() {
		return patientPin;
	}

	public void setPatientPin(int patientPin) {
		this.patientPin = patientPin;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	

}
