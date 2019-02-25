package edu.asu.heal.core.api.dao.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.models.*;
import edu.asu.heal.reachv3.api.models.BlobTricks;
import edu.asu.heal.reachv3.api.models.Emotions;
import edu.asu.heal.reachv3.api.models.MakeBelieveActivityInstance;
import edu.asu.heal.reachv3.api.models.MakeBelieveSituation;
import edu.asu.heal.reachv3.api.models.SUDSActivityInstance;
import edu.asu.heal.reachv3.api.models.SUDSQuestion;
import edu.asu.heal.reachv3.api.models.StandUpActivityInstance;
import edu.asu.heal.reachv3.api.models.StandUpSituation;
import edu.asu.heal.reachv3.api.models.FaceitActivityInstance;
import edu.asu.heal.reachv3.api.models.FaceItModel;
import edu.asu.heal.reachv3.api.models.WorryHeadsActivityInstance;
import edu.asu.heal.reachv3.api.models.WorryHeadsSituation;
import edu.asu.heal.reachv3.api.models.schedule.ActivityScheduleJSON;
import edu.asu.heal.reachv3.api.models.schedule.PatientScheduleJSON;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.*;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBDAO implements DAO {

	// collection captions
	private static final String DOMAINS_COLLECTION = "domains";
	private static final String TRIALS_COLLECTION = "trials";
	private static final String ACTIVITIES_COLLECTION = "activities";
	private static final String PATIENTS_COLLECTION = "patients";
	private static final String ACTIVITYINSTANCES_COLLECTION = "activityInstances";
	private static final String MAKEBELIEVESITUATIONS_COLLECTION = "makeBelieveSituations";
	private static final String MAKEBELIEVESITUATIONNAMES_COLLECTION = "makeBelieveSituationNames";
	private static final String FACEITCHALLENGES_COLLECTION = "faceItChallenges";
	private static final String WORRYHEADSSITUATIONS_COLLECTION = "worryHeadsSituations";
	private static final String STANDUPSITUATIONS_COLLECTION = "standUpSituations";
	private static final String SCHEDULE_COLLECTION = "schedule";
	private static final String LOGGER_COLLECTION = "logger";
	private static final String PERSONALIZE_LOGGER_COLLECTION = "personalizelogger";
	private static final String EMOTIONS_COLLECTION = "emotion";
	private static final String BLOB_COLLECTION = "blobtricks";
	private static final String SUDS_COLLECTION = "sudsquestions";


	private static String __mongoDBName;
	private static String __mongoURI;
	private Properties properties1 = new Properties();
	private Map<String, List<String>> emotionsMap = new HashMap<>();


	public MongoDBDAO(Properties properties) {
		__mongoURI = properties.getProperty("mongo.uri");
		__mongoDBName = properties.getProperty("mongo.dbname");

	}

	private static MongoClient mongoClient  = null;

	private static MongoDatabase getConnectedDatabase() {
		try {

			if (mongoClient == null) {
				mongoClient = new MongoClient(new MongoClientURI(__mongoURI));
			}
			// create codec registry for POJOs
			CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
					fromProviders(PojoCodecProvider.builder().automatic(true).build()));

			//MongoClient mongoClient = new MongoClient(new MongoClientURI(__mongoURI));

			// get handle to "_mongoDBName" database
			return mongoClient.getDatabase(__mongoDBName).withCodecRegistry(pojoCodecRegistry);
		} catch (Exception e) {
			System.out.println("SOME ERROR GETTING MONGO CONNECTION");
			e.printStackTrace();
			return null;
		}

	}

	/****************************************  Domain DAO methods *****************************************************/
	@Override
	public List<Domain> getDomains() {
		MongoDatabase database = MongoDBDAO.getConnectedDatabase();
		MongoCollection<Domain> domainCollection = database.getCollection(MongoDBDAO.DOMAINS_COLLECTION, Domain.class);

		return domainCollection.find().projection(Projections.excludeId()).into(new ArrayList<>());
	}

	@Override
	public Domain getDomain(String id) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Domain> domainCollection = database.getCollection(MongoDBDAO.DOMAINS_COLLECTION, Domain.class);

			Domain domain = domainCollection
					.find(Filters.eq(Domain.DOMAINID_ATTRIBUTE, id))
					.projection(Projections.excludeId())
					.first();
			if (domain == null)
				return NullObjects.getNullDomain();

			return domain;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Domain createDomain(Domain instance) {
		try {

			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Domain> domainCollection = database.getCollection(MongoDBDAO.DOMAINS_COLLECTION, Domain.class);

			ObjectId newId = ObjectId.get();
			instance.setDomainId(newId.toHexString());

			domainCollection.insertOne(instance);

			return instance;
		} catch (Exception e) {
			System.out.println("SOME ERROR CREATING DOMAIN");
			e.printStackTrace();
			return null;
		}

	}

	/****************************************  Activity DAO methods ***************************************************/
	@Override
	public List<Activity> getActivities(String domain)  {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Domain> domainCollection = database.getCollection(DOMAINS_COLLECTION, Domain.class);

			Domain domain1 = domainCollection.find(Filters.eq(Domain.TITLE_ATTRIBUTE, domain)).first();
			if (domain1 == null) {
				List<Activity> result = new ArrayList<>();
				result.add(NullObjects.getNullActivity());
				return result;
			}

			MongoCollection<Activity> activityCollection = database.getCollection(ACTIVITIES_COLLECTION, Activity.class);
			return activityCollection
					.find(Filters.in(Activity.ACTIVITYID_ATTRIBUTE, domain1.getActivities().toArray(new String[]{})))
					.projection(Projections.excludeId())
					.into(new ArrayList<>());

		} catch (NullPointerException ne) {
			ne.printStackTrace();
			return new ArrayList<>();
		} catch (Exception e) {
			System.out.println("SOME ERROR HERE **");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity createActivity(Activity activity)  {
		try {
			ObjectId newId = ObjectId.get();
			activity.setActivityId(newId.toHexString());

			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Activity> activitiesCollection = database.getCollection(MongoDBDAO.ACTIVITIES_COLLECTION,
					Activity.class);

			activitiesCollection.insertOne(activity);

			return activity;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity getActivity(String activityId) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Activity> activityMongoCollection =
					database.getCollection(ACTIVITIES_COLLECTION, Activity.class);

			return activityMongoCollection
					.find(Filters.eq(Activity.ACTIVITYID_ATTRIBUTE, activityId))
					.projection(Projections.excludeId())
					.first();
		} catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN GETTING ACTIVITY WITH ID " + activityId);
			ne.printStackTrace();
			return NullObjects.getNullActivity();
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN GETACTIVITY");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity updateActivity(Activity activity) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Activity> activityMongoCollection =
					database.getCollection(ACTIVITIES_COLLECTION, Activity.class);

			return activityMongoCollection
					.findOneAndReplace(Filters.eq(Activity.ACTIVITYID_ATTRIBUTE, activity.getActivityId()), activity);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity deleteActivity(String activityId) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Activity> activityMongoCollection =
					database.getCollection(ACTIVITIES_COLLECTION, Activity.class);

			Activity deletedActivity = activityMongoCollection
					.findOneAndDelete(Filters.eq(Activity.ACTIVITYID_ATTRIBUTE, activityId));

			if (deletedActivity == null)
				return NullObjects.getNullActivity();

			// What are the possible ramification of deleting an Activity?
			// Delete all activity instances related to it
			// Delete the activity from the domain
			// Need to consider this before deleting

			return deletedActivity;
		} catch (Exception e) {
			System.out.println("SOME PROBLEM DELETING ACTIVITY " + activityId);
			e.printStackTrace();
			return null;
		}
	}


	/****************************************  ActivityInstance DAO methods *******************************************/
	@Override
	public List<ActivityInstance> getScheduledActivities(int patientPin)  {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Patient> patientCollection = database.getCollection(MongoDBDAO.PATIENTS_COLLECTION,
					Patient.class);

			Patient patient = patientCollection.find(Filters.eq(Patient.PIN_ATTRIBUTE, patientPin)).first();
			if (patient == null) {
				List<ActivityInstance> result = new ArrayList<>();
				result.add(NullObjects.getNullActivityInstance());
				return result;
			}

			MongoCollection<ActivityInstance> activityInstanceCollection =
					database.getCollection(MongoDBDAO.ACTIVITYINSTANCES_COLLECTION, ActivityInstance.class);

			List<String> states = new ArrayList<String>();
			states.add(ActivityInstanceStatus.CREATED.status());
			states.add(ActivityInstanceStatus.SUSPENDED.status());

			return activityInstanceCollection
					.find(Filters.and(Filters.in(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE,
							patient.getActivityInstances().toArray(new String[]{})),
							Filters.in(ActivityInstance.STATE_ATTRIBUTE, states.toArray(new String[] {})))) // todo Need to confirm this. It could be other states from the enum as well
					.projection(Projections.excludeId())
					.into(new ArrayList<>());
		} catch (Exception e) {
			System.out.println("SOME ERROR IN GETSCHEDULEDACTIVITIES IN MONGODBDAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ActivityInstance deleteActivityInstance(String activityInstanceId) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<ActivityInstance> activityInstanceMongoCollection =
					database.getCollection(ACTIVITYINSTANCES_COLLECTION, ActivityInstance.class);

			ActivityInstance deletedInstance = activityInstanceMongoCollection
					.findOneAndDelete(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, activityInstanceId));

			if (deletedInstance == null)
				return NullObjects.getNullActivityInstance();

			Patient patient = getPatient(deletedInstance.getPatientPin());
			List<String> patientActivityInstances = patient.getActivityInstances();
			patientActivityInstances.remove(patientActivityInstances.indexOf(deletedInstance.getActivityInstanceId()));
			updatePatient(patient);

			return deletedInstance;
		} catch (Exception e) {
			System.out.println("SOME PROBLEM DELETING ACTIVITY INSTANCE " + activityInstanceId);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ActivityInstance createActivityInstance(ActivityInstance instance) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<ActivityInstance> activityInstanceMongoCollection =
					database.getCollection(ACTIVITYINSTANCES_COLLECTION, ActivityInstance.class);

			Patient patient = getPatient(instance.getPatientPin());
			if (patient == null || patient.equals(NullObjects.getNullPatient()))
				return NullObjects.getNullActivityInstance();

			ObjectId newId = ObjectId.get();
			instance.setActivityInstanceId(newId.toHexString());
			activityInstanceMongoCollection.insertOne(instance);
			patient.getActivityInstances().add(instance.getActivityInstanceId());
			patient.setUpdatedAt(new Date());

			updatePatient(patient);
			return instance;
		} catch (Exception e) {
			System.out.println("SOME ERROR INSERTING NEW ACTIVITY INSTANCE INTO DATABASE");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ActivityInstance getActivityInstance(String activityInstanceId) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<ActivityInstance> activityInstanceMongoCollection =
					database.getCollection(ACTIVITYINSTANCES_COLLECTION, ActivityInstance.class);


			ActivityInstance instance = activityInstanceMongoCollection
					.find(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, activityInstanceId))
					.projection(Projections.excludeId())
					.first();

			if(instance.getInstanceOf().getName().equals("MakeBelieve")) //todo need to do this more elegantly
				instance = getActivityMakeBelieveInstanceDAO(activityInstanceId);
			else if(instance.getInstanceOf().getName().equals("FaceIt"))
				instance = getActivityFaceItInstanceDAO(activityInstanceId);
			else if(instance.getInstanceOf().getName().equals("WorryHeads"))
				instance = getActivityWorryHeadsInstanceDAO(activityInstanceId);
			else if(instance.getInstanceOf().getName().equals("StandUp"))
				instance = getActivityStandUpInstanceDAO(activityInstanceId);

			System.out.println("ACTIVITY INSTANCE GOT FROM DB");
			System.out.println(instance);
			return instance ;
		} catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN GETTING ACTIVITY INSTANCE WITH ID " + activityInstanceId);
			ne.printStackTrace();
			return NullObjects.getNullActivityInstance();
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN GETACTIVITYINSTANCEID");
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public FaceitActivityInstance getActivityFaceItInstanceDAO (String activityInstanceId) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<FaceitActivityInstance> activityInstanceMongoCollection =
					database.getCollection(ACTIVITYINSTANCES_COLLECTION, FaceitActivityInstance.class);

			FaceitActivityInstance faceitActivityInstance =  new FaceitActivityInstance();
			FaceitActivityInstance instance = activityInstanceMongoCollection
					.find(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, activityInstanceId))
					.projection(Projections.excludeId())
					.first();

			//Call the method here
			instance.setFaceItChallenge();


			System.out.println("ACTIVITY INSTANCE GOT FROM DB");
			return instance ;
		} catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN GETTING ACTIVITY INSTANCE WITH ID " + activityInstanceId);
			ne.printStackTrace();
			return (FaceitActivityInstance) NullObjects.getNullActivityInstance();
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN GETACTIVITYINSTANCEID");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateActivityInstance(ActivityInstance instance) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<ActivityInstance> activityInstanceMongoCollection =
					database.getCollection(ACTIVITYINSTANCES_COLLECTION, ActivityInstance.class);

			ActivityInstance updatedInstance = activityInstanceMongoCollection
					.findOneAndReplace(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, instance.getActivityInstanceId()), instance);

			if(updatedInstance != null){
				return true;
			}
			return false;
		}catch (Exception e){
			System.out.println("Some problem in updateActivityInstance() in MongoDBDao");
			return false;
		}
	}

	@Override
	public boolean updateFaceitActivityInstance(ActivityInstance instance) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<FaceitActivityInstance> activityInstanceMongoCollection =
					database.getCollection(ACTIVITYINSTANCES_COLLECTION, FaceitActivityInstance.class);

			//code to update the answerId and status based on the questionId passed
			FaceitActivityInstance faceItActivityInstance = (FaceitActivityInstance) instance;
			int questionId = faceItActivityInstance.getFaceItChallenges().get(0).getQuestionId();
			String status= faceItActivityInstance.getFaceItChallenges().get(0).getStatus();
			int answerId= faceItActivityInstance.getFaceItChallenges().get(0).getAnswerId();
			BasicDBObject query = new BasicDBObject();
			query.put("activityInstanceId", faceItActivityInstance.getActivityInstanceId());
			query.put("faceItChallenges.questionId", questionId);

			BasicDBObject data = new BasicDBObject();
			data.put("faceItChallenges.$.status", status);
			data.put("faceItChallenges.$.answerId", answerId);

			BasicDBObject command = new BasicDBObject();
			command.put("$set", data);

			FaceitActivityInstance myUpdatedInstance=activityInstanceMongoCollection.findOneAndUpdate(query, command);

			if(myUpdatedInstance != null){
				return true;
			}
			return false;
		}catch (Exception e){
			System.out.println("Some problem in updateFaceitActivityInstance() in MongoDBDao");
			return false;
		}
	}

	@Override
	public WorryHeadsActivityInstance getActivityWorryHeadsInstanceDAO(String activityInstanceId) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<WorryHeadsActivityInstance> activityInstanceMongoCollection =
					database.getCollection(ACTIVITYINSTANCES_COLLECTION, WorryHeadsActivityInstance.class);

			WorryHeadsActivityInstance instance = activityInstanceMongoCollection
					.find(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, activityInstanceId))
					.projection(Projections.excludeId())
					.first();

			//instance.setWorryHeadsSituation();
			List<WorryHeadsSituation> situations = getAllWorryHeadsSituations();
			instance.setSituation(situations);

			System.out.println("ACTIVITY INSTANCE GOT FROM DB");
			return instance ;
		} catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN GETTING ACTIVITY INSTANCE WITH ID " + activityInstanceId);
			ne.printStackTrace();
			return (WorryHeadsActivityInstance) NullObjects.getNullActivityInstance();
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN GETACTIVITYINSTANCEID");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<WorryHeadsSituation> getAllWorryHeadsSituations() {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<WorryHeadsSituation> situationMongoCollection =
					database.getCollection(MongoDBDAO.WORRYHEADSSITUATIONS_COLLECTION, WorryHeadsSituation.class);

			//Code to randomly get a situation from the database
			AggregateIterable<WorryHeadsSituation> situations = situationMongoCollection
					.aggregate(Arrays.asList(Aggregates.sample(1)));

			WorryHeadsSituation situation = null;
			for(WorryHeadsSituation temp : situations){
				situation = temp;
			}

			List<WorryHeadsSituation> worryHeadsSituations = new ArrayList();
			worryHeadsSituations.add(situation);

			return worryHeadsSituations;
		}catch (NullPointerException ne){
			System.out.println("Could not get random make believe situation");
			ne.printStackTrace();
			return null;
		}catch (Exception e){
			System.out.println("Some problem in getting Make believe situation");
			e.printStackTrace();
			return null;
		}
	}

	/****************************************  Patient DAO methods ****************************************************/
	@Override
	public List<Patient> getPatients()  {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Patient> patientCollection = database.getCollection(PATIENTS_COLLECTION, Patient.class);

			return patientCollection
					.find()
					.projection(Projections.excludeId())
					.into(new ArrayList<>());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Patient> getPatients(String trialId)  {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Trial> trialCollection = database.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

			Trial trial = trialCollection.find(Filters.eq(Trial.TRIALID_ATTRIBUTE, trialId)).first();
			if (trial == null) {
				ArrayList<Patient> result = new ArrayList<>();
				result.add(NullObjects.getNullPatient());
				return result;
			}

			MongoCollection<Patient> patientsCollection = database
					.getCollection(MongoDBDAO.PATIENTS_COLLECTION, Patient.class);

			return patientsCollection
					.find(Filters.in(Patient.PATIENTID_ATTRIBUTE, trial.getPatients().toArray(new String[]{})))
					.projection(Projections.excludeId())
					.into(new ArrayList<>());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Patient createPatient(String trialId) {
		try {

			MongoCollection<Trial> trialsCollection = MongoDBDAO.getConnectedDatabase()
					.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

			Trial trial = trialsCollection.find(Filters.eq(Trial.TRIALID_ATTRIBUTE, trialId)).first();

			if(trial == null)
				return NullObjects.getNullPatient();

			//Temporary code to generate new pin. It just increments the largest pin number in the database by 1
			int newPin = MongoDBDAO.getConnectedDatabase()
					.getCollection(MongoDBDAO.PATIENTS_COLLECTION)
					.aggregate(Arrays.asList(Aggregates.group(null,
							Accumulators.max(Patient.PIN_ATTRIBUTE, "$" + Patient.PIN_ATTRIBUTE))))
					.first()
					.getInteger(Patient.PIN_ATTRIBUTE);
			++newPin;

			Patient newPatient = new Patient();
			ObjectId newId = ObjectId.get();
			newPatient.setPatientId(newId.toHexString());
			newPatient.setPin(newPin);
			newPatient.setCreatedAt(new Date());
			newPatient.setEndDate(new Date());
			newPatient.setStartDate(new Date());
			newPatient.setUpdatedAt(new Date());
			newPatient.setState(PatientState.CREATED.state());

			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Patient> patientCollection = database.getCollection(MongoDBDAO.PATIENTS_COLLECTION, Patient.class);

			patientCollection.insertOne(newPatient);

			trial.getPatients().add(newPatient.getPatientId());

			trialsCollection.replaceOne(Filters.eq(Trial.TRIALID_ATTRIBUTE, trialId), trial);

			return newPatient;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Patient getPatient(int patientPin) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			Patient patient = database.getCollection(MongoDBDAO.PATIENTS_COLLECTION, Patient.class)
					.find(Filters.eq(Patient.PIN_ATTRIBUTE, patientPin))
					.first();

			if (patient == null)
				return NullObjects.getNullPatient();
			return patient;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Patient updatePatient(Patient patient) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Patient> patientMongoCollection =
					database.getCollection(PATIENTS_COLLECTION, Patient.class);

			return patientMongoCollection
					.findOneAndReplace(Filters.eq(Patient.PIN_ATTRIBUTE, patient.getPin()), patient);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/****************************************  Trial DAO methods ******************************************************/

	@Override
	public List<Trial> getTrials()  {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Trial> trialCollection = database.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

			return trialCollection
					.find()
					.projection(Projections.excludeId())
					.into(new ArrayList<>());
		} catch (Exception e) {
			System.out.println("SOME PROBLEM IN GETTRIALS() METHOD OF MONGODBDAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Trial> getTrials(String domain)  {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Domain> domainCollection = database.getCollection(MongoDBDAO.DOMAINS_COLLECTION, Domain.class);

			Domain domain1 = domainCollection.find(Filters.eq(Domain.TITLE_ATTRIBUTE, domain)).first();
			if (domain1 == null) {
				List<Trial> result = new ArrayList<>();
				result.add(NullObjects.getNullTrial());
				return result;
			}

			MongoCollection<Trial> trialCollection = database.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

			return trialCollection
					.find(Filters.in(Trial.TRIALID_ATTRIBUTE, domain1.getTrials().toArray(new String[]{})))
					.projection(Projections.excludeId())
					.into(new ArrayList<>());
		} catch (Exception e) {
			System.out.println("SOME PROBLEM IN GETTRIALS() METHOD OF MONGODBDAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Trial createTrial(Trial trialInstance)  {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Trial> trialCollection = database.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

			ObjectId newId = ObjectId.get();
			trialInstance.setTrialId(newId.toHexString());

			trialCollection.insertOne(trialInstance);

			return trialInstance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getTrialIdByTitle(String title) {

		MongoDatabase database = MongoDBDAO.getConnectedDatabase();
		MongoCollection<Trial> trialCollection = database.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

		Trial rval= trialCollection
				.find(Filters.eq(Trial.TITLE_ATTRIBUTE, title))
				.projection(Projections.excludeId()).first();

		return rval.getTrialId();
	}

	/****************************************  Logger DAO methods *****************************************************/

	@Override
	public Logger[] logMessage (Logger[] loggerInstance) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Trial> trialsCollection = MongoDBDAO.getConnectedDatabase()
					.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

			MongoCollection<Logger> loggerCollection = database.getCollection(MongoDBDAO.LOGGER_COLLECTION,
					Logger.class);
			for (Logger log: loggerInstance) {
				String trialId = log.getTrialId();

				Trial trial = trialsCollection.find(Filters.eq(Trial.TRIALID_ATTRIBUTE, trialId)).first();

				if(trial != null) {
					loggerCollection.insertOne(log);
				}
			}



			return loggerInstance;
		} catch (Exception e){
			System.out.println("Some problem in storing logs");
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Logger[] logPersonalizationMessage (Logger[] loggerInstance) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Trial> trialsCollection = MongoDBDAO.getConnectedDatabase()
					.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

			MongoCollection<Logger> loggerCollection = database.getCollection(MongoDBDAO.PERSONALIZE_LOGGER_COLLECTION,
					Logger.class);
			for (Logger log: loggerInstance) {
				String trialId = log.getTrialId();

				Trial trial = trialsCollection.find(Filters.eq(Trial.TRIALID_ATTRIBUTE, trialId)).first();

				if(trial != null) {
					loggerCollection.insertOne(log);
				}
			}
			return loggerInstance;
		} catch (Exception e){
			System.out.println("Some problem in storing personlized logs");
			e.printStackTrace();
			return null;
		}
	}

	/****************************************  Other DAO methods ******************************************************/

	@Override
	public MakeBelieveActivityInstance getActivityMakeBelieveInstanceDAO(String activityInstanceId) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<MakeBelieveActivityInstance> activityInstanceMongoCollection =
					database.getCollection(ACTIVITYINSTANCES_COLLECTION, MakeBelieveActivityInstance.class);

			MakeBelieveActivityInstance instance = activityInstanceMongoCollection
					.find(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, activityInstanceId))
					.projection(Projections.excludeId())
					.first();

			MakeBelieveSituation situation = getMakeBelieveSituation();

			instance.setSituation(situation);

			System.out.println("ACTIVITY INSTANCE GOT FROM DB");
			System.out.println(instance);
			return instance ;
		} catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN GETTING ACTIVITY INSTANCE WITH ID " + activityInstanceId);
			ne.printStackTrace();
			return (MakeBelieveActivityInstance) NullObjects.getNullActivityInstance();
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN GETACTIVITYINSTANCEID");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public MakeBelieveSituation getMakeBelieveSituation() {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<MakeBelieveSituation> situationMongoCollection =
					database.getCollection(MongoDBDAO.MAKEBELIEVESITUATIONS_COLLECTION, MakeBelieveSituation.class);

			//Code to randomly get a situation from the database
			AggregateIterable<MakeBelieveSituation> situations = situationMongoCollection
					.aggregate(Arrays.asList(Aggregates.sample(1)));

			MakeBelieveSituation situation = null;
			for(MakeBelieveSituation temp : situations){
				situation = temp;
			}

			// Code to randomly get a name from the database
			MongoCollection<Document> namesCollection =
					database.getCollection(MongoDBDAO.MAKEBELIEVESITUATIONNAMES_COLLECTION);
			AggregateIterable<Document> names = namesCollection.aggregate(Arrays.asList(Aggregates.sample(1)));

			String name = null;
			for(Document temp : names){
				name = temp.getString("name");
			}

			situation.setName(name);
			return situation;
		}catch (NullPointerException ne){
			System.out.println("Could not get random make believe situation");
			ne.printStackTrace();
			return null;
		}catch (Exception e){
			System.out.println("Some problem in getting Make believe situation");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<String> getEmotionsActivityInstance(String emotion, Object intensity, String sessionId) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			// needs to incorporate Emotions model. - Task #386
			MongoCollection<Document> emotionMongoCollection =
					database.getCollection(MongoDBDAO.EMOTIONS_COLLECTION);//, Emotions.class);

			FindIterable<Document> result =	emotionMongoCollection.find(Filters.eq(Emotions.EMOTION_NAME,emotion));

			MongoCursor<Document> cursor = result.iterator();
			List<String> rval = new ArrayList<String>();
			while(cursor.hasNext()) {
				Document doc = cursor.next();
				String tempIntensity = doc.getString(Emotions.INTENSITY);
				String tempSession = doc.getString(Emotions.SESSION);
				if(((sessionId == null && sessionId.length() == 0) && tempIntensity.contains((String)intensity))
						|| tempIntensity.contains((String)intensity) && tempSession.contains(sessionId)) {
					if(doc.getString(Emotions.SUGGESTED_ACTIVITIES).length() ==1) {
						rval.add(doc.getString(Emotions.SUGGESTED_ACTIVITIES));
					}
					else {
						String x[] = doc.getString(Emotions.SUGGESTED_ACTIVITIES).split(",");
						Collections.addAll(rval, x);
					}
				}
			}

			return rval;
		}catch (NullPointerException npe){
			npe.printStackTrace();
			return null;
		}

	}

	@Override
	public List<FaceItModel> getFaceItChallenges() {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<FaceItModel> faceItMongoCollection =
					database.getCollection(MongoDBDAO.FACEITCHALLENGES_COLLECTION, FaceItModel.class);

			FindIterable<FaceItModel> challenges = faceItMongoCollection
					.find();

			List<FaceItModel> faceItchallenges = new ArrayList<>();
			for (FaceItModel temp : challenges) {
				System.out.println(temp.getQuestionText());
				faceItchallenges.add(temp);
			}

			Collections.shuffle(faceItchallenges);
			return faceItchallenges;
		} catch (java.lang.Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	@Override
	public StandUpActivityInstance getActivityStandUpInstanceDAO(String activityInstanceId) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<StandUpActivityInstance> activityInstanceMongoCollection =
					database.getCollection(ACTIVITYINSTANCES_COLLECTION, StandUpActivityInstance.class);

			StandUpActivityInstance instance = activityInstanceMongoCollection
					.find(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, activityInstanceId))
					.projection(Projections.excludeId())
					.first();

			List<StandUpSituation> situations = getStandUpSituations();
			instance.setSituations(situations);

			System.out.println("ACTIVITY INSTANCE GOT FROM DB");
			System.out.println(instance);
			return instance ;
		} catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN GETTING ACTIVITY INSTANCE WITH ID " + activityInstanceId);
			ne.printStackTrace();
			return (StandUpActivityInstance) NullObjects.getNullActivityInstance();
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN GETACTIVITYINSTANCEID");
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public List<StandUpSituation> getStandUpSituations() {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<StandUpSituation> situationMongoCollection =
					database.getCollection(MongoDBDAO.STANDUPSITUATIONS_COLLECTION, StandUpSituation.class);

			//Code to randomly get a situation from the database
			AggregateIterable<StandUpSituation> situations = situationMongoCollection
					.aggregate(Arrays.asList(Aggregates.sample(1)));

			StandUpSituation situation = null;
			for(StandUpSituation temp : situations){
				situation = temp;
			}

			List<StandUpSituation> standUpSituations = new ArrayList<>();
			standUpSituations.add(situation);

			return standUpSituations;
		}catch (NullPointerException ne){
			System.out.println("Could not get random make believe situation");
			ne.printStackTrace();
			return null;
		}catch (Exception e){
			System.out.println("Some problem in getting Make believe situation");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BlobTricks getReleasedBlobTricksDAO(int patientPin) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();

			MongoCollection<BlobTricks> blobTricksCollection =
					database.getCollection(BLOB_COLLECTION,BlobTricks.class);

			return blobTricksCollection
					.find(Filters.eq("patientPin",patientPin))
					.projection(Projections.excludeId())
					.first();

		}catch (NullPointerException ne){
			System.out.println("Could not get random make believe situation");
			ne.printStackTrace();
			return null;
		}catch (Exception e){
			System.out.println("Some problem in getting Make believe situation");
			e.printStackTrace();
			return null;
		}	
	}

	@Override
	public void updateBlobTrickCountDAO(BlobTricks blobTricks) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();

			MongoCollection<BlobTricks> blobTricksCollection =
					database.getCollection(MongoDBDAO.BLOB_COLLECTION,BlobTricks.class);

			blobTricksCollection.findOneAndReplace(Filters.eq("patientPin",blobTricks.getPatientPin()),blobTricks);

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public PatientScheduleJSON getSchedule(int patientPin) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();

			MongoCollection<PatientScheduleJSON> scheduleMongoCollection =
					database.getCollection(SCHEDULE_COLLECTION, PatientScheduleJSON.class);

			PatientScheduleJSON patientScheduleJSON = scheduleMongoCollection
					.find(Filters.eq(PatientScheduleJSON.PATIENTPIN_ATTRIBUTE, patientPin))
					.projection(Projections.excludeId())
					.first();


			return patientScheduleJSON;
		}catch (NullPointerException ne){
			System.out.println("Error in getting schedule.");
			ne.printStackTrace();
			return null;
		}catch (Exception e){
			System.out.println("Some problem in getting schedule.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateUIPersonalization(int patientPin, int module, 
			int dayOfModule, int indexOfActivity, int levelOfUIPersonalization) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();

			MongoCollection<PatientScheduleJSON> scheduleMongoCollection =
					database.getCollection(SCHEDULE_COLLECTION, PatientScheduleJSON.class);

						PatientScheduleJSON patientScheduleJSON = scheduleMongoCollection
								.find(Filters.eq(PatientScheduleJSON.PATIENTPIN_ATTRIBUTE, patientPin))
								.projection(Projections.excludeId())
								.first();
						patientScheduleJSON.getSchedule().get(module).getSchedule().get(dayOfModule).getActivitySchedule()
								.get(indexOfActivity).setLevelOfUIPersonalization(levelOfUIPersonalization);

			scheduleMongoCollection.findOneAndReplace(Filters.eq(PatientScheduleJSON.PATIENTPIN_ATTRIBUTE, patientPin),
					patientScheduleJSON);

			return true;
		}catch (NullPointerException ne){
			System.out.println("Error in getting schedule.");
			ne.printStackTrace();
			return false;
		}catch (Exception e){
			System.out.println("Some problem in getting schedule.");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateLvlTwoUIPersonalization(int patientPin, int module, 
			int dayOfModule, List<String> activityNames, int levelOfUIPersonalization) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();

			MongoCollection<PatientScheduleJSON> scheduleMongoCollection =
					database.getCollection(SCHEDULE_COLLECTION, PatientScheduleJSON.class);

						PatientScheduleJSON patientScheduleJSON = scheduleMongoCollection
								.find(Filters.eq(PatientScheduleJSON.PATIENTPIN_ATTRIBUTE, patientPin))
								.projection(Projections.excludeId())
								.first();
						ArrayList<ActivityScheduleJSON> activities = patientScheduleJSON.getSchedule().get(module)
								.getSchedule().get(dayOfModule-1).getActivitySchedule();
						
						for(ActivityScheduleJSON activity : activities) {
							if(activityNames.contains(activity.getActivity())) {
								activity.setLevelOfUIPersonalization(levelOfUIPersonalization);
							}
						}
//						patientScheduleJSON.getSchedule().get(module).getSchedule().get(dayOfModule).getActivitySchedule()
//								.get(indexOfActivity).setLevelOfUIPersonalization(levelOfUIPersonalization);

			scheduleMongoCollection.findOneAndReplace(Filters.eq(PatientScheduleJSON.PATIENTPIN_ATTRIBUTE, patientPin),
					patientScheduleJSON);

			return true;
		}catch (NullPointerException ne){
			System.out.println("Error in getting schedule.");
			ne.printStackTrace();
			return false;
		}catch (Exception e){
			System.out.println("Some problem in getting schedule.");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updatePatientScoreActualCount(int patientPin, int module, int day, 
			int indexOfActivity,int score, int actualCount) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();

			MongoCollection<PatientScheduleJSON> scheduleMongoCollection =
					database.getCollection(SCHEDULE_COLLECTION, PatientScheduleJSON.class);

			PatientScheduleJSON patientScheduleJSON = scheduleMongoCollection
					.find(Filters.eq(PatientScheduleJSON.PATIENTPIN_ATTRIBUTE, patientPin))
					.projection(Projections.excludeId())
					.first();
			patientScheduleJSON.getSchedule().get(module).getSchedule().get(day).getActivitySchedule()
			.get(indexOfActivity).setScore(score);
			patientScheduleJSON.getSchedule().get(module).getSchedule().get(day).getActivitySchedule()
			.get(indexOfActivity).setActualCount(actualCount);

			scheduleMongoCollection.findOneAndReplace(Filters.eq(PatientScheduleJSON.PATIENTPIN_ATTRIBUTE, patientPin),
					patientScheduleJSON);

			return true;
		}catch (NullPointerException ne){
			System.out.println("Error in getting schedule.");
			ne.printStackTrace();
			return false;
		}catch (Exception e){
			System.out.println("Some problem in getting schedule.");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateLevelOfSkillPersonalization(int patientPin, int module, int day,
												 int indexOfActivity,Integer levelOfSkillPersonalization) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();

			MongoCollection<PatientScheduleJSON> scheduleMongoCollection =
					database.getCollection(SCHEDULE_COLLECTION, PatientScheduleJSON.class);

			PatientScheduleJSON patientScheduleJSON = scheduleMongoCollection
					.find(Filters.eq(PatientScheduleJSON.PATIENTPIN_ATTRIBUTE, patientPin))
					.projection(Projections.excludeId())
					.first();
			patientScheduleJSON.getSchedule().get(module).getSchedule().get(day).getActivitySchedule()
					.get(indexOfActivity).setLevelofSkillPersonalization(levelOfSkillPersonalization);

			scheduleMongoCollection.findOneAndReplace(Filters.eq(PatientScheduleJSON.PATIENTPIN_ATTRIBUTE, patientPin),
					patientScheduleJSON);

			return true;
		}catch (NullPointerException ne){
			System.out.println("Error in getting schedule.");
			ne.printStackTrace();
			return false;
		}catch (Exception e){
			System.out.println("Some problem in getting schedule.");
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean updateActivityInstanceInPatientSchedule(int patientPin, int module, int day,
    		int indexOfActivity,String activityInstanceId) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();

			MongoCollection<PatientScheduleJSON> scheduleMongoCollection =
					database.getCollection(SCHEDULE_COLLECTION, PatientScheduleJSON.class);

			PatientScheduleJSON patientScheduleJSON = scheduleMongoCollection
					.find(Filters.eq(PatientScheduleJSON.PATIENTPIN_ATTRIBUTE, patientPin))
					.projection(Projections.excludeId())
					.first();
			patientScheduleJSON.getSchedule().get(module).getSchedule().get(day).getActivitySchedule()
					.get(indexOfActivity).getActivityInstancesIds().add(activityInstanceId);

			scheduleMongoCollection.findOneAndReplace(Filters.eq(PatientScheduleJSON.PATIENTPIN_ATTRIBUTE, patientPin),
					patientScheduleJSON);

			return true;
		}catch (NullPointerException ne){
			System.out.println("Error in getting schedule.");
			ne.printStackTrace();
			return false;
		}catch (Exception e){
			System.out.println("Some problem in getting schedule.");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateResetDate(int patientPin, Date newResetDate, String activityName) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();

			MongoCollection<PatientScheduleJSON> scheduleMongoCollection =
					database.getCollection(SCHEDULE_COLLECTION, PatientScheduleJSON.class);

			PatientScheduleJSON patientScheduleJSON = scheduleMongoCollection
					.find(Filters.eq(PatientScheduleJSON.PATIENTPIN_ATTRIBUTE, patientPin))
					.projection(Projections.excludeId())
					.first();
			switch(activityName) {
			case "WorryHeads":
				patientScheduleJSON.setWorryHeadsResetDate(newResetDate);
				break;
			case "MakeBelieve":
				patientScheduleJSON.setMakeBelieveResetDate(newResetDate);
				break;
			case "StandUp":
				patientScheduleJSON.setStandUpResetDate(newResetDate);
				break;
			}
			
			scheduleMongoCollection.findOneAndReplace(Filters.eq(PatientScheduleJSON.PATIENTPIN_ATTRIBUTE, patientPin),
					patientScheduleJSON);

			return true;
		}catch (NullPointerException ne){
			System.out.println("Error in getting schedule.");
			ne.printStackTrace();
			return false;
		}catch (Exception e){
			System.out.println("Some problem in getting schedule.");
			e.printStackTrace();
			return false;
		}
  }
  
	@Override
	public SUDSQuestion getSUDSQuestion() {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();

			MongoCollection<SUDSQuestion> sudsMongoCollection =
					database.getCollection(SUDS_COLLECTION, SUDSQuestion.class);

			AggregateIterable<SUDSQuestion> questions = sudsMongoCollection
					.aggregate(Arrays.asList(Aggregates.sample(1)));

			SUDSQuestion result=null;
			for(SUDSQuestion temp : questions){
				result = temp;
			}
			return result;
		}catch (NullPointerException ne){
			System.out.println("Error in getting schedule.");
			ne.printStackTrace();
			return null;
		}catch (Exception e){
			System.out.println("Some problem in getting schedule.");
			e.printStackTrace();
			return null;
		}

	}
}

//enum Emotions{
//	happy, sad, sick, scared, worried, angry
//}
