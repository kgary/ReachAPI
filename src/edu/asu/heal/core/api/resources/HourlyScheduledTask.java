package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.core.api.models.ActivityInstanceStatus;
import edu.asu.heal.core.api.models.Logger;
import edu.asu.heal.core.api.models.Patient;

import java.text.SimpleDateFormat;
import java.util.*;

public class HourlyScheduledTask extends TimerTask {

	private static HealService reachService = HealServiceFactory.getTheService();
	Date now;
	@Override
	public void run() {

		try {
			List<Patient> patients = reachService.getPatients("5a946ff566684905df608446");

			String trialTitle = "Compass"; // Refactor : needs to be done in a better way...
			SimpleDateFormat timeStampFormat = new SimpleDateFormat("MM.dd.YYYY HH:mm:ss", Locale.US);
			String date = timeStampFormat.format(new Date());
			for(int i=0;i<patients.size();i++){
				Integer ppin = patients.get(i).getPin();
				String startMetaData = "{ \"Message\" :  \"UX Personalization Steared for PIN :"+ppin+"\" } ";
				Logger start = new Logger(reachService.getTrialIdByTitle(trialTitle), date, "INFO", "UX_PERSONALIZATION_BEGIN", "JSON",
						null, ppin.toString(), startMetaData);

				ArrayList<Logger> al = new ArrayList<Logger>();
				al.add(start);
				Logger[] logs = new Logger[al.size()];

				logs = al.toArray(logs);
				reachService.logPersonalizationMessage(logs);
				reachService.personalizeUserExperience(patients.get(i).getPin());
				
				String endMetaData = "{ \"Message\" :  \"UX Personalization Ended for PIN :"+ppin+"\" } ";
				Logger end = new Logger(reachService.getTrialIdByTitle(trialTitle), date, "INFO", "UX_PERSONALIZATION_END", "JSON",
						null, ppin.toString(), endMetaData);

				al.clear();
				al.add(end);
				Logger[] loge = new Logger[al.size()];

				loge = al.toArray(loge);
				reachService.logPersonalizationMessage(loge);
			}
			now = new Date();
			System.out.println("Time is :" + now);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}