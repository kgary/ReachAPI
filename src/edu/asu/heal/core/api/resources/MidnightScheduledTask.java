package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.Patient;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;
import edu.asu.heal.reachv3.api.service.ReachService;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

public class MidnightScheduledTask extends TimerTask {

    private static HealService reachService = HealServiceFactory.getTheService();
    ReachService service = (ReachService)reachService;
    Date now;

    @Override
    public void run() {
//        List<Patient> patients = reachService.getPatients("5a946ff566684905df608446");
//        for(int i=0;i<patients.size();i++){
//            service.personalizeSkillSet(patients.get(i).getPin());
//            service.updateBlobTricksCount(patients.get(i).getPin());
//        }
        now = new Date();
        System.out.println("Time in the midnight is :" + now);
    }
}
