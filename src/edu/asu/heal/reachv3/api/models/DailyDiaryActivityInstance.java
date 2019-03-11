package edu.asu.heal.reachv3.api.models;

import java.util.Date;
import java.util.List;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

public class DailyDiaryActivityInstance extends ActivityInstance {
	

	private String whatHappened;
	private int worryIntensity;
	private String actionTaken;
	private String thoughts;
	private List<SUDSQuestion> sudsQuestion;
	
	public DailyDiaryActivityInstance() {
	}

	public DailyDiaryActivityInstance(String activityInstanceId,
									  Date createdAt, Date updatedAt,
									  String description, Date startTime, Date endTime,
									  Date userSubmissionTime, Date actualSubmissionTime,
									  ActivityInstanceType instanceOf, String state,
									  int patientPin, boolean activityGlowing, String whatHappened,
									  int worryIntensity, String actionTaken, String thoughts,
									  List<SUDSQuestion> sudsQuestion) {
		super(activityInstanceId, createdAt, updatedAt, description, startTime, endTime,
				userSubmissionTime, actualSubmissionTime, instanceOf, state, patientPin, activityGlowing);
		this.whatHappened = whatHappened;
		this.worryIntensity = worryIntensity;
		this.actionTaken = actionTaken;
		this.thoughts = thoughts;
		this.sudsQuestion = sudsQuestion;
	}

	public String getWhatHappened() {
		return whatHappened;
	}
	public void setWhatHappened(String whatHappened) {
		this.whatHappened = whatHappened;
	}
	public int getWorryIntensity() {
		return worryIntensity;
	}
	public void setWorryIntensity(int worryIntensity) {
		this.worryIntensity = worryIntensity;
	}
	public String getActionTaken() {
		return actionTaken;
	}
	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}
	public String getThoughts() {
		return thoughts;
	}
	public void setThoughts(String thoughts) {
		this.thoughts = thoughts;
	}

	public List<SUDSQuestion> getSudsQuestion() {
		return sudsQuestion;
	}

	public void setSudsQuestion(List<SUDSQuestion> sudsQuestion) {
		this.sudsQuestion = sudsQuestion;
	}

}
