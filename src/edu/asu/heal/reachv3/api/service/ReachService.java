package edu.asu.heal.reachv3.api.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asu.heal.reachv3.api.models.*;
import edu.asu.heal.reachv3.api.models.schedule.ActivityScheduleJSON;
import edu.asu.heal.reachv3.api.models.schedule.AvailableTime;
import edu.asu.heal.reachv3.api.models.schedule.ModuleJSON;
import edu.asu.heal.reachv3.api.models.schedule.PatientScheduleJSON;
import edu.asu.heal.reachv3.api.models.schedule.ScheduleArrayJSON;
import edu.asu.heal.reachv3.api.notification.INotificationInterface;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
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

import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReachService implements HealService {

	private static final String DATE_FORMAT = "MM/dd/yyyy";
	private static String days;
	private static String TRIAL_NAME="Compass";
	private static String MODULE="module";
	private static String DAY="day";
	private static String MODULE_LENGTH="moduleLength";
	private static String UI_L1_MIN = "level_1.minValDivisor";
	private static String UI_L1_MAX = "level_1.maxValSubtrahend";
	private static String UI_L2_MIN = "level_2.minValSubtrahend";
	private static String UI_L2_MAX = "level_2.maxValSubtrahend";
	private static String BLOB_TRICKS_DAYS = "blobTricks.day.list";
	private static String SKILL_WH_L1_MIN = "WorryHeads.skill_level_1_min";
	private static String SKILL_WH_L1_MAX = "WorryHeads.skill_level_1_max";
	private static String SKILL_WH_L2_MIN = "WorryHeads.skill_level_2_min";
	private static String SKILL_WH_L2_MAX = "WorryHeads.skill_level_2_max";
	private static String SKILL_SU_L1_MIN = "StandUp.skill_level_1_min";
	private static String SKILL_SU_L1_MAX = "StandUp.skill_level_1_max";
	private static String SKILL_SU_L2_MIN = "StandUp.skill_level_2_min";
	private static String SKILL_SU_L2_MAX = "StandUp.skill_level_2_max";
	private static String SKILL_MB_L1_MIN = "MakeBelieve.skill_level_1_min";
	private static String SKILL_MB_L1_MAX = "MakeBelieve.skill_level_1_max";
	private static String SKILL_MB_L2_MIN = "MakeBelieve.skill_level_2_min";
	private static String SKILL_MB_L2_MAX = "MakeBelieve.skill_level_2_max";
	private static String MB_RESET_COUNT = "MakeBeleieve.reset.count";
	private static String WH_RESET_COUNT = "WorryHeads.reset.count";
	private static String SU_RESET_COUNT = "StandUp.reset.count";

	private static Properties _properties;
	static {
		_properties = new Properties();
		try {
			InputStream propFile = ReachService.class.getResourceAsStream("notificationRule.properties");
			_properties.load(propFile);
			propFile.close();
			days = _properties.getProperty(BLOB_TRICKS_DAYS);
		} catch (Throwable t) {
			t.printStackTrace();
			try {
				throw new Exception(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

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
			System.out.println(dao);
			List<ActivityInstance> instances = dao.getScheduledActivities(patientPin);

			return instances;
		} catch (Exception e) {
			System.out.println("SOME ERROR IN GETACTIVITYINSTANCES() IN REACHSERVICE");
			e.printStackTrace();
			return null;
		}
	}

	public String getEmotionsActivityInstance(int patientPin, String emotion, int intensity) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			MappingInterface mapper = MappingFactory.getTheMapper();
			String intensityVal = (String)mapper.intensityMappingToDifficultyLevel(intensity);

			String sessionId = "";

			PatientScheduleJSON patientSchedule = dao.getSchedule(patientPin);
			HashMap<String, Integer> modules = getModuleAndDay(patientSchedule, new Date());
			Integer module =-1;

			if (modules != null && modules.size() > 0 && modules.containsKey(this.MODULE)
					&& modules.get(this.MODULE) != null) {
				module = modules.get(this.MODULE);
				sessionId = patientSchedule.getSchedule().get(module).getModule();
			}
			List<String> results = dao.getEmotionsActivityInstance(emotion.toLowerCase(), intensityVal, sessionId);
			if(results == null || results.size() == 0)
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

		} catch (Exception e) {
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

			String trialTitle = TRIAL_NAME; // Refactor : needs to be done in a better way...
			SimpleDateFormat timeStampFormat = new SimpleDateFormat("MM.dd.YYYY HH:mm:ss", Locale.US);
			String date = timeStampFormat.format(new Date());
			Integer ppin = instance.getPatientPin();
			String metaData = "{ \"activityInstanceId :\" \"" + instance.getActivityInstanceId() + "\" , \"ACTIVITY_INSTANCE_STATE\" : \"" + ActivityInstanceStatus.IN_EXECUTION.status() + "\" } ";
			Logger log = new Logger(dao.getTrialIdByTitle(trialTitle), date, "INFO", "ACTIVITY_INSTANCE_STATE", "JSON",
					instance.getInstanceOf().getName(), ppin.toString(), metaData);

			ArrayList<Logger> al = new ArrayList<Logger>();
			al.add(log);
			Logger[] logs = new Logger[al.size()];

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

			if (activityInstance.getInstanceOf().getName().equals("MakeBelieve")) { //todo need a more elegant way of making the check whether it is of type make believe
				activityInstance =
						new MakeBelieveActivityInstance(activityInstance.getActivityInstanceId(),
								activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
								activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
								activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
								activityInstance.getInstanceOf(), activityInstance.getState(),
								activityInstance.getPatientPin(), dao.getMakeBelieveSituation(), activityInstance.getActivityGlowing());
			} else if (activityInstance.getInstanceOf().getName().equals("FaceIt")) {
				activityInstance = new FaceitActivityInstance(
						activityInstance.getActivityInstanceId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getInstanceOf(), activityInstance.getState(),
						activityInstance.getPatientPin(), dao.getFaceItChallenges(), activityInstance.getActivityGlowing()
						);
			} else if (activityInstance.getInstanceOf().getName().equals("DailyDiary")) {
				activityInstance = new DailyDiaryActivityInstance(
						activityInstance.getActivityInstanceId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getInstanceOf(), activityInstance.getState(),
						activityInstance.getPatientPin(), activityInstance.getActivityGlowing()
						);
			} else if (activityInstance.getInstanceOf().getName().equals("STOP")) {
				activityInstance = new SwapActivityInstance(activityInstance.getActivityInstanceId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getInstanceOf(), activityInstance.getState(),
						activityInstance.getPatientPin(), activityInstance.getActivityGlowing()
						);
			} else if (activityInstance.getInstanceOf().getName().equals("WorryHeads")) {
				activityInstance = new WorryHeadsActivityInstance(
						activityInstance.getActivityInstanceId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getInstanceOf(), activityInstance.getState(),
						activityInstance.getPatientPin(), dao.getAllWorryHeadsSituations(), activityInstance.getActivityGlowing());
			} else if (activityInstance.getInstanceOf().getName().equals("StandUp")) {
				activityInstance = new StandUpActivityInstance(
						activityInstance.getActivityInstanceId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getInstanceOf(), activityInstance.getState(),
						activityInstance.getPatientPin(), dao.getStandUpSituations(), activityInstance.getActivityGlowing());
			}else if (activityInstance.getInstanceOf().getName().equals("SUDS")) {
				activityInstance = new SUDSActivityInstance(
						activityInstance.getActivityInstanceId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getInstanceOf(), activityInstance.getState(),
						activityInstance.getPatientPin(), 
						activityInstance.getActivityGlowing(), dao.getSUDSQuestion());
			}
			ActivityInstance newActivityInstance = dao.createActivityInstance(activityInstance);

			try {
				// Code to add AI in patient schedule
				String activityInstanceId = newActivityInstance.getActivityInstanceId();
				Integer patientPin = newActivityInstance.getPatientPin();

				if (dao.getSchedule(patientPin) != null) {
					PatientScheduleJSON patientSchedule = dao.getSchedule(patientPin);
					Date today = new Date();
					HashMap<String, Integer> map = getModuleAndDay(patientSchedule, today);
					Integer module =-1, dayOfModule =-1, moduleLen =-1;
					if(map != null && map.size() > 0) {
						if (map.containsKey(this.MODULE) && map.get(this.MODULE) != null)
							module = map.get(this.MODULE);
						if (map.containsKey(this.DAY) && map.get(this.DAY) != null)
							dayOfModule=map.get(this.DAY);
						if (map.containsKey(this.MODULE_LENGTH) && map.get(this.MODULE_LENGTH) != null)
							moduleLen = map.get(this.MODULE_LENGTH);
					}
					if(module != -1 && patientSchedule != null
							&& patientSchedule.getSchedule() != null
							&& patientSchedule.getSchedule().size() > module) {
						ScheduleArrayJSON schedule = patientSchedule.getSchedule().get(module).getSchedule().get(dayOfModule);
						ArrayList<ActivityScheduleJSON> actList = schedule.getActivitySchedule();
						if (actList.size() > 0) {
							int indexOfActivity = -1;
							for (ActivityScheduleJSON obj : actList) {
								indexOfActivity++;
								if (obj.getActivity().equals(newActivityInstance.getInstanceOf().getName())) {
									if (dao.updateActivityInstanceInPatientSchedule(patientPin, module, dayOfModule, indexOfActivity, activityInstanceId)) {
										System.out.println("AI is successfully updated in Schedule");
										break;
									} else {
										System.out.println("AI update is failed in schedule");
									}

								}
							}
						}
					}else {
						// do nothing
					}
				} else {
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// Code to log state of activity instance in the Mongo...

				String trialTitle = TRIAL_NAME; // Refactor : needs to be done in a better way...
				SimpleDateFormat timeStampFormat = new SimpleDateFormat("MM.dd.YYYY HH:mm:ss", Locale.US);
				String date = timeStampFormat.format(new Date());
				Integer ppin = newActivityInstance.getPatientPin();
				String metaData = "{ \"activityInstanceId :\" \"" + activityInstance.getActivityInstanceId() + "\" , " +
						"\"ACTIVITY_INSTANCE_STATE\" : \"" + ActivityInstanceStatus.CREATED.status() + "\" } ";
				Logger log = new Logger(dao.getTrialIdByTitle(trialTitle), date, "INFO", "ACTIVITY_INSTANCE_STATE",
						"JSON", activityInstance.getInstanceOf().getName(), ppin.toString(), metaData);

				ArrayList<Logger> al = new ArrayList<Logger>();
				al.add(log);
				Logger[] logs = new Logger[al.size()];

				logs = al.toArray(logs);
				dao.logMessage(logs);

				return newActivityInstance;
			}


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
			if (activityInstanceType.equals("MakeBelieve")) { // todo Need to find a more elegant way to do this
				instance = mapper.readValue(requestBody, MakeBelieveActivityInstance.class);
				instance.setUpdatedAt(new Date());

			} else if (activityInstanceType.equals("FaceIt")) {
				instance = mapper.readValue(requestBody, FaceitActivityInstance.class);

				//List<FaceItModel> faceItList=faceItInstance.getFaceItChallenges();
				//if the size of the faceItList is more than one then that means the payload is improper 
				//and the error needs to be handled
				if (dao.updateFaceitActivityInstance(instance)) {
					return instance;
				}
				return NullObjects.getNullActivityInstance();
			} else if (activityInstanceType.equals("DailyDiary")) {
				instance = mapper.readValue(requestBody, DailyDiaryActivityInstance.class);
				instance.setUpdatedAt(new Date());
			} else if (activityInstanceType.equals("STOP")) {
				instance = mapper.readValue(requestBody, SwapActivityInstance.class);
				instance.setUpdatedAt(new Date());
			} else if (activityInstanceType.equals("WorryHeads")) {
				instance = mapper.readValue(requestBody, WorryHeadsActivityInstance.class);
				instance.setUpdatedAt(new Date());
			} else if (activityInstanceType.equals("StandUp")) {
				instance = mapper.readValue(requestBody, StandUpActivityInstance.class);
				instance.setUpdatedAt(new Date());  
			}else if(activityInstanceType.equals("Emotion")){
				instance = mapper.readValue(requestBody, Emotions.class);
				instance.setUpdatedAt(new Date());  
			}else if(activityInstanceType.equals("SUDS")){
				instance = mapper.readValue(requestBody, SUDSActivityInstance.class);
				instance.setUpdatedAt(new Date());  
			}else{
				instance  = mapper.readValue(requestBody, ActivityInstance.class);
				instance.setUpdatedAt(new Date());      
			}
			instance.setUserSubmissionTime(new Date());

			// Code to log state of activity instance in the Mongo...

			String trialTitle = TRIAL_NAME; // Refactor : needs to be done in a better way...
			SimpleDateFormat timeStampFormat = new SimpleDateFormat("MM.dd.YYYY HH:mm:ss", Locale.US);
			String date = timeStampFormat.format(new Date());
			Integer ppin = instance.getPatientPin();
			String metaData = "{ \"activityInstanceId :\" \"" + instance.getActivityInstanceId() + "\" , \"ACTIVITY_INSTANCE_STATE\" : \"" + instance.getState() + "\" } ";
			Logger log = new Logger(dao.getTrialIdByTitle(trialTitle), date, "INFO", "ACTIVITY_INSTANCE_STATE", "JSON",
					instance.getInstanceOf().getName(), ppin.toString(), metaData);

			ArrayList<Logger> al = new ArrayList<Logger>();
			al.add(log);
			Logger[] logs = new Logger[al.size()];

			logs = al.toArray(logs);
			dao.logMessage(logs);

			if (dao.updateActivityInstance(instance)) {
				//
				if(instance.getState().equals(ActivityInstanceStatus.COMPLETED.status())) {

					PatientScheduleJSON patientSchedule = dao.getSchedule(instance.getPatientPin());
					HashMap<String, Integer> modules = getModuleAndDay(patientSchedule, new Date());
					int module = -1;
					int days = -1;
					if (modules != null && modules.size() > 0) {
						if (modules.containsKey(this.MODULE) && modules.get(this.MODULE) != null)
							module = modules.get(this.MODULE);
						if (modules.containsKey(this.DAY) && modules.get(this.DAY) != null)
							days = modules.get(this.DAY);
					}

					if (module != -1 && patientSchedule != null
							&& patientSchedule.getSchedule() != null
							&& patientSchedule.getSchedule().size() > 0) {
						ArrayList<ActivityScheduleJSON> activityScheduleJSON = patientSchedule.getSchedule().get(module).getSchedule().get(days).getActivitySchedule();
						for (int i = 0; i < activityScheduleJSON.size(); i++) {
							String activityName = activityScheduleJSON.get(i).getActivity();
							if (instance.getInstanceOf().getName().equals(activityName)) {
								int score = activityScheduleJSON.get(i).getScore() + instance.getResponseCount();
								int actualCount = activityScheduleJSON.get(i).getActualCount() + 1;
								if (dao.updatePatientScoreActualCount(ppin, module, days, i, score, actualCount)) {
									System.out.println("Update sucessful");
								} else {
									System.out.println("Update failed.");
								}

							}
						}
					}

					//patientSchedule.getSchedule().get(0).getSchedule().get(0).getActivitySchedule().get(0).getScore();

				}

				//
				return instance;
			}
			return NullObjects.getNullActivityInstance();
		} catch (NullPointerException ne) {
			return NullObjects.getNullActivityInstance();
		} catch (Exception e) {
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
	public Patient createPatient(String trialId, int patientPin) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			//			MainSchedule schedule = new MainSchedule();
			ObjectMapper mapper = new ObjectMapper();
			//			String json = mapper.writeValueAsString(schedule);
			System.out.println("Schedule JSON is : " );
			System.out.println("-------------------------");
			//			System.out.println(json);
			return dao.createPatient(trialId,patientPin);
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

	public Patient updatePatientDeviceId(int patientPin, String regiToken) {
		try {
			DAO dao = DAOFactory.getTheDAO();

			Patient p = getPatient(patientPin);
			p.getRegistrationToken().add(regiToken);
			return dao.updatePatient(p);
		} catch (Exception e) {
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

	@Override
	public String getTrialIdByTitle(String trialName) {
		try {
			DAO dao = DAOFactory.getTheDAO();

			String trialId = dao.getTrialIdByTitle(trialName);
			return trialId;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/****************************************  Service methods for Logger *********************************************/
	@Override
	public Logger[] logMessage(Logger[] loggerInstance) {
		try {
			DAO dao = DAOFactory.getTheDAO();

			Logger[] logger = dao.logMessage(loggerInstance);
			return logger;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Logger[] logPersonalizationMessage(Logger[] loggerInstance) {
		try {
			DAO dao = DAOFactory.getTheDAO();

			Logger[] logger = dao.logPersonalizationMessage(loggerInstance);
			return logger;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/****************************************  Personalization methods  *************************************************/

	@Override
	public boolean personalizeUserExperience(int patientPin) {
		//Fetch schedule for passed pin

		// check available time and current time match?

		// if available time = current time
		// check status of completion and decide type of notification to be sent
		// check if notification already sent
		// if not, then send it
		boolean rval = false;
		try {
			DAO dao = DAOFactory.getTheDAO();
			PatientScheduleJSON patientScheduleJSON = dao.getSchedule(patientPin);
			if(patientScheduleJSON == null
					|| patientScheduleJSON.getSchedule() == null
					|| patientScheduleJSON.getSchedule().size() == 0) {
				return false;
			}

			Integer module =-1;
			Integer dayOfModule =-1;
			Integer moduleLen=0;

			Date today = new Date();//new SimpleDateFormat(ReachService.DATE_FORMAT).parse(.toString());
			DateFormat dateFormat = new SimpleDateFormat("HH");
			Integer currHour = Integer.valueOf(dateFormat.format(today));

			// create method  to get module and day of module - done
			HashMap<String, Integer> map = this.getModuleAndDay(patientScheduleJSON,today);

			if(map != null && map.size() > 0) {
				if (map.containsKey(this.MODULE) && map.get(this.MODULE) != null)
					module = map.get(this.MODULE);
				if (map.containsKey(this.DAY) && map.get(this.DAY) != null)
					dayOfModule = map.get(this.DAY);
				if (map.containsKey(this.MODULE_LENGTH) && map.get(this.MODULE_LENGTH) != null)
					moduleLen=map.get(this.MODULE_LENGTH);
			}

			if(module ==-1) {
				rval = false;// No module selected so no trials for this patient pin
			}else {
				if(dayOfModule != 0) {

					int l1_min = Integer.parseInt(_properties.getProperty(UI_L1_MIN));
					int l1_max = Integer.parseInt(_properties.getProperty(UI_L1_MAX));
					int l2_min = Integer.parseInt(_properties.getProperty(UI_L2_MIN));
					int l2_max = Integer.parseInt(_properties.getProperty(UI_L2_MAX));

					int l1_minVal = Double.valueOf(Math.floor(moduleLen/l1_min)).intValue();

					// Array list of all activities which needs L2 Notification
					ArrayList<String> levelTwoNotifActivities = new ArrayList<>();

					// get schedule array for specific module
					ArrayList<ScheduleArrayJSON> schedule = patientScheduleJSON.getSchedule()
							.get(module).getSchedule();

					// get schedule array for specific day
					ArrayList<ActivityScheduleJSON> activityList = schedule.get(dayOfModule).getActivitySchedule();

					// check for each activity
					int indexOfActivity =-1;
					for(ActivityScheduleJSON activity : activityList) {
						indexOfActivity++;

						ArrayList<AvailableTime> time = activity.getAvailableTime();

						// Check if cutrrent time is in available time
						for(AvailableTime t : time) {

							if(currHour >= t.getFrom() && currHour <= t.getTo()) {

								// Configure for daily and weekly activities
								if(activity.isDailyActivity()) {

									// check if not done for minimum count
									if(activity.getActualCount() < activity.getMinimumCount()) {

										int notDoneDays = this.getNotDoneDays(schedule,activity.getActivity(),dayOfModule);

										// Check if L1 or L2 notif needs to be sent
										if(activity.getLevelOfUIPersonalization() != 1 &&
												(l1_minVal == (moduleLen-l1_max) && notDoneDays == l1_minVal)
												|| (notDoneDays >= l1_minVal
													&& notDoneDays < (moduleLen-l1_max))) {

											System.out.println("In send le notif");

											// Send L1 notification
											if(sendLevelOneNotification(patientPin, module,
													dayOfModule, indexOfActivity, notDoneDays, activity)) {
												System.out.println("L1 SENT for PIN : " + patientPin);
											}else {
												System.out.println("L1 NOT_SENT for PIN : "+patientPin);
											}
										}else if(notDoneDays >= (moduleLen-l2_min) && notDoneDays <=moduleLen-l2_max
												&& activity.getLevelOfUIPersonalization() != 2) {
											//L2
											levelTwoNotifActivities.add(activity.getActivity());
											
										}

									} else {
										System.out.println("Patient is doing well for activity: "
												+ activity.getActivity()
												+ " , do not send notification");
									}
								} else {
									//code for SWAP - non daily activity
									// Calculate instances of activities completed for weekly activity
									// and compare with minimum count

									int totalActualCount = this.getTotalActualCountOfActivity(schedule, activity.getActivity(), dayOfModule);

									if(totalActualCount < activity.getMinimumCount()) {

										if(activity.getLevelOfUIPersonalization() != 1 &&
												(l1_minVal == (moduleLen-l1_max) && dayOfModule == l1_minVal)
												|| ((l1_minVal != (moduleLen-l1_max))
													&& (dayOfModule >= l1_minVal
														&& dayOfModule < (moduleLen-l1_max)))) {
											// Send L1
											if(sendLevelOneNotification(patientPin, module,
													dayOfModule, indexOfActivity, 0, activity)) {
												System.out.println("L1 SENT for PIN : " + patientPin);
											}else {
												System.out.println("L1 NOT_SENT for PIN : "+patientPin);
											}
										}else if(dayOfModule >= (moduleLen-l2_min) && dayOfModule <=moduleLen-l2_max
												&& activity.getLevelOfUIPersonalization() != 2) {
											//L2
											levelTwoNotifActivities.add(activity.getActivity());
										}
									}else {
										System.out.println("Patient is doing well for activity: "
												+ activity.getActivity()
												+ " , do not send notification");
									}
								}
							}
						}
					}
					if(!levelTwoNotifActivities.isEmpty()) {
						sendLevelTwoNotification(patientPin, module, dayOfModule, levelTwoNotifActivities);
					}
				}else {
					System.out.println("It is day 0.");
				}
				rval=true;
			}
		} catch (RuntimeException runtimeException) {
			rval = false;
			//runtimeException.printStackTrace();
		} catch (Exception exception) {
			rval=false;
			//exception.printStackTrace();
		}
		return rval;
	}

	public boolean sendLevelOneNotification(int patientPin,int module, int dayOfModule,
			int indexOfActivity, int days, ActivityScheduleJSON activity) {
		boolean rval = false;
		try {
			String l1_class = _properties.getProperty("level_1.className");
			INotificationInterface notificationClass = null;

			Logger log;
			String trialTitle = TRIAL_NAME; // Refactor : needs to be done in a better way...
			SimpleDateFormat timeStampFormat = new SimpleDateFormat("MM.dd.YYYY HH:mm:ss", Locale.US);
			String date = timeStampFormat.format(new Date());
			Integer ppin = Integer.valueOf(patientPin);
			String metaData ="";
			String level="";
			String activityVal = "ACTIVITY_NAME";
			String levelOfUXP= "LEVEL_OF_PERSONALIZATION";
			String type ="PERSONALIZATION";
			String format = "JSON";
			String subType = "UX";
			String notDoneDays = "NOT_DONE_DAYS";

			DAO dao = DAOFactory.getTheDAO();

			// Level 1 notification
			if (l1_class != null) {
				Class<?> level_1 = Class.forName(l1_class);
				Constructor<?> constructor = level_1.getConstructor();
				notificationClass = (INotificationInterface) constructor.newInstance();
			}
			if(notificationClass != null) {
				if(notificationClass.sendNotification(activity.getActivity(), patientPin,
						days, 1, null)) {
					rval = true;
					metaData = "{ \""+activityVal+ "\": \""+activity.getActivity()+"\","
							+ "\""+levelOfUXP+"\" : \"1\" ,"
							+ "\""+notDoneDays+"\" : "+days+"\" } ";
					level="SENT";
					// Updating level of UI personalization in schedule
					activity.setLevelOfUIPersonalization(1);
					if(dao.updateUIPersonalization(patientPin, module, dayOfModule,indexOfActivity,1))
						System.out.println("Update successful");
					else
						System.out.println("Update failed.");	//May need to do something here. Also, add to logs - Vishakha
				}

			}
			else {
				metaData = "{ \"" + activityVal + "\": \"" + activity.getActivity() + "\","
						+ "\"" + levelOfUXP + "\" : \"1\" ,"
						+ "\"" + notDoneDays + "\" : " + days + "\" } ";
				level = "NOT_SENT";
				System.out.println("Notification class not set for level 1.");
			}
			if(!metaData.equals("")) {
				log = new Logger(dao.getTrialIdByTitle(trialTitle),date,level,
						type, format, subType, ppin.toString(), metaData);
				ArrayList<Logger> al = new ArrayList<Logger>();
				al.add(log);
				Logger[] logs = new Logger[al.size()];

				logs = al.toArray(logs);
				dao.logPersonalizationMessage(logs);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rval;
	}

	public boolean sendLevelTwoNotification(int patientPin,int module, int dayOfModule,
			List<String> list) {
		boolean rval = false;
		try {
			Logger log;
			String trialTitle = TRIAL_NAME; // Refactor : needs to be done in a better way...
			SimpleDateFormat timeStampFormat = new SimpleDateFormat("MM.dd.YYYY HH:mm:ss", Locale.US);
			String date = timeStampFormat.format(new Date());
			Integer ppin = Integer.valueOf(patientPin);
			String metaData ="";
			String level="";
			String activityVal = "ACTIVITY_NAME";
			String levelOfUXP= "LEVEL_OF_PERSONALIZATION";
			String type ="PERSONALIZATION";
			String format = "JSON";
			String subType = "UX";
			String notDoneDays = "NOT_DONE_DAYS";

			String l2_class = _properties.getProperty("level_2.className");
			INotificationInterface notificationClass = null;
			DAO dao = DAOFactory.getTheDAO();
			String activityName = "notifLandingPage";	

				// Level 2
				if (l2_class != null) {
					Class<?> level_2 = Class.forName(l2_class);
					Constructor<?> constructor = level_2.getConstructor();
					notificationClass = (INotificationInterface) constructor.newInstance();
//				}
				if(notificationClass != null) {
					if(notificationClass.sendNotification(activityName, patientPin, 0, 2, list)) {
						rval =true;
						metaData = "{ \""+activityVal+ "\": \""+activityName+"\","
								+ "\""+levelOfUXP+"\" : \"2\" ,"
								+ "\"activityList\" : " + list.toString()
								+ "\""+notDoneDays+"\" : "+days+"\" } ";
						level="SENT";
						// Updating level of UI personalization in schedule
						// For update we have to do iteratively.
					//	activity.setLevelOfUIPersonalization(2);
						
						if(dao.updateLvlTwoUIPersonalization(patientPin, module, dayOfModule,list,2))
							System.out.println("Update successful");
						else 
							System.out.println("Update failed.");
					}

				}
				else {
					metaData = "{ \""+activityVal+ "\": \""+activityName+"\","
							+ "\""+levelOfUXP+"\" : \"2\" ,"
							+ "\"activityList\" : " + list.toString()
							+ "\""+notDoneDays+"\" : "+days+"\" } ";
					level="NOT_SENT";
					System.out.println("Notification class not set for level 2");
				}
			}
			else {
				// Do nothing
			}
			if(!metaData.equals("")) {
				log = new Logger(dao.getTrialIdByTitle(trialTitle),date,level,
						type, format, subType, ppin.toString(), metaData);
				ArrayList<Logger> al = new ArrayList<Logger>();
				al.add(log);
				Logger[] logs = new Logger[al.size()];

				logs = al.toArray(logs);
				dao.logPersonalizationMessage(logs);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rval;
	}

	private int getTotalActualCountOfActivity(ArrayList<ScheduleArrayJSON> schedule, String activity, int dayOfModule) {

		int counter = dayOfModule;
		int totalActualCount =0;
		while(counter >= 0) {
			ScheduleArrayJSON obj = schedule.get(counter);
			ArrayList<ActivityScheduleJSON> act = obj.getActivitySchedule();
			for(ActivityScheduleJSON temp : act) {
				if(temp.getActivity().equals(activity)) {
					totalActualCount += temp.getActualCount();
				}
			}
			counter--;
		}
		return totalActualCount;

	}

	private int getNotDoneDays(ArrayList<ScheduleArrayJSON> schedule, String activity, int dayOfModule) {

		int counter = dayOfModule;
		int rval =0;
		while(counter >= 0) {
			ScheduleArrayJSON obj = schedule.get(counter);
			ArrayList<ActivityScheduleJSON> act = obj.getActivitySchedule();
			for(ActivityScheduleJSON temp : act) {
				if(temp.getActivity().equals(activity)) {
					if(temp.getActualCount() >= temp.getMinimumCount()) {
						return rval;
					}else if(temp.getActualCount() < temp.getMinimumCount()) {
						rval++;
					}
				}
			}
			counter--;
		}
		return rval;
	}

	private HashMap<String, Integer> getModuleAndDay(PatientScheduleJSON patientScheduleJSON, Date today) {

		if (patientScheduleJSON == null
				|| patientScheduleJSON.getSchedule() == null
				|| patientScheduleJSON.getSchedule().size() == 0) {
			return  new HashMap<>();
		}

		ArrayList<ModuleJSON> moduleJson = patientScheduleJSON.getSchedule();

		HashMap<String, Integer> rval = new HashMap<String, Integer>();
		try {
			for(int i =0; i<moduleJson.size(); i++) {

				Date startDate= getDateWithoutTime(moduleJson.get(i).getStartDate());// new SimpleDateFormat(ReachService.DATE_FORMAT).parse(.toString());
				Date endDate = getDateWithoutTime(moduleJson.get(i).getEndDate()); //new SimpleDateFormat(ReachService.DATE_FORMAT).parse(.toString());

				today = getDateWithoutTime(today);

				if(today.compareTo(startDate) >= 0 && today.compareTo(endDate) <=0) {

					rval.put(this.MODULE, Integer.valueOf(moduleJson.get(i).getModule())-1);
					long diffTime = today.getTime() - startDate.getTime();
					Long d = TimeUnit.DAYS.convert(diffTime, TimeUnit.MILLISECONDS);
					rval.put(this.DAY,d.intValue());
					Long moduleLen =TimeUnit.DAYS.convert(endDate.getTime() -
							startDate.getTime(),TimeUnit.MILLISECONDS)+1;
					rval.put(this.MODULE_LENGTH, moduleLen.intValue());
					System.out.println("Map in getModuleAndDay : " + rval);
					break;
				}

			}

			return rval;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date getDateWithoutTime(Date date) throws ParseException {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"dd/MM/yyyy");
			return formatter.parse(formatter.format(date));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getBlobTricks(int patientPin) {
		try {
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
			String day = sdf.format(today);
			DAO dao = DAOFactory.getTheDAO();
			int currVal = dao.getReleasedBlobTricksDAO(patientPin).getCount();
			return currVal;
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	public int updateBlobTricksCount(int patientPin) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			int rval = dao.getReleasedBlobTricksDAO(patientPin).getCount();
			rval++;
			BlobTricks  obj = new BlobTricks(patientPin, rval);
			dao.updateBlobTrickCountDAO(obj);
			return rval;
		}catch(Exception e) {
			System.out.println("Issue in updating blobtrick count.");
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public boolean personalizeSkillSet(int patientPin){

		boolean rval = false;
		try {
			Logger log;
			String trialTitle = TRIAL_NAME; // Refactor : needs to be done in a better way...
			SimpleDateFormat timeStampFormat = new SimpleDateFormat("MM.dd.YYYY HH:mm:ss", Locale.US);
			String date = timeStampFormat.format(new Date());
			Integer ppin = Integer.valueOf(patientPin);
			String metaData ="";
			String level="";
			String activityVal = "ACTIVITY_NAME";
			String levelOfUXP= "LEVEL_OF_PERSONALIZATION";
			String type ="PERSONALIZATION";
			String format = "JSON";
			String subType = "SKILL";

			DAO dao = DAOFactory.getTheDAO();
			PatientScheduleJSON patientScheduleJSON = dao.getSchedule(patientPin);

			if(patientScheduleJSON == null || patientScheduleJSON.getSchedule() == null
					|| patientScheduleJSON.getSchedule().size() == 0) {
				return false;
			}

			Integer module = -1, resetModule=-1;
			Integer dayOfModule = -1, resetDay=-1;
			Integer moduleLen = 0;

			Date today = new Date();//new SimpleDateFormat(ReachService.DATE_FORMAT).parse(.toString());
			DateFormat dateFormat = new SimpleDateFormat("HH");
			Integer currHour = Integer.valueOf(dateFormat.format(today));

			// create method  to get module and day of module - done
			HashMap<String, Integer> map = this.getModuleAndDay(patientScheduleJSON, today);

			if(map != null && map.size() > 0) {
				if (map.containsKey(this.MODULE) && map.get(this.MODULE) != null)
					module = map.get(this.MODULE);
				if (map.containsKey(this.DAY) && map.get(this.DAY) != null)
					dayOfModule = map.get(this.DAY);
				if (map.containsKey(this.MODULE_LENGTH) && map.get(this.MODULE_LENGTH) != null)
					moduleLen=map.get(this.MODULE_LENGTH);
			}
			if (module == -1) {
				// No module selected so no trials for this patient pin
				return false;
			} else {
				ArrayList<ModuleJSON> moduleJson = patientScheduleJSON.getSchedule();
				ArrayList<ScheduleArrayJSON> schedule = moduleJson.get(module).getSchedule();
				ArrayList<ActivityScheduleJSON> activityList = schedule.get(dayOfModule).getActivitySchedule();
				Date resetDate;
				int index =0;

				// check skill personalization for each activity
				for(ActivityScheduleJSON activity : activityList){

					String activityName = activity.getActivity();
					Integer l1_min = 0, l1_max = 0, l2_min = 0, l2_max = 0;
					if(activityName.equals("WorryHeads") || activityName.equals("StandUp") ||
							activityName.equals("MakeBelieve")){

						switch (activityName) {
						case "WorryHeads":
							resetDate= patientScheduleJSON.getWorryHeadsResetDate();
							l1_min = Integer.parseInt(_properties.getProperty(SKILL_WH_L1_MIN));
							l1_max = Integer.parseInt(_properties.getProperty(SKILL_WH_L1_MAX));
							l2_min = Integer.parseInt(_properties.getProperty(SKILL_WH_L2_MIN));
							l2_max = Integer.parseInt(_properties.getProperty(SKILL_WH_L2_MAX));
							break;
						case "StandUp":
							resetDate= patientScheduleJSON.getStandUpResetDate();
							l1_min = Integer.parseInt(_properties.getProperty(SKILL_SU_L1_MIN));
							l1_max = Integer.parseInt(_properties.getProperty(SKILL_SU_L1_MAX));
							l2_min = Integer.parseInt(_properties.getProperty(SKILL_SU_L2_MIN));
							l2_max = Integer.parseInt(_properties.getProperty(SKILL_SU_L2_MAX));
							break;
						case "MakeBelieve":
							resetDate= patientScheduleJSON.getMakeBelieveResetDate();
							l1_min = Integer.parseInt(_properties.getProperty(SKILL_MB_L1_MIN));
							l1_max = Integer.parseInt(_properties.getProperty(SKILL_MB_L1_MAX));
							l2_min = Integer.parseInt(_properties.getProperty(SKILL_MB_L2_MIN));
							l2_max = Integer.parseInt(_properties.getProperty(SKILL_MB_L2_MAX));
							break;
						default:
							continue;

						}


						float totalScore=0,totalActtualCount=0;
						int currModule = module;
						int prevDay = dayOfModule-1;
						HashMap<String, Integer> resetDateMap = this.getModuleAndDay(patientScheduleJSON, resetDate);

						if (resetDateMap != null && resetDateMap.size() > 0) {
							if (map.containsKey(this.MODULE) && map.get(this.MODULE) != null)
								resetModule = resetDateMap.get(this.MODULE);
							if (map.containsKey(this.DAY) && map.get(this.DAY) != null)
								resetDay = resetDateMap.get(this.DAY);
						}

						if (resetModule == -1) {
							// no reset date found in current module
							continue;
						}

						ArrayList<ScheduleArrayJSON> currentModuleSchedule = moduleJson.get(currModule).getSchedule();

						// calculate score till now for current activity
						while((currModule > resetModule)
								|| (currModule == resetModule && prevDay >= resetDay)) {

							if (prevDay < 0) {
								currModule--;
								if (module == -1)
									break;
								currentModuleSchedule = moduleJson.get(currModule).getSchedule();
								prevDay = currentModuleSchedule.size() - 1;
							}

							ArrayList<ActivityScheduleJSON> actList = currentModuleSchedule.get(prevDay)
									.getActivitySchedule();
							for (ActivityScheduleJSON a : actList) {

								if(a.getActivity().equals(activityName)) {
									if (a.getActualCount() > 0) {
										totalScore += a.getScore();
										totalActtualCount += a.getActualCount();
									}
									break;
								}
							}
							prevDay--;
						}

						Double result=0.0;

						// calculate level of personalization for activity
						if(totalActtualCount !=0) {
							result = Double.valueOf((totalScore / totalActtualCount) * 100);

							//set levelOfSkillPersonalization to 1
							if (result >= l1_min && result < l1_max && 
									shouldSetNewSkillSet(patientScheduleJSON, today, activityName)) {

								if (dao.updateLevelOfSkillPersonalization(patientPin, module,
										dayOfModule, index, 1)) {

									metaData = "{ \""+activityVal+ "\": \""+activity.getActivity()+"\","
											+ "\""+levelOfUXP+"\" : \"1\" } ";
									level="UPDATED_SKILL_LEVEL";
									System.out.println("Skill level updated successfully to level 1");
								} else {
									metaData = "{ \""+activityVal+ "\": \""+activity.getActivity()+"\","
											+ "\""+levelOfUXP+"\" : \"1\" } ";
									level="NOT_UPDATED_SKILL_LEVEL";
									System.out.println("Skill level updated FAILED !! to level 1");
								}
								//set levelOfSkillPersonalization to 2
							} else if (result >= l2_min && result < l2_max && 
									shouldSetNewSkillSet(patientScheduleJSON, today, activityName)) {

								if (dao.updateLevelOfSkillPersonalization(patientPin, module,
										dayOfModule, index, 2)) {
									metaData = "{ \""+activityVal+ "\": \""+activity.getActivity()+"\","
											+ "\""+levelOfUXP+"\" : \"2\" } ";
									level="UPDATED_SKILL_LEVEL";
									System.out.println("Skill level updated successfully to level 2");
								} else {
									metaData = "{ \""+activityVal+ "\": \""+activity.getActivity()+"\","
											+ "\""+levelOfUXP+"\" : \"2\" } ";
									level="NOT_UPDATED_SKILL_LEVEL";
									System.out.println("Skill level updated FAILED !!! to level 2");
								}
							}else {
								System.out.println("Skill personalization has already applied for several days.");
							}
						}
					}
					index++;
				}
			}
			if(!metaData.equals("")) {
				log = new Logger(dao.getTrialIdByTitle(trialTitle),date,level,
						type, format, subType, ppin.toString(), metaData);
				ArrayList<Logger> al = new ArrayList<Logger>();
				al.add(log);
				Logger[] logs = new Logger[al.size()];

				logs = al.toArray(logs);
				dao.logPersonalizationMessage(logs);
			}
			rval = true;
		}catch (Exception e){
			rval = false;
			//e.printStackTrace();
		}
		return rval;
	}

	@Override
	public HashMap<String, Boolean> getActivitySchedule(int patientPin) {

		HashMap<String,Boolean> rval = new HashMap<String, Boolean>();

		try {
			DAO dao = DAOFactory.getTheDAO();

			PatientScheduleJSON patientScheduleJSON = dao.getSchedule(patientPin);

			if (patientScheduleJSON == null
					|| patientScheduleJSON.getSchedule() == null
					|| patientScheduleJSON.getSchedule().size() == 0) {
				return new HashMap<>();
			}

			ArrayList<ModuleJSON> moduleJson = patientScheduleJSON.getSchedule();
			Integer module =-1;
			Integer dayOfModule =-1;
			Integer moduleLen=0;

			Date today = new Date();//new SimpleDateFormat(ReachService.DATE_FORMAT).parse(.toString());

			// create method  to get module and day of module - done
			HashMap<String, Integer> map = this.getModuleAndDay(patientScheduleJSON,today);

			if(map != null && map.size() > 0) {
				if (map.containsKey(this.MODULE) && map.get(this.MODULE) != null)
					module = map.get(this.MODULE);
				if (map.containsKey(this.DAY) && map.get(this.DAY) != null)
					dayOfModule = map.get(this.DAY);
				if (map.containsKey(this.MODULE_LENGTH) && map.get(this.MODULE_LENGTH) != null)
					moduleLen=map.get(this.MODULE_LENGTH);
			} else {
				return new HashMap<>();
			}
			ArrayList<ScheduleArrayJSON> schedule = moduleJson.get(module).getSchedule();
			ArrayList<ActivityScheduleJSON> activityList = schedule.get(dayOfModule).getActivitySchedule();
			for(ActivityScheduleJSON activity : activityList) {
				if(activity.getActualCount() < activity.getMinimumCount()) {
					rval.put(activity.getActivity(), true);
				}else {
					rval.put(activity.getActivity(),false);
				}
			}


		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			return rval;
		}
	}


	public boolean shouldSetNewSkillSet(PatientScheduleJSON patientScheduleJSON, Date today, String activityName) {
		try {
			DAO dao = DAOFactory.getTheDAO();
			Integer module=-1, dayOfModule=0, moduleLen=0;
			ArrayList<ModuleJSON> moduleJson = patientScheduleJSON.getSchedule();
			HashMap<String, Integer> map = this.getModuleAndDay(patientScheduleJSON,today);

			if(map != null && map.size() > 0) {
				if (map.containsKey(this.MODULE) && map.get(this.MODULE) != null)
					module = map.get(this.MODULE);
				if (map.containsKey(this.DAY) && map.get(this.DAY) != null)
					dayOfModule = map.get(this.DAY);
				if (map.containsKey(this.MODULE_LENGTH) && map.get(this.MODULE_LENGTH) != null)
					moduleLen=map.get(this.MODULE_LENGTH);
			}

			Integer resetModule=-1, resetDay=0;
			Date resetDate = new Date();

			int resetCount =-1;
			int count =0, levelOfSkill =2;
			int prevDay = dayOfModule-1;
			boolean rval = true;
			int flag=0;
			if(module ==-1) {

			}else {
				switch(activityName) {
				case "WorryHeads":
					resetDate = patientScheduleJSON.getWorryHeadsResetDate();
					resetCount = Integer.parseInt(_properties.getProperty(WH_RESET_COUNT));
					break;
				case "MakeBelieve":
					resetDate = patientScheduleJSON.getMakeBelieveResetDate();
					resetCount = Integer.parseInt(_properties.getProperty(MB_RESET_COUNT));
					break;
				case "SWAP":
					//		resetCount = Integer.parseInt(_properties.getProperty("SWAP_RESET_COUNT"));
					break;
				case "StandUp":
					resetDate = patientScheduleJSON.getStandUpResetDate();
					resetCount = Integer.parseInt(_properties.getProperty(SU_RESET_COUNT));
					break;
				}
				HashMap<String, Integer> resetDateMap = this.getModuleAndDay(patientScheduleJSON, resetDate);
				if (resetDateMap != null && resetDateMap.size() > 0) {
					if (map.containsKey(this.MODULE) && map.get(this.MODULE) != null)
						resetModule = resetDateMap.get(this.MODULE);
					if (map.containsKey(this.DAY) && map.get(this.DAY) != null)
						resetDay = resetDateMap.get(this.DAY);
				}

				if(resetCount == -1) {
					System.out.println("Reset count not set for the Activity : " + activityName);
				}else {
					ArrayList<ScheduleArrayJSON> schedule = moduleJson.get(module).getSchedule();;
					while(count < resetCount) {
						if(prevDay == -1) {
							module--;
							if (module == -1)
								break;
							schedule = moduleJson.get(module).getSchedule();
							prevDay = schedule.size() -1;
							if(resetModule > module
									|| (module == resetModule) && prevDay < resetDay) {
								break;
							}

						}
						ArrayList<ActivityScheduleJSON> activityList = schedule.get(prevDay).getActivitySchedule();

						for(ActivityScheduleJSON activity :activityList) {
							if(activity.getActivity().equals(activityName)) {
								if(activity.getLevelofSkillPersonalization() == levelOfSkill) {
									flag++;
								}
								break;
							}
						}
						prevDay--;
						count++;
					}
					if(flag == resetCount) {
						// update reset date
						if(dao.updateResetDate(patientScheduleJSON.getPin(), new Date(), activityName)) {
							System.out.println("Reset Date updated for activity : " + activityName);
						}else {
							System.out.println("Reset Date FAILED to update for activity : " + activityName);
						}
						rval = false;
					}
				}
			}
			return rval;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}


	}

}

