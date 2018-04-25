package edu.asu.heal.core.api.dao.impl;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOException;
import edu.asu.heal.core.api.models.*;
import edu.asu.heal.reachv3.api.models.MakeBelieveActivityInstance;
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

    private String __mongoDBName;
    private String __mongoURI;

    public MongoDBDAO(Properties properties) {
        __mongoURI = properties.getProperty("mongo.uri");
        __mongoDBName = properties.getProperty("mongo.dbname");
    }

    private MongoDatabase getConnectedDatabase() {
        try {

            // create codec registry for POJOs
            CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                    fromProviders(PojoCodecProvider.builder().automatic(true).build()));

            MongoClient mongoClient = new MongoClient(new MongoClientURI(__mongoURI));

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
        MongoDatabase database = getConnectedDatabase();
        MongoCollection<Domain> domainCollection = database.getCollection(MongoDBDAO.DOMAINS_COLLECTION, Domain.class);

        return domainCollection.find().projection(Projections.excludeId()).into(new ArrayList<>());
    }

    @Override
    public Domain getDomain(String id) {
        try {
            MongoDatabase database = getConnectedDatabase();
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

            MongoDatabase database = getConnectedDatabase();
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
    public List<Activity> getActivities(String domain) throws DAOException {
        try {
            MongoDatabase database = getConnectedDatabase();
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
    public Activity createActivity(Activity activity) throws DAOException {
        try {
            ObjectId newId = ObjectId.get();
            activity.setActivityId(newId.toHexString());

            MongoDatabase database = getConnectedDatabase();
            MongoCollection<Activity> activitiesCollection = database.getCollection(MongoDBDAO.ACTIVITIES_COLLECTION,
                    Activity.class);

            System.out.println(activity);
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
            MongoDatabase database = getConnectedDatabase();
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
            MongoDatabase database = getConnectedDatabase();
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
            MongoDatabase database = getConnectedDatabase();
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
    public List<ActivityInstance> getScheduledActivities(int patientPin, int currentDay) throws DAOException {
        try {
            MongoDatabase database = getConnectedDatabase();
            MongoCollection<Patient> patientCollection = database.getCollection(MongoDBDAO.PATIENTS_COLLECTION, Patient.class);

            Patient patient = patientCollection.find(Filters.eq(Patient.PIN_ATTRIBUTE, patientPin)).first();
            if (patient == null) {
                List<ActivityInstance> result = new ArrayList<>();
                result.add(NullObjects.getNullActivityInstance());
                return result;
            }

            MongoCollection<ActivityInstance> activityInstanceCollection =
                    database.getCollection(MongoDBDAO.ACTIVITYINSTANCES_COLLECTION, ActivityInstance.class);

            return activityInstanceCollection
                    .find(Filters.in(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE,
                            patient.getActivityInstances().toArray(new String[]{})))
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
            MongoDatabase database = getConnectedDatabase();
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
            MongoDatabase database = getConnectedDatabase();
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
            MongoDatabase database = getConnectedDatabase();
            MongoCollection<ActivityInstance> activityInstanceMongoCollection =
                    database.getCollection(ACTIVITYINSTANCES_COLLECTION, ActivityInstance.class);

            ActivityInstance instance = activityInstanceMongoCollection
                    .find(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, activityInstanceId))
                    .projection(Projections.excludeId())
                    .first();

            if(instance.getInstanceOf().getName().equals("MakeBelieve")) //todo document this
                instance = database
                        .getCollection(ACTIVITYINSTANCES_COLLECTION, MakeBelieveActivityInstance.class)
                        .find(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, activityInstanceId))
                        .projection(Projections.excludeId())
                        .first();

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

    /****************************************  Patient DAO methods ****************************************************/
    @Override
    public List<Patient> getPatients() throws DAOException {
        try {
            MongoDatabase database = getConnectedDatabase();
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
    public List<Patient> getPatients(String trialId) throws DAOException {
        try {
            MongoDatabase database = getConnectedDatabase();
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
    public Patient createPatient() {
        try {

//TODO             MongoCollection<Trial> trialsCollection = getConnectedDatabase()
//TODO                    .getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);
//
//TODO            Trial trial = trialsCollection.find(Filters.eq(Trial.TRIALID_ATTRIBUTE, trialID)).first();
//TODO
//TODO            if(trial == null)
//TODO                return NullObjects.getNullPatient();

            //Temporary code to generate new pin. It just increments the largest pin number in the database by 1
            int newPin = getConnectedDatabase()
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

            MongoDatabase database = getConnectedDatabase();
            MongoCollection<Patient> patientCollection = database.getCollection(MongoDBDAO.PATIENTS_COLLECTION, Patient.class);

            patientCollection.insertOne(newPatient);

//TODO            trial.getPatients().add(newPatient.getPatientId());
//TODO
//TODO            trialsCollection.replaceOne(Filters.eq(Trial.TRIALID_ATTRIBUTE, trialID), trial);

            return newPatient;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Patient getPatient(int patientPin) {
        try {
            MongoDatabase database = getConnectedDatabase();
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
            MongoDatabase database = getConnectedDatabase();
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
    public List<Trial> getTrials() throws DAOException {
        try {
            MongoDatabase database = getConnectedDatabase();
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
    public List<Trial> getTrials(String domain) throws DAOException {
        try {
            MongoDatabase database = getConnectedDatabase();
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
    public Trial createTrial(Trial trialInstance) throws DAOException {
        try {
            MongoDatabase database = getConnectedDatabase();
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

    /****************************************  Other DAO methods ******************************************************/

    @Override
    public boolean scheduleSTOPActivity(int day, boolean completed) throws DAOException {
        return false;
    }

    @Override
    public boolean scheduleSTICActivity(int day, int sticVariable) throws DAOException {
        return false;
    }

    @Override
    public boolean scheduleRelaxationActivity(int day, boolean completed) throws DAOException {
        return false;
    }

    @Override
    public boolean scheduleDailyDiaryActivity(String dailyDiaryWeeklySchedule) {
        return false;
    }

    @Override
    public boolean scheduleABMTActivity(int day, boolean completed) throws DAOException {
        return false;
    }

    @Override
    public boolean scheduleWorryHeadsActivity(String worryHeadsWeeklySchedule) {
        return false;
    }

    @Override
    public boolean scheduleSAFEACtivity(int day, boolean completed) throws DAOException {
        return false;
    }

    @Override
    public Object getMakeBelieveActivityInstance() throws DAOException {
        return null;
    }

    @Override
    public boolean checkSituationExists(int situationId) throws DAOException {
        return false;
    }

    @Override
    public Object getMakeBelieveActivityAnswers(int situationId) throws DAOException {
        return null;
    }

    @Override
    public boolean updateMakeBelieveActivityInstance(Object makeBelieveResponse) throws DAOException {
        return false;
    }


}
