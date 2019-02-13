package edu.asu.heal.reachv3.api.models;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

import java.util.Date;

public class MakeBelieveActivityInstance extends ActivityInstance{
    private MakeBelieveSituation situation;

    public MakeBelieveActivityInstance(){}
    public MakeBelieveActivityInstance(MakeBelieveSituation situation) {
        this.situation = situation;
    }

    public MakeBelieveActivityInstance(String activityInstanceId, Date createdAt, Date updatedAt, String description, Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime, ActivityInstanceType instanceOf, String state, int patientPin, MakeBelieveSituation situation, boolean isActivityGlowing) {
        super(activityInstanceId, createdAt, updatedAt, description, startTime, endTime, userSubmissionTime, actualSubmissionTime, instanceOf, state, patientPin, isActivityGlowing);
        this.situation = situation;
    }


    public MakeBelieveSituation getSituation() {
        return situation;
    }

    public void setSituation(MakeBelieveSituation situation) {
        this.situation = situation;
    }
    
    public int getResponseCount() {
    	
    	if(situation != null && situation.getQuestions().size() > 1
                && situation.getQuestions().get(0).getResponses() != null &&
                situation.getQuestions().get(1).getResponses() != null
                && ((situation.getQuestions().get(0).getResponses().size() == 1) &&
                (situation.getQuestions().get(1).getResponses().size() == 1)))
    		return 1;
    	else
    		return 0;
    }
}
