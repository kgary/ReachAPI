package edu.asu.heal.reachv3.api.notification;

import java.util.List;

public interface INotificationInterface {
	
	boolean sendNotification(String activityName,int module, int patientPin, 
			Integer numberOfDaysNotDone, int levelOfNotification, List<String> list, boolean sudsConfig);

}
