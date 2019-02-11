package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;
import edu.asu.heal.core.api.models.Patient;

import java.util.*;

public class HourlyScheduledTask extends TimerTask {

    private static HealService reachService = HealServiceFactory.getTheService();
    Date now;
    @Override
    public void run() {

        List<Patient> patients = reachService.getPatients("5a946ff566684905df608446");

        for(int i=0;i<patients.size();i++){
            reachService.personalizeUserExperience(4012);
        }
        now = new Date();
        System.out.println("Time is :" + now);
    }
}
