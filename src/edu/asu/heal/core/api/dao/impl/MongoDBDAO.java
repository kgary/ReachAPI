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
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.*;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBDAO implements DAO {

    // collection captions
    public static final String DOMAINS_COLLECTION = "domains";
    public static final String TRIALS_COLLECTION = "trials";
    public static final String ACTIVITIES_COLLECTION = "activities";
    public static final String PATIENTS_COLLECTION = "patients";
    public static final String ACTIVITYINSTANCES_COLLECTION = "activityInstances";

    private String __mongoUser;
    private String __mongoPassword;
    private String __mongoHost;
    private int __mongoPort;
    private String __mongoDBName;
    private String __mongoURI;

    public MongoDBDAO() {
    }

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

    @Override
    public List<Domain> getDomains() {
        MongoDatabase database = getConnectedDatabase();
        MongoCollection<Domain> domainCollection = database.getCollection("domains", Domain.class);

        return domainCollection.find().projection(Projections.excludeId()).into(new ArrayList<>());
    }

    @Override
    public Domain getDomain(String id) {
        MongoDatabase database = getConnectedDatabase();
        MongoCollection<Domain> domainCollection = database.getCollection(MongoDBDAO.DOMAINS_COLLECTION, Domain.class);

        return domainCollection
                .find(Filters.eq("_id", id))
                .projection(Projections.excludeId())
                .first();
    }

    @Override
    public boolean createDomain(Domain instance) {
        MongoDatabase database = getConnectedDatabase();
        MongoCollection<Domain> domainCollection = database.getCollection(MongoDBDAO.DOMAINS_COLLECTION, Domain.class);

        domainCollection.insertOne(instance);

        return true;
    }

    @Override
    public List<ActivityInstance> getScheduledActivities(int patientPin, int currentDay) throws DAOException {
        try {
            MongoDatabase database = getConnectedDatabase();
            MongoCollection<Patient> patientCollection = database.getCollection(MongoDBDAO.PATIENTS_COLLECTION, Patient.class);

            Patient patient = patientCollection.find(Filters.eq(Patient.PIN_ATTRIBUTE, patientPin)).first();
            if(patient == null){
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

    // methods pertaining to Activity (activities) collection
    @Override
    public List<Activity> getActivities(String domain) throws DAOException {
        try {
            MongoDatabase database = getConnectedDatabase();
            MongoCollection<Domain> domainCollection = database.getCollection(DOMAINS_COLLECTION, Domain.class);

            Domain domain1 = domainCollection.find(Filters.eq(Domain.TITLE_ATTRIBUTE, domain)).first();
            if(domain1 == null){
                List<Activity> result = new ArrayList<>();
                Activity a = new Activity();
                a.setId(null);
                a.setActivityId("NULL");
                a.setTitle("NULL");
                a.setDescription("NULL");
                a.setCreatedAt(new Date(0));
                a.setUpdatedAt(new Date(0));
                result.add(a);
                return result;
            }

            MongoCollection<Activity> activityCollection = database.getCollection(ACTIVITIES_COLLECTION, Activity.class);
            return activityCollection
                    .find(Filters.in(Activity.ACTIVITYID_ATTRIBUTE, domain1.getActivities().toArray(new String[]{})))
                    .projection(Projections.excludeId())
                    .into(new ArrayList<>());

        } catch(NullPointerException ne){
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
            activity.setId(newId);
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

    // methods pertaining to the Trial Model


    @Override
    public List<Trial> getTrials() throws DAOException {
        try {
            MongoDatabase database = getConnectedDatabase();
            MongoCollection<Trial> trialCollection = database.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

            return trialCollection
                    .find()
                    .projection(Projections.excludeId())
                    .into(new ArrayList<Trial>());
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
            MongoCollection<Trial> trialCollection = database.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

            return trialCollection
                    .find(Filters.in(Trial.ID_ATTRIBUTE, domain1.getTrials().toArray(new ObjectId[]{})))
                    .projection(Projections.excludeId())
                    .into(new ArrayList<Trial>());
        } catch (NullPointerException ne) {
            System.out.println("SOME ERROR IN GETTING DOMAIN DATA");
            ne.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println("SOME PROBLEM IN GETTRIALS() METHOD OF MONGODBDAO");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createTrial(Trial trialInstance) throws DAOException {
        try {
            MongoDatabase database = getConnectedDatabase();
            MongoCollection<Trial> trialCollection = database.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);
            trialCollection.insertOne(trialInstance);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

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
            if(trial == null){
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

//            MongoCollection<Trial> trialsCollection = getConnectedDatabase()
//                    .getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);
//
//            Trial trial = trialsCollection.find(Filters.eq(Trial.TRIALID_ATTRIBUTE, trialID)).first();
//
//            if(trial == null)
//                return NullObjects.getNullPatient();

            //Temporary code to generate new pin. It just increments the largest pin number in the database by 1
            int newPin = getConnectedDatabase()
                    .getCollection(MongoDBDAO.PATIENTS_COLLECTION)
                    .aggregate(Arrays.asList(Aggregates.group(null,
                            Accumulators.max(Patient.PIN_ATTRIBUTE, "$" + Patient.PIN_ATTRIBUTE))))
                    .first()
                    .getInteger(Patient.PIN_ATTRIBUTE);
            ++newPin;

            Patient newPatient = new Patient();
            newPatient.setId(new ObjectId());
            newPatient.setPatientId(newPatient.getId().toHexString());
            newPatient.setPin(newPin);
            newPatient.setCreatedAt(new Date());
            newPatient.setEndDate(new Date());
            newPatient.setStartDate(new Date());
            newPatient.setUpdatedAt(new Date());
            newPatient.setState(PatientState.CREATED.status());

            MongoDatabase database = getConnectedDatabase();
            MongoCollection<Patient> patientCollection = database.getCollection(MongoDBDAO.PATIENTS_COLLECTION, Patient.class);

            patientCollection.insertOne(newPatient);

//            trial.getPatients().add(newPatient.getPatientId());
//
//            trialsCollection.replaceOne(Filters.eq(Trial.TRIALID_ATTRIBUTE, trialID), trial);

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

            if(patient == null)
                return NullObjects.getNullPatient();
            return patient;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ActivityInstance deleteActivityInstance(String activityInstanceId) {
        try{
            MongoDatabase database = getConnectedDatabase();
            MongoCollection<ActivityInstance> activityInstanceMongoCollection =
                    database.getCollection(ACTIVITYINSTANCES_COLLECTION, ActivityInstance.class);

            ActivityInstance deletedInstance = activityInstanceMongoCollection
                    .findOneAndDelete(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, activityInstanceId));

            if(deletedInstance == null)
                return NullObjects.getNullActivityInstance();
            return deletedInstance;
        }catch (Exception e){
            System.out.println("SOME PROBLEM DELETING ACTIVITY INSTANCE " + activityInstanceId);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ActivityInstance createActivityInstance(ActivityInstance instance) {
        try{
            MongoDatabase database = getConnectedDatabase();
            MongoCollection<ActivityInstance> activityInstanceMongoCollection =
                    database.getCollection(ACTIVITYINSTANCES_COLLECTION, ActivityInstance.class);

            Patient patient = getPatient(instance.getPatientPin());
            if(patient == null)
                return NullObjects.getNullActivityInstance();

            ObjectId id = ObjectId.get();
            instance.setId(id);
            instance.setActivityInstanceId(id.toHexString());

            activityInstanceMongoCollection.insertOne(instance);
            patient.getActivityInstances().add(instance.getActivityInstanceId());
            patient.setUpdatedAt(new Date());

            updatePatients(patient);
            return instance;
        }catch (Exception e){
            System.out.println("SOME ERROR INSERTING NEW ACTIVITY INSTANCE INTO DATABASE");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ActivityInstance getActivityInstance(String activityInstanceId) {
        try{
            MongoDatabase database = getConnectedDatabase();
            MongoCollection<ActivityInstance> activityInstanceMongoCollection =
                    database.getCollection(ACTIVITYINSTANCES_COLLECTION, ActivityInstance.class);

            ActivityInstance instance = activityInstanceMongoCollection
                    .find(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, activityInstanceId))
                    .projection(Projections.excludeId())
                    .first();

            return instance;
        }catch (NullPointerException ne){
            System.out.println("SOME PROBLEM IN GETTING ACTIVITY INSTANCE WITH ID " + activityInstanceId);
            ne.printStackTrace();
            return NullObjects.getNullActivityInstance();
        }catch (Exception e){
            System.out.println("SOME SERVER PROBLEM IN GETACTIVITYINSTANCEID");
            e.printStackTrace();
            return null;
        }
    }
}
