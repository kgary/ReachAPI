package edu.asu.heal.reachv3.api.notification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.core.api.models.NotificationData;

public class LevelTwoNotification implements INotificationInterface{

	private static JSONObject notificationData;
	private static Properties _properties;

	static {
		try {
			String data = readFile("notificationData.json");
			notificationData = new JSONObject(data);
			
			_properties = new Properties();
			InputStream propFile = LevelTwoNotification.class.getResourceAsStream("urls.properties");
			_properties.load(propFile);
			propFile.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public LevelTwoNotification() {
	}

	@Override
	public void sendNotification(String activityName, int patientPin, Integer numberOfDaysNotDone,int levelOfNotification) {
		String details = getNotifiactionDetails(activityName,levelOfNotification,numberOfDaysNotDone.toString());
		String url = _properties.getProperty(activityName);
		String serverKey = _properties.getProperty("serverKey");
		
		NotificationData data = new NotificationData(details, null, url, levelOfNotification);

		Notification obj = new Notification();
		obj.sendNotification(data, patientPin, serverKey);
		
	}
	
	public String getNotifiactionDetails(String activityName, int levelOfNotification,String numberOfDaysNotDone) {
		JSONObject actDetails = notificationData.getJSONObject(activityName);
		JSONArray arr =actDetails.getJSONArray("level_"+levelOfNotification);
		ArrayList<String> detail = new ArrayList<String>();
		if(arr != null) {
			for(int i=0; i<arr.length(); i++) {
				detail.add(arr.getString(i));
			}
		}
		Collections.shuffle(detail);
		if(detail.get(0).contains("<N>")) {
			detail.get(0).replaceAll("<N>", numberOfDaysNotDone);
		}
		return detail.get(0);		
	}

	public static String readFile(String filename) {
		String result = "";
		try {
			InputStream is = LevelTwoNotification.class.getResourceAsStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			result = sb.toString();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	
	

}
