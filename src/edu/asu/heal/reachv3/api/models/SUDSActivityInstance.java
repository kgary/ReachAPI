package edu.asu.heal.reachv3.api.models;

import java.util.Date;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

public class SUDSActivityInstance extends ActivityInstance {
	
	private SUDSQuestion question;
	private int intensity;
	
	public SUDSActivityInstance() {
		
	}
	
	public SUDSActivityInstance(String activityInstanceId, Date createdAt, Date updatedAt, String description,
			Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime,
			ActivityInstanceType instanceOf, String state, int patientPin, boolean isActivityGlowing, SUDSQuestion question) {
	     super(activityInstanceId, createdAt, updatedAt, description, startTime, endTime, 
	    		 userSubmissionTime, actualSubmissionTime, instanceOf, state, patientPin, isActivityGlowing);
	     this.question=question;
	     this.intensity=0;
	}

	public SUDSQuestion getQuestion() {
		return question;
	}

	public void setQuestion(SUDSQuestion question) {
		this.question = question;
	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}
	

}
