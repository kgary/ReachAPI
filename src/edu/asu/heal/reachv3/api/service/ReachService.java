package edu.asu.heal.reachv3.api.service;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonWriter;

public class ReachService {

    public ReachService(){

    }

    public JsonObject checkScheduledActivities(int currentDay, int pin){
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObject responseJSON = factory.createObjectBuilder()
                .add("RELAXATION", true)
                .add("STOP", true)
                .add("STIC", true)
                .add("WORRYHEADS", true)
                .add("ABMT", true)
                .build();

        return responseJSON;

    }
}
