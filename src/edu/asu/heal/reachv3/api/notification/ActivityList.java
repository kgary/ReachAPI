package edu.asu.heal.reachv3.api.notification;

public class ActivityList {

	public String activityName;
	public String url;
	public boolean shouldGlow;
	
	public ActivityList() {}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isShouldGlow() {
		return shouldGlow;
	}

	public void setShouldGlow(boolean shouldGlow) {
		this.shouldGlow = shouldGlow;
	}
	
	
}
