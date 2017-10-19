package edu.asu.heal.reachv3.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asu.heal.core.HealCoreInterface;
import edu.asu.heal.reachv3.api.model.ScheduleModel;

import java.util.Date;
import java.util.Random;

public class ReachService implements HealCoreInterface {

    @Override
    public String getActivityInstances(String patientPin) {
        try {
            // return the mockup data
            Random randomizer = new Random();

            ScheduleModel instance = new ScheduleModel();
            instance.setWeekNumber(randomizer.nextInt(7));
            instance.setDay(randomizer.nextInt(43));
            instance.setRelaxation(randomizer.nextBoolean());
            instance.setDiaryEvent1(randomizer.nextBoolean());
            instance.setDiaryEvent2(randomizer.nextBoolean());
            instance.setStop(randomizer.nextBoolean());
            instance.setStopWorryheads(randomizer.nextBoolean());
            instance.setStic(randomizer.nextInt(4));
            instance.setSudScaleEvent(randomizer.nextBoolean());
            instance.setRtu(randomizer.nextBoolean());
            instance.setBlob(randomizer.nextBoolean());
            instance.setSafe(randomizer.nextBoolean());

            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(instance);
        } catch(Exception e){
            //TODO String JsonErrorMessage = mapper.writeValueAsString(new ErrorMessage("Invalid survey instance ID"));
            //TODO throw new NotFoundException(Response.Status.BAD_REQUEST, JsonErrorMessage);

            System.out.println("Do something here");
        }
        return null;
    }

    @Override
    public String getActivityInstanceDetail(Integer activityInstanceId, String patientPin) {

        // TODO

        return null;
    }

    @Override
    public String createActivityInstance(String requestPayload) {
        try {
            //Mock data as of now
            ObjectMapper mapper = new ObjectMapper();
            ScheduleModel model = mapper.readValue(requestPayload, ScheduleModel.class);

            System.out.println(model.toString());

            return "OK";
        }catch (Exception e){
            System.out.println("Error from createActivityInstance() in ReachService");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String updateActivities(String patientPin) {

        // TODO

        return null;
    }
}