package edu.asu.heal.reachv3.api.models;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

import java.util.Date;
import java.util.List;

public class WorryHeadsActivityInstance extends ActivityInstance {

    private List<WorryHeadsSituation> situation;

    public WorryHeadsActivityInstance(){}

    public WorryHeadsActivityInstance(List<WorryHeadsSituation> situation ) {
        this.situation = situation;
    }
    
    @Override
    public int fetchResponseCount() {
    	if(situation != null && situation.size() > 0 && situation.get(0).getResponses() != null
                && situation.get(0).getResponses().size()==1)
    		return 1;
    	else
    		return 0;
    }

    public WorryHeadsActivityInstance(String activityInstanceId,
                                      Date createdAt, Date updatedAt, String description, Date startTime,
                                      Date endTime, Date userSubmissionTime, Date actualSubmissionTime,
                                      ActivityInstanceType instanceOf, String state, int patientPin,
                                      List<WorryHeadsSituation> situation, boolean isActivityGlowing,
                                      int currentCount,int toBeDoneCount) {
        super(activityInstanceId, createdAt, updatedAt, description, startTime, endTime, userSubmissionTime,
                actualSubmissionTime, instanceOf, state, patientPin, isActivityGlowing,
                 currentCount, toBeDoneCount);
        this.situation = situation;
    }


    public List<WorryHeadsSituation>  getSituation() {
        return situation;
    }

    public void setSituation(List<WorryHeadsSituation> situation ) {
        this.situation = situation;
    }
}
