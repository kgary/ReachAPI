package edu.asu.heal.reachv3.api.notification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.asu.heal.core.api.models.NotificationData;

public class LevelOneNotification implements INotificationInterface {

	private static JSONObject notificationData;
	private static Properties _properties;

	static {
		try {
			String data = readFile("notificationData.json");
			notificationData = new JSONObject(data);

			_properties = new Properties();
			InputStream propFile = LevelOneNotification.class.getResourceAsStream("urls.properties");
			_properties.load(propFile);
			propFile.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public LevelOneNotification() {
	}

	@Override
	public boolean sendNotification(String activityName,int module, int patientPin, 
			Integer numberOfDaysNotDone, int levelOfNotification,List<String> list ) {

		String details = getNotifiactionDetails(String.valueOf(module),levelOfNotification,numberOfDaysNotDone.toString());
		if(!details.equals("")) {
			String url = _properties.getProperty(activityName);
			String serverKey = _properties.getProperty("serverKey");

			List<ActivityList> l1List = new ArrayList<>();

			for(int i=0; i<list.size();i++) {
				String activityUrl = "";
				if (_properties.getProperty(list.get(i)) != null) {
					activityUrl = _properties.getProperty(list.get(i));
				}
				ActivityList obj = new ActivityList(list.get(i), activityUrl, false);
				l1List.add(obj);
			}

			NotificationData data = new NotificationData(details, null, url, levelOfNotification, l1List);

			Notification obj = new Notification();
			if(obj.sendNotification(data, patientPin, serverKey)) {
				return true;
			}
		}
		return false;		
	}

	public String getNotifiactionDetails(String module, int levelOfNotification, String numberOfDaysNotDone ) {

		if (notificationData.has(module) && notificationData.getJSONObject(module) != null) {
			JSONObject actDetails = notificationData.getJSONObject(module);
			JSONArray arr = actDetails.getJSONArray("level_" + levelOfNotification);
			ArrayList<String> detail = new ArrayList<String>();
			if (arr != null) {
				for (int i = 0; i < arr.length(); i++) {
					detail.add(arr.getString(i));
				}
			}
			Collections.shuffle(detail);
			if (detail.get(0).contains("<N>")) {
				detail.get(0).replaceAll("<N>", numberOfDaysNotDone);
			}
			return detail.get(0);
		}
		return "";
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
