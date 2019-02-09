package edu.asu.heal.reachv3.api.service.schedule;

import java.util.ArrayList;

public class MakeBelieveJSON {

	private int score;
	private ArrayList<AvailableTime> availableTime;
	private int levelofSkillPersonalization;
	private int minimumCount0;
	private int minimumCount1;
	private int minimumCount2;
	private int actualCount;
	private boolean isDailyActivity;
	private UIPersonalizationFile fileNameForUIPersonalization;
	private ArrayList<String> activityInstanceId;
	private int levelOfUIPersonalization;
	
	public MakeBelieveJSON() {		
		this.score=0;
		this.levelofSkillPersonalization=0;
		this.minimumCount0=5;
		this.minimumCount1=8;
		this.minimumCount2=10;
		this.actualCount=0;
		this.isDailyActivity=true;
		this.availableTime = new ArrayList<AvailableTime>();
		this.fileNameForUIPersonalization = new UIPersonalizationFile();
		this.levelOfUIPersonalization=0;
		this.activityInstanceId = new ArrayList<String>();	
		
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ArrayList<AvailableTime> getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(ArrayList<AvailableTime> availableTime) {
		this.availableTime = availableTime;
	}

	public int getLevelofSkillPersonalization() {
		return levelofSkillPersonalization;
	}

	public void setLevelofSkillPersonalization(int levelofSkillPersonalization) {
		this.levelofSkillPersonalization = levelofSkillPersonalization;
	}

	public int getMinimumCount0() {
		return minimumCount0;
	}

	public void setMinimumCount0(int minimumCount0) {
		this.minimumCount0 = minimumCount0;
	}

	public int getMinimumCount1() {
		return minimumCount1;
	}

	public void setMinimumCount1(int minimumCount1) {
		this.minimumCount1 = minimumCount1;
	}

	public int getMinimumCount2() {
		return minimumCount2;
	}

	public void setMinimumCount2(int minimumCount2) {
		this.minimumCount2 = minimumCount2;
	}

	public int getActualCount() {
		return actualCount;
	}

	public void setActualCount(int actualCount) {
		this.actualCount = actualCount;
	}

	public boolean isDailyActivity() {
		return isDailyActivity;
	}

	public void setDailyActivity(boolean isDailyActivity) {
		this.isDailyActivity = isDailyActivity;
	}

	public UIPersonalizationFile getFileNameForUIPersonalization() {
		return fileNameForUIPersonalization;
	}

	public void setFileNameForUIPersonalization(UIPersonalizationFile fileNameForUIPersonalization) {
		this.fileNameForUIPersonalization = fileNameForUIPersonalization;
	}

	public ArrayList<String> getActivityInstanceId() {
		return activityInstanceId;
	}

	public void setActivityInstanceId(ArrayList<String> activityInstanceId) {
		this.activityInstanceId = activityInstanceId;
	}

	public int getLevelOfUIPersonalization() {
		return levelOfUIPersonalization;
	}

	public void setLevelOfUIPersonalization(int levelOfUIPersonalization) {
		this.levelOfUIPersonalization = levelOfUIPersonalization;
	}
	
}
