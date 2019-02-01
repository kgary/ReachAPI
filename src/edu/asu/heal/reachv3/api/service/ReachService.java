package edu.asu.heal.reachv3.api.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.responses.HEALResponse;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.SuggestedActivityiesMappingService.MappingFactory;
import edu.asu.heal.core.api.service.SuggestedActivityiesMappingService.MappingInterface;
import edu.asu.heal.reachv3.api.models.MakeBelieveActivityInstance;
import edu.asu.heal.reachv3.api.models.MakeBelieveSituation;
import edu.asu.heal.reachv3.api.models.DailyDiaryActivityInstance;
import edu.asu.heal.reachv3.api.models.Emotions;
import edu.asu.heal.reachv3.api.models.SwapActivityInstance;
import edu.asu.heal.reachv3.api.models.StandUpActivityInstance;
import edu.asu.heal.reachv3.api.models.FaceItModel;
import edu.asu.heal.reachv3.api.models.FaceitActivityInstance;
import edu.asu.heal.reachv3.api.models.WorryHeadsActivityInstance;
import edu.asu.heal.reachv3.api.models.WorryHeadsSituation;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ReachService implements HealService {

	private static final String DATE_FORMAT = "MM/dd/yyyy";

	/****************************************  Service methods for Activity  ******************************************/
	@Override
	public List<Activity> getActivities(String domain) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			List<Activity> result = dao.getActivities(domain);

			return result;
		} catch (Exception e) {
			System.out.println("SOME ERROR IN GETACTIVITIES() IN REACHSERVICE CLASS");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity createActivity(String title, String description) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			Activity newActivity = new Activity();
			newActivity.setTitle(title);
			newActivity.setDescription(description);
			newActivity.setUpdatedAt(new Date());
			newActivity.setCreatedAt(new Date());
			Activity createdActivity = dao.createActivity(newActivity);

			return createdActivity;
		} catch (Exception e) {
			System.out.println("SOME PROBLEM IN REACH SERVICE - CREATEACTIVITY");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity getActivity(String activityId) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			return dao.getActivity(activityId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity updateActivity(Activity activity) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			Activity activityInDatabase = dao.getActivity(activity.getActivityId());
			if (activityInDatabase == null || activityInDatabase.equals(NullObjects.getNullActivity()))
				return activityInDatabase;

			activityInDatabase.setTitle(
					activity.getTitle() != null ? activity.getTitle() : activityInDatabase.getTitle());
			activityInDatabase.setDescription(
					activity.getDescription() != null ? activity.getDescription() : activityInDatabase.getDescription());
			activityInDatabase.setUpdatedAt(new Date());

			return dao.updateActivity(activityInDatabase);
		} catch (Exception e) {
			System.out.println("SOME PROBLEM IN UPDATE ACTIVITY IN REACHSERVICE");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity deleteActivity(String activityId) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			return dao.deleteActivity(activityId);
		} catch (Exception e) {
			System.out.println("SOME PROBLEM IN REACH SERVICE DELETE ACTIVITY INSTANCE");
			e.printStackTrace();
			return null;
		}
	}

	/****************************************  Service methods for ActivityInstance  **********************************/
	@Override
	public List<ActivityInstance> getActivityInstances(int patientPin) {
		List<ActivityInstance> response = null;
		try {
			DAO dao = DAOFactory.getTheDAO();
			List<ActivityInstance> instances = dao.getScheduledActivities(patientPin);

			return instances;
		} catch (Exception e) {
			System.out.println("SOME ERROR IN GETACTIVITYINSTANCES() IN REACHSERVICE");
			e.printStackTrace();
			return null;
		}
	}

	public String getEmotionsActivityInstance(int patientPin, String emotion, int intensity){
		try{
			DAO dao = DAOFactory.getTheDAO();
			List<String> results = dao.getEmotionsActivityInstance(emotion.toLowerCase(), intensity);
			if(results == null)
				return "";

			StringWriter writer = new StringWriter();
			JsonGenerator generator = new JsonFactory().createGenerator(writer);
			generator.setCodec(new ObjectMapper());
			generator.writeStartObject();
			generator.writeObjectField("activities", results);
			generator.writeEndObject();

			generator.close();
			String emotionsActivities = writer.toString();
			writer.close();
			return emotionsActivities;

		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ActivityInstance getActivityInstance(String activityInstanceId) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			ActivityInstance instance = dao.getActivityInstance(activityInstanceId);

			// Code to log state of activity instance in the Mongo...

			String trialTitle = "Compass"; // Refactor : needs to be done in a better way...
			SimpleDateFormat timeStampFormat = new SimpleDateFormat("MM.dd.YYYY HH:mm:ss", Locale.US);
			String date = timeStampFormat.format(new Date());
			Integer ppin= instance.getPatientPin();
			String metaData = "{ \"activityInstanceId :\" \"" +instance.getActivityInstanceId() +"\" , \"ACTIVITY_INSTANCE_STATE\" : \""+ ActivityInstanceStatus.IN_EXECUTION.status() +"\" } " ;
			Logger log = new Logger(dao.getTrialIdByTitle(trialTitle),date,"INFO","ACTIVITY_STATE","JSON",
					instance.getInstanceOf().getName(),ppin.toString(),metaData);

			ArrayList<Logger> al = new ArrayList<Logger>();
			al.add(log);
			Logger[] logs = new Logger[al.size()] ;

			logs = al.toArray(logs);
			dao.logMessage(logs);

			return instance;
		} catch (Exception e) {
			System.out.println("SOME ERROR IN HEAL SERVICE getActivityInstance");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ActivityInstance createActivityInstance(ActivityInstance activityInstance) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			if (activityInstance.getCreatedAt() == null) activityInstance.setCreatedAt(new Date());
			if (activityInstance.getState() == null) activityInstance.setState(ActivityInstanceStatus.CREATED.status());
			if (activityInstance.getUpdatedAt() == null) activityInstance.setUpdatedAt(new Date());

			if(activityInstance.getInstanceOf().getName().equals("MakeBelieve")){ //todo need a more elegant way of making the check whether it is of type make believe
				activityInstance =
						new MakeBelieveActivityInstance(activityInstance.getActivityInstanceId(),
								activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
								activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
								activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
								activityInstance.getInstanceOf(), activityInstance.getState(),
								activityInstance.getPatientPin(), dao.getMakeBelieveSituation(),activityInstance.getActivityGlowing());
			} else if(activityInstance.getInstanceOf().getName().equals("FaceIt")) {
				activityInstance = new FaceitActivityInstance(
						activityInstance.getActivityInstanceId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getInstanceOf(), activityInstance.getState(),
						activityInstance.getPatientPin(), dao.getFaceItChallenges(), activityInstance.getActivityGlowing()
						);
			}else if(activityInstance.getInstanceOf().getName().equals("DailyDiary")) {
				activityInstance = new DailyDiaryActivityInstance(
						activityInstance.getActivityInstanceId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getInstanceOf(), activityInstance.getState(),
						activityInstance.getPatientPin(), activityInstance.getActivityGlowing()
						);
			} else if(activityInstance.getInstanceOf().getName().equals("SWAP")) {
				activityInstance = new SwapActivityInstance(activityInstance.getActivityInstanceId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getInstanceOf(), activityInstance.getState(),
						activityInstance.getPatientPin(), activityInstance.getActivityGlowing()
						);
			} else if(activityInstance.getInstanceOf().getName().equals("WorryHeads")){
				activityInstance = new WorryHeadsActivityInstance(
						activityInstance.getActivityInstanceId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getInstanceOf(), activityInstance.getState(),
						activityInstance.getPatientPin(), dao.getAllWorryHeadsSituations(), activityInstance.getActivityGlowing());
			} else if(activityInstance.getInstanceOf().getName().equals("StandUp")) {
				activityInstance = new StandUpActivityInstance(
						activityInstance.getActivityInstanceId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getInstanceOf(), activityInstance.getState(),
						activityInstance.getPatientPin(), dao.getStandUpSituations(),activityInstance.getActivityGlowing());
			}
			ActivityInstance newActivityInstance = dao.createActivityInstance(activityInstance);


			// Code to log state of activity instance in the Mongo...

			String trialTitle = "Compass"; // Refactor : needs to be done in a better way...
			SimpleDateFormat timeStampFormat = new SimpleDateFormat("MM.dd.YYYY HH:mm:ss", Locale.US);
			String date = timeStampFormat.format(new Date());
			Integer ppin= newActivityInstance.getPatientPin();
			String metaData = "{ \"activityInstanceId :\" \"" +activityInstance.getActivityInstanceId() +"\" , \"ACTIVITY_INSTANCE_STATE\" : \""+ ActivityInstanceStatus.CREATED.status()+"\" } " ;
			Logger log = new Logger(dao.getTrialIdByTitle(trialTitle),date,"INFO","ACTIVITY_STATE","JSON",
					activityInstance.getInstanceOf().getName(),ppin.toString(),metaData);

			ArrayList<Logger> al = new ArrayList<Logger>();
			al.add(log);
			Logger[] logs = new Logger[al.size()] ;

			logs = al.toArray(logs);
			dao.logMessage(logs);

			return newActivityInstance;
		} catch (Exception e) {
			System.out.println("SOME ERROR CREATING NE ACTIVITY INSTANCE IN REACH SERVICE - CREATEACTIVITYINSTANCE");
			e.printStackTrace();
			return null;

		}
	}

	@Override
	public ActivityInstance updateActivityInstance(String requestBody) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
			mapper.setDateFormat(format);

			JsonNode activityInstanceAsTree = mapper.readTree(requestBody);
			String activityInstanceType = activityInstanceAsTree.get("instanceOf").get("name").asText();

			ActivityInstance instance;
			if(activityInstanceType.equals("MakeBelieve")){ // todo Need to find a more elegant way to do this
				instance = mapper.readValue(requestBody, MakeBelieveActivityInstance.class);
				instance.setUpdatedAt(new Date());

			}else if(activityInstanceType.equals("FaceIt")){
				instance = mapper.readValue(requestBody, FaceitActivityInstance.class);

				//List<FaceItModel> faceItList=faceItInstance.getFaceItChallenges();
				//if the size of the faceItList is more than one then that means the payload is improper 
				//and the error needs to be handled
				if(dao.updateFaceitActivityInstance(instance)) {
					return instance;
				}
				return NullObjects.getNullActivityInstance();
			}else if(activityInstanceType.equals("DailyDiary")){
				instance = mapper.readValue(requestBody, DailyDiaryActivityInstance.class);
				instance.setUpdatedAt(new Date());   	
			}else if(activityInstanceType.equals("SWAP")){
				instance = mapper.readValue(requestBody, SwapActivityInstance.class);
				instance.setUpdatedAt(new Date());
			}else if(activityInstanceType.equals("WorryHeads")){
				instance = mapper.readValue(requestBody, WorryHeadsActivityInstance.class);
				instance.setUpdatedAt(new Date());
			}else if(activityInstanceType.equals("StandUp")){
				instance = mapper.readValue(requestBody, StandUpActivityInstance.class);
				instance.setUpdatedAt(new Date());  
			}else{
				instance  = mapper.readValue(requestBody, ActivityInstance.class);
				instance.setUpdatedAt(new Date());      
			}
			instance.setUserSubmissionTime(new Date());

			// Code to log state of activity instance in the Mongo...

			String trialTitle = "Compass"; // Refactor : needs to be done in a better way...
			SimpleDateFormat timeStampFormat = new SimpleDateFormat("MM.dd.YYYY HH:mm:ss", Locale.US);
			String date = timeStampFormat.format(new Date());
			Integer ppin= instance.getPatientPin();
			String metaData = "{ \"activityInstanceId :\" \"" +instance.getActivityInstanceId() +"\" , \"ACTIVITY_INSTANCE_STATE\" : \""+ instance.getState() +"\" } " ;
			Logger log = new Logger(dao.getTrialIdByTitle(trialTitle),date,"INFO","ACTIVITY_STATE","JSON",
					instance.getInstanceOf().getName(),ppin.toString(),metaData);

			ArrayList<Logger> al = new ArrayList<Logger>();
			al.add(log);
			Logger[] logs = new Logger[al.size()] ;

			logs = al.toArray(logs);
			dao.logMessage(logs);

			if(dao.updateActivityInstance(instance)){
				return instance;
			}
			return NullObjects.getNullActivityInstance();
		} catch (NullPointerException ne){
			return NullObjects.getNullActivityInstance();
		}catch (Exception e) {
			System.out.println("Error from updateActivityInstance() in ReachService");
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public ActivityInstance deleteActivityInstance(String activityInstanceId) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			return dao.deleteActivityInstance(activityInstanceId);
		} catch (Exception e) {
			System.out.println("SOME PROBLEM IN REACH SERVICE DELETE ACTIVITY INSTANCE");
			e.printStackTrace();
			return null;
		}
	}

	/****************************************  Service methods for Domain  ********************************************/
	@Override
	public List<Domain> getDomains() {
		try {
			DAO dao = DAOFactory.getTheDAO();

			return dao.getDomains();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Domain getDomain(String id) {
		try {
			DAO dao = DAOFactory.getTheDAO();

			return dao.getDomain(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Domain addDomain(String title, String description, String state) {

		try {
			DAO dao = DAOFactory.getTheDAO();
			Domain instance = new Domain(title, description, state);
			instance.setCreatedAt(new Date());
			if (instance.getState() == null) instance.setState(DomainState.CREATED.state());

			return dao.createDomain(instance);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String addTestDomain(String title, String description, String state) {
		return null;
	}

	/****************************************  Service methods for Patient  *******************************************/
	@Override
	public List<Patient> getPatients(String trialId) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			List<Patient> result;

			if (trialId == null) {
				// return list of all patients present
				result = dao.getPatients();
			} else {
				// return list of patients for given trialId
				result = dao.getPatients(trialId);
			}

			return result;
		} catch (Exception e) {
			System.out.println("SOME PROBLEM WITH REACH SERVICE - GET PATIENTS");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Patient getPatient(int patientPin) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			return dao.getPatient(patientPin);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Patient createPatient(String trialId) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			return dao.createPatient(trialId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Patient updatePatient(Patient patient) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			Patient patientInDatabase = dao.getPatient(patient.getPin());
			if (patientInDatabase == null || patientInDatabase.equals(NullObjects.getNullPatient()))
				return patientInDatabase;

			patientInDatabase.setStartDate(
					patient.getStartDate() != null ? patient.getStartDate() : patientInDatabase.getStartDate());
			patientInDatabase.setEndDate(
					patient.getEndDate() != null ? patient.getEndDate() : patientInDatabase.getEndDate());
			patientInDatabase.setState(
					patient.getState() != null ? patient.getState() : patientInDatabase.getState());
			patientInDatabase.setCreatedAt(
					patient.getCreatedAt() != null ? patient.getCreatedAt() : patientInDatabase.getCreatedAt());
			patientInDatabase.setUpdatedAt(new Date());

			return dao.updatePatient(patientInDatabase);
		} catch (Exception e) {
			System.out.println("SOME PROBLEM IN UPDATE PATIENT IN REACHSERVICE");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String deletePatient(String patientPin) {
		return "DELETE PATIENT";
	}

	/****************** Patient DeviceId update ********************************/

	public Patient updatePatientDeviceId(int patientPin , String regiToken) {
		try {
			DAO dao = DAOFactory.getTheDAO();

			Patient p = getPatient(patientPin);
			p.getRegistrationToken().add(regiToken);
			return dao.updatePatient(p);
		}catch(Exception e) {
			System.out.println("PROBLEM IN ADDING DEVICE ID TOKEN.");
			e.printStackTrace();
			return null;
		}

	}
	/****************************************  Service methods for Trial  *********************************************/

	@Override
	public List<Trial> getTrials(String domain) {
		HEALResponse response = null;
		try {
			DAO dao = DAOFactory.getTheDAO();
			List<Trial> trials = null;

			if (domain == null)
				trials = dao.getTrials();
			else
				trials = dao.getTrials(domain);

			return trials;
		} catch (Exception e) {
			System.out.println("SOME ERROR IN GETTRIALS() IN REACHSERVICE CLASS");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Trial addTrial(Trial trialInstance) {
		try {
			DAO dao = DAOFactory.getTheDAO();

			// check if the domain exist, if yes get the id of domain
			Domain domain = dao.getDomain(trialInstance.getDomainId());
			if (domain != null) {

				Date startDateFormat = new SimpleDateFormat(ReachService.DATE_FORMAT).parse(trialInstance.getStartDate().toString());
				Date endDateFormat = new SimpleDateFormat(ReachService.DATE_FORMAT).parse(trialInstance.getEndDate().toString());

				trialInstance.setUpdatedAt(new Date());
				trialInstance.setCreatedAt(new Date());
				trialInstance.setStartDate(startDateFormat);
				trialInstance.setEndDate(endDateFormat);
				trialInstance.setDomainId(domain.getDomainId());

				return dao.createTrial(trialInstance);
			} else {
				return NullObjects.getNullTrial();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/****************************************  Service methods for Logger *********************************************/
	@Override
	public Logger[] logMessage (Logger[] loggerInstance) {
		try {
			DAO dao = DAOFactory.getTheDAO();

			Logger[] logger = dao.logMessage(loggerInstance);
			return logger;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
      /****************************************  Notification methods  *************************************************/
    // Reference 1: http://developine.com/how-to-send-firebase-push-notifications-from-app-server-tutorial/
    // Reference 2: https://firebase.google.com/docs/cloud-messaging/send-message
    public void sendNotification(NotificationData data, int patientPin) {

        try {
        	DAO dao = DAOFactory.getTheDAO();
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(
                    "https://fcm.googleapis.com/fcm/send");

            NotificationRequestModel notificationRequestModel = new NotificationRequestModel();
            notificationRequestModel.setData(data);
            //TODO: get token from db for pin
            Patient p = dao.getPatient(patientPin);
            ArrayList<String> registrationToken = p.getRegistrationToken();
            notificationRequestModel.setTo(registrationToken);
//            notificationRequestModel.setTo("fxxJWeK-Fo8:APA91bG_-82urLmUgfZwGfY1QA4REuXZzzQojqu9Q4FzUVo3PScT-" +
//                    "UFAEK9PSBeb2X6sRQvu6pZYfqRFeY5p3Zv2t0fqFOzFmcMBPx5nRq9GMRFzb-LuselMuS97bbmuVOzlk76_VJVu");


            ObjectMapper mapper = new ObjectMapper();
            String notificationJson = mapper.writeValueAsString(notificationRequestModel);

            StringEntity input = new StringEntity(notificationJson);
            input.setContentType("application/json");
            System.out.println(input);

            //TODO: server key of your firebase project goes here in header field.
            // You can get it from firebase console.
            postRequest.addHeader("Authorization", "key=AAAAX5CbDOM:APA91bGd_AzSXfn64BsrxT1KEfCnh_yy99lXKPFo7l" +
                    "QUbGqM7tK0YU_YOUUO0X2lpJmMSmVkxZC6JPFkFeC6TimZFg0BsXsutnVhsGM-Ydp2ZFCVswMMnhHrzKbMZpTwKDyZU2XllSZn");
            postRequest.setEntity(input);

            System.out.println("reques:" + notificationJson);

            HttpResponse response = httpClient.execute(postRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("Unsuccessful");
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            } else if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("Successful");
                System.out.println("response:" + EntityUtils.toString(response.getEntity()));

            }
        } catch (RuntimeException runtimeException) {
            runtimeException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
