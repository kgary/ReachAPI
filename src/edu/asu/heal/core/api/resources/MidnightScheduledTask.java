package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.Logger;
import edu.asu.heal.core.api.models.Patient;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;
import edu.asu.heal.reachv3.api.service.ReachService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimerTask;

public class MidnightScheduledTask extends TimerTask {

	private static HealService reachService = HealServiceFactory.getTheService();
	Date now;
	@Override
	public void run() {
		try {
			List<Patient> patients = reachService.getPatients("5a946ff566684905df608446");

			if(patients != null) {
				String trialTitle = "Compass"; // Refactor : needs to be done in a better way...
				SimpleDateFormat timeStampFormat = new SimpleDateFormat("MM.dd.YYYY HH:mm:ss", Locale.US);
				String date = timeStampFormat.format(new Date());
				String startMetaData = "", endMetaData="";
				Logger start,end;
				Logger[] logs;
				ArrayList<Logger> al = new ArrayList<Logger>();

				startMetaData = "{ \"Message\" :  \"**************** SKILL PERSONALIZATION CRON STARTED ****************\" } ";
				start = new Logger(reachService.getTrialIdByTitle(trialTitle), date, "INFO", "SKILL_PERSONALIZATION_BEGIN", "JSON",
						null, null, startMetaData);

				al.add(start);
//				logs = new Logger[al.size()];
//
//				logs = al.toArray(logs);
//				reachService.logPersonalizationMessage(logs);
//				al.clear();
				for(int i=0;i<patients.size();i++){
					Integer ppin = patients.get(i).getPin();
					startMetaData = "{ \"Message\" :  \"Skill Personalization started for PIN :"+ppin+"\" } ";
					start = new Logger(reachService.getTrialIdByTitle(trialTitle), date, "INFO", "SKILL_PERSONALIZATION_BEGIN", "JSON",
							null, ppin.toString(), startMetaData);

					al.add(start);
//					logs = new Logger[al.size()];
//
//					logs = al.toArray(logs);
//					reachService.logPersonalizationMessage(logs);

					((ReachService) reachService).updateBlobTricksCount(patients.get(i).getPin());

					boolean rval = reachService.personalizeSkillSet(patients.get(i).getPin());
//					System.out.println("rval = " + rval);
					if(rval) {
						endMetaData = "{ \"Message\" :  \"Skill Personalization ended for PIN :"+ppin+"\" } ";

					}else {
						endMetaData = "{ \"Message\" :  \"SKILL_PERSONALIZATION - NO SCHEDULE FOR PIN :"+ppin+"\" } ";	
					}

					end = new Logger(reachService.getTrialIdByTitle(trialTitle), date, "INFO", "SKILL_PERSONALIZATION_END", "JSON",
							null, ppin.toString(), endMetaData);

//					al.clear();
					al.add(end);
//					logs = new Logger[al.size()];
//
//					logs = al.toArray(logs);
//					reachService.logPersonalizationMessage(logs);
				}
//				al.clear();
				endMetaData = "{ \"Message\" :  \"**************** SKILL PERSONALIZATION CRON ENDED ****************\" } ";
				end = new Logger(reachService.getTrialIdByTitle(trialTitle), date, "INFO", "SKILL_PERSONALIZATION_END", "JSON",
						null, null, endMetaData);
				al.add(end);
				logs = new Logger[al.size()];
				logs = al.toArray(logs);
				reachService.logPersonalizationMessage(logs);
				now = new Date();
				System.out.println("Time is :" + now);
			}else {
				System.out.println("No Patients in the trial....");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
