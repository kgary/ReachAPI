package edu.asu.heal.core.api.dao.impl;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOException;
import edu.asu.heal.core.api.models.Activity;
import edu.asu.heal.core.api.models.Domain;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.*;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBDAO implements DAO {
    private String __mongoUser;
    private String __mongoPassword;
    private String __mongoHost;
    private int __mongoPort;
    private String __mongoDBName;
    private String __mongoURI;

    public MongoDBDAO() {}

    public MongoDBDAO(Properties properties){
        __mongoURI = properties.getProperty("mongo.uri");
        __mongoDBName = properties.getProperty("mongo.dbname");

        // Alternatively, the following properties can be used if we don't want to deal with URIs separately
//        __mongoUser = properties.getProperty("mongo.username");
//        __mongoPassword = properties.getProperty("mongo.password");
//        __mongoHost = properties.getProperty("mongo.host");
//        __mongoPort = Integer.parseInt(properties.getProperty("mongo.port"));

    }

    private MongoDatabase getConnectedDatabase(){
        try {

            // create codec registry for POJOs
            CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                    fromProviders(PojoCodecProvider.builder().automatic(true).build()));

            MongoClient mongoClient = new MongoClient(new MongoClientURI(__mongoURI));

            // get handle to "_mongoDBName" database
            return mongoClient.getDatabase(__mongoDBName).withCodecRegistry(pojoCodecRegistry);

            // Alternatively the following lines of code can be used
            // MongoCredential credential = MongoCredential.createCredential(__mongoUser, __mongoDBName, __mongoPassword.toCharArray());
            // MongoClient client = new MongoClient(new ServerAddress(__mongoHost, __mongoPort), credential, MongoClientOptions.builder().build());
        }catch (Exception e){
            System.out.println("SOME ERROR GETTING MONGO CONNECTION");
            e.printStackTrace();
            return null;
        }

    }

    public List<Domain> getDomains(){
        MongoDatabase database = getConnectedDatabase();
        MongoCollection<Domain> domainCollection = database.getCollection("domains", Domain.class);

        return domainCollection.find().into(new ArrayList<>());
    }

    @Override
    public String createDomain(Domain instance) {
        MongoDatabase database = getConnectedDatabase();
        MongoCollection<Domain> domainCollection = database.getCollection("domains", Domain.class);

        domainCollection.insertOne(instance);

        return "SUCCESS";
    }

    @Override
    public Object getScheduledActivities(int currentDay) throws DAOException {
        return null;
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

    @Override
    public String getActivities(String domain) throws DAOException {
        try {
            MongoDatabase database = getConnectedDatabase();
            MongoCollection<Document> domainCollection = database.getCollection("domains");

            Document document = domainCollection
                    .find()
                    .filter(Filters.eq("title", domain.toUpperCase()))
                    .projection(Projections.fields(Projections.include("activities"),
                            Projections.excludeId()))
                    .first();

            return document.toJson();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getTrials(String domain) throws DAOException {
        try{
            MongoDatabase database = getConnectedDatabase();
            MongoCollection<Document> domainCollection = database.getCollection("domains");

            Document document = domainCollection
                    .find()
                    .filter(Filters.eq("title", domain.toUpperCase()))
                    .projection(Projections.include("trials.title",
                            "trials.description",
                            "trials.endDate",
                            "trials.startDate"))
                    .first();

            return document.toJson();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getPatients(String trialId) throws DAOException {
        try{
            MongoDatabase database = getConnectedDatabase();
            MongoCollection<Document> domainCollection = database.getCollection("domains");

            Document document = domainCollection
                    .find()
                    .filter(Filters.eq("_id", new ObjectId(trialId)))
                    .projection(Projections.include("trials.patients"))
                    .first();

            return document.toJson();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
