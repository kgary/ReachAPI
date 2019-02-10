package edu.asu.heal.reachv3.api.models.schedule;

import java.util.ArrayList;

public class ActivityScheduleJSON {

	private String activity;
	private int score;
	private ArrayList<AvailableTime> availableTime;
	private int levelofSkillPersonalization;
	private int minimumCount0;
	private int minimumCount1;
	private int minimumCount2;
	private int actualCount;
	private boolean isDailyActivity;
	private UIPersonalizationFile fileNameForUIPersonalization;
	private ArrayList<String> activityInstancesIds;
	private int levelOfUIPersonalization;

	public ActivityScheduleJSON() {
		this.activity = "";
		this.score=0;
		this.levelofSkillPersonalization=0;
		this.minimumCount0=1;
		this.minimumCount1=1;
		this.minimumCount2=1;
		this.actualCount=0;
		this.isDailyActivity=true;
		this.availableTime = new ArrayList<AvailableTime>();
		this.fileNameForUIPersonalization = new UIPersonalizationFile();
		this.levelOfUIPersonalization=0;
		this.activityInstancesIds = new ArrayList<String>();

	}
	
	public int getMinimumCount() {
		
		if(this.levelofSkillPersonalization == 0)
			return minimumCount0;
		if(this.levelofSkillPersonalization == 1)
			return minimumCount1;
		if(this.levelofSkillPersonalization == 2)
			return minimumCount2;
		
		return minimumCount0;
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

	public ArrayList<String> getActivityInstancesIds() {
		return activityInstancesIds;
	}

	public void setActivityInstancesIds(ArrayList<String> activityInstancesIds) {
		this.activityInstancesIds = activityInstancesIds;
	}

	public int getLevelOfUIPersonalization() {
		return levelOfUIPersonalization;
	}

	public void setLevelOfUIPersonalization(int levelOfUIPersonalization) {
		this.levelOfUIPersonalization = levelOfUIPersonalization;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}


	@Override
	public String toString() {
		return "ActivityScheduleJSON{" +
				", activity='" + activity + '\'' +
				", score=" + score +
				", availableTime=" + availableTime.toString() +
				", levelofSkillPersonalization=" + levelofSkillPersonalization +
				", minimumCount0=" + minimumCount0 +
				", minimumCount1=" + minimumCount1 +
				", minimumCount2=" + minimumCount2 +
				", actualCount=" + actualCount +
				", isDailyActivity='" + isDailyActivity + '\'' +
				", fileNameForUIPersonalization='" + fileNameForUIPersonalization.toString() + '\'' +
				", activityInstancesIds='" + activityInstancesIds.toString() + '\'' +
				", levelOfUIPersonalization='" + levelOfUIPersonalization + '\'' +
				'}';
	}
}
