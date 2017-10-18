package edu.asu.heal.reachv3.api.service;

import edu.asu.heal.core.HealCoreInterface;
import edu.asu.heal.reachv3.api.model.ScheduleModel;

import java.util.Date;
import java.util.Random;

public class ReachService implements HealCoreInterface {

    @Override
    public String getActivityInstances(String patientPin) {

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
    }

    @Override
    public String getActivityInstanceDetail(Integer activityInstanceId, String patientPin) {

        // TODO

        return null;
    }

    @Override
    public String createActivityInstance(Date startTime, Date endTime, Date userSubmissionType,
                                         Date actualSubmissionType, String State, String patientPin,
                                         String Sequence, String activityTitle, String description) {

        // TODO

        return null;
    }

    @Override
    public String updateActivities(String patientPin) {

        // TODO

        return null;
    }
}