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
}
