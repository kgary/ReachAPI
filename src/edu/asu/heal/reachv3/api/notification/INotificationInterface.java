package edu.asu.heal.reachv3.api.notification;

import java.util.List;

import edu.asu.heal.core.api.models.NotificationData;

public interface INotificationInterface {
	
	boolean sendNotification(String activityName,int module, int patientPin, 
			Integer numberOfDaysNotDone, int levelOfNotification, List<String> list);

}
