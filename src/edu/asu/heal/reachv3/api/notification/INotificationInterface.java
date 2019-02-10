package edu.asu.heal.reachv3.api.notification;

import edu.asu.heal.core.api.models.NotificationData;

public interface INotificationInterface {
	
	void sendNotification(String activityName, int patientPin, Integer numberOfDaysNotDone, int levelOfNotification);

}
