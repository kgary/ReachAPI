package edu.asu.heal.reachv3.api.models;

import java.util.Date;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

public class Emotions extends ActivityInstance{
	
	public static String EMOTION_NAME="emotionName";
	public static String INTENSITY="intensity";
	public static String SUGGESTED_ACTIVITIES="suggestedActivities";
	public static String SESSION ="session";
	//public static String ID=""
	private String emotionName;
	private String intensity;
	private String suggestedActivities;
	private String session;
	
	public Emotions() {}
	
	public Emotions(String activityInstanceId, Date createdAt, Date updatedAt, String description,
			Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime,
			ActivityInstanceType instanceOf, String state, int patientPin, boolean isActivityGlowing) {
		super(activityInstanceId, createdAt, updatedAt, description, startTime, endTime, 
	    		 userSubmissionTime, actualSubmissionTime, instanceOf, state, patientPin, isActivityGlowing);
		this.emotionName=null;
		this.intensity=null;
		this.suggestedActivities =null;
		this.session = null;
	}
	
	public String getEmotionName() {
		return emotionName;
	}
	public void setEmotionName(String emotionName) {
		this.emotionName = emotionName;
	}
	public String getIntensity() {
		return intensity;
	}
	public void setIntensity(String intensity) {
		this.intensity = intensity;
	}
	public String getSuggestedActivity() {
		return suggestedActivities;
	}
	public void setSuggestedActivity(String suggestedActivities) {
		this.suggestedActivities = suggestedActivities;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}

}
