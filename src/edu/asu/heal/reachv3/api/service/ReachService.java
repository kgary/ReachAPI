package edu.asu.heal.reachv3.api.service;

import com.sun.org.apache.regexp.internal.RE;
import edu.asu.heal.reachv3.api.dao.ValueObject;
import edu.asu.heal.reachv3.api.errorHandler.ErrorMessage;
import edu.asu.heal.reachv3.api.errorHandler.NotFoundException;
import edu.asu.heal.reachv3.api.model.ModelException;
import edu.asu.heal.reachv3.api.model.ModelFactory;

import javax.json.*;
import javax.json.stream.JsonParser;
import javax.ws.rs.core.Response;
import java.io.StringReader;

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
                    .add("WORRY_HEADS", (Boolean)vo.getAttribute("WORRY_HEADS"))
                    .add("ABMT", (Boolean)vo.getAttribute("ABMT"))
                    .add("DAILY_DIARY", (Boolean)vo.getAttribute("DAILY_DIARY"))
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

    public JsonObject scheduleActivities(String requestPayload){
        try{
            JsonParser parser = Json.createParser(new StringReader(requestPayload));
            String STOP, Relaxation, DailyDiary, SAFE, WorryHeads, ABMT;
            STOP = Relaxation = DailyDiary =  SAFE = WorryHeads = ABMT = null;
            int STIC = -1;

            while(parser.hasNext()){
                JsonParser.Event event = parser.next();
                if(event == JsonParser.Event.KEY_NAME){
                    switch (parser.getString()){
                        case "STOP":
                            if(parser.next() != JsonParser.Event.VALUE_FALSE)
                                STOP = parser.getString();
                            break;
                        case "SAFE":
                            if(parser.next() != JsonParser.Event.VALUE_FALSE)
                                SAFE = parser.getString();
                            break;
                        case "RELAXATION":
                            if(parser.next() != JsonParser.Event.VALUE_FALSE)
                                Relaxation = parser.getString();
                            break;
                        case "WORRY_HEADS":
                            if(parser.next() != JsonParser.Event.VALUE_FALSE)
                                WorryHeads = parser.getString();
                            break;
                        case "ABMT":
                            if(parser.next() != JsonParser.Event.VALUE_FALSE)
                                ABMT = parser.getString();
                            break;
                        case "DAILY_DIARY":
                            if(parser.next() != JsonParser.Event.VALUE_FALSE)
                                ABMT = parser.getString();
                            break;
                        case "STIC":
                            parser.next();
                            STIC = parser.getInt();
                            break;
                    }

                }
            }

            _modelFactory.scheduleActivities(STOP, SAFE, WorryHeads, DailyDiary, ABMT, Relaxation, STIC);


        }catch (Exception e){

        }
        return null;
    }
}
