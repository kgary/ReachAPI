package edu.asu.heal.reachv3.api.models;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

import java.util.Date;
import java.util.List;


public class FaceitActivityInstance extends ActivityInstance {

    private List<FaceItModel> faceItChallenges;

    public FaceitActivityInstance() {}

    public FaceitActivityInstance(List<FaceItModel> faceItChallenges){ this.faceItChallenges = faceItChallenges;}

    public FaceitActivityInstance(String activityInstanceId, Date createdAt, Date updatedAt, String description,
                                       Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime,
                                       ActivityInstanceType instanceOf, String state, int patientPin,
                                       List<FaceItModel> faceItChallenges, boolean isActivityGlowing,
                                       int currentCount,int toBeDoneCount) {
        super(activityInstanceId, createdAt, updatedAt, description, startTime, endTime, userSubmissionTime,
                actualSubmissionTime, instanceOf, state, patientPin, isActivityGlowing,
                 currentCount, toBeDoneCount);
        this.faceItChallenges = faceItChallenges;
    }

    public List<FaceItModel> getFaceItChallenges() {
        return faceItChallenges;
    }

    public void setFaceItChallenges(List<FaceItModel> faceItChallenges) {
        this.faceItChallenges = faceItChallenges;
    }
    
    public void setFaceItChallenge() {
    	
    	FaceItModel tmp=new FaceItModel();
    	System.out.println("Iterating face it");
    	for(FaceItModel faceItModel:faceItChallenges) {
    		System.out.println(faceItModel.getQuestionId());
    		if(faceItModel.getAnswerId()==0) {
    			tmp=faceItModel;
    			break;
    		}
    	}
    	faceItChallenges.clear();
    	faceItChallenges.add(tmp);
    }
}