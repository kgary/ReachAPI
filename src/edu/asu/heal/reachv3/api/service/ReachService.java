package edu.asu.heal.reachv3.api.service;

import edu.asu.heal.reachv3.api.dao.ValueObject;
import edu.asu.heal.reachv3.api.errorHandler.ErrorMessage;
import edu.asu.heal.reachv3.api.errorHandler.NotFoundException;
import edu.asu.heal.reachv3.api.model.ModelException;
import edu.asu.heal.reachv3.api.model.ModelFactory;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.ws.rs.core.Response;

public class ReachService {

    private ModelFactory _modelFactory;


    public ReachService(){
        try {
            _modelFactory = new ModelFactory();
        }catch (ModelException me){
            me.printStackTrace();
        }

    }

    public JsonObject checkScheduledActivities(int currentDay, int pin){
        try {
            ValueObject vo = _modelFactory.getScheduledActivities(currentDay);

            JsonBuilderFactory factory = Json.createBuilderFactory(null);
            JsonObject responseJSON = factory.createObjectBuilder()
                    .add("RELAXATION", (Boolean) vo.getAttribute("RELAXATION"))
                    .add("STOP", (Boolean) vo.getAttribute("STOP"))
                    .add("STIC", (Integer) vo.getAttribute("STIC"))
                    .add("WORRYHEADS", (Boolean)vo.getAttribute("WORRYHEADS"))
                    .add("ABMT", (Boolean)vo.getAttribute("ABMT"))
                    .add("DAILYDIARY", (Boolean)vo.getAttribute("DAILYDIARY"))
                    .add("SAFE", (Boolean)vo.getAttribute("SAFE"))
                    .build();

            return responseJSON;
        }catch (ModelException me){
            //TODO String JsonErrorMessage = mapper.writeValueAsString(new ErrorMessage("Invalid survey instance ID"));
            //TODO throw new NotFoundException(Response.Status.BAD_REQUEST, JsonErrorMessage);
            System.out.println("Do something here");
        }
        return  null;
    }
}
