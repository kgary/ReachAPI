package edu.asu.heal.core.api.models;

import java.util.Date;

public class NullObjects {
    private static final Activity NULL_ACTIVITY;
    private static final ActivityInstance NULL_ACTIVITY_INSTANCE;
    private static final ActivityInstanceType NULL_ACTIVITY_INSTANCE_TYPE;
    private static final Patient NULL_PATIENT;


    static {
        Activity a = new Activity();
        a.setTitle("NULL");
        a.setDescription("NULL");
        a.setActivityId("NULL");
        a.setCreatedAt(new Date(0));
        a.setUpdatedAt(new Date(0));

        NULL_ACTIVITY = a;
        a = null;


        ActivityInstance instance = new ActivityInstance();
        instance.setActivityInstanceId("NULL");
        instance.setCreatedAt(new Date(0));
        instance.setUpdatedAt(new Date(0));
        instance.setStartTime(new Date(0));
        instance.setEndTime(new Date(0));
        instance.setUserSubmissionTime(new Date(0));
        instance.setActualSubmissionTime(new Date(0));
        instance.setInstanceOf(new ActivityInstanceType("NULL", "NULL"));
        instance.setState("NULL");
        instance.setDescription("NULL");

        NULL_ACTIVITY_INSTANCE = instance;
        instance = null;

        ActivityInstanceType instanceType = new ActivityInstanceType("NULL", "NULL");
        NULL_ACTIVITY_INSTANCE_TYPE = instanceType;


        Patient patient = new Patient();
        patient.setPatientId("NULL");
        patient.setState("NULL");
        patient.setCreatedAt(new Date(0));
        patient.setStartDate(new Date(0));
        patient.setPin(-1);

        NULL_PATIENT = patient;

    }

    public static Activity getNullActivity() {
        return NULL_ACTIVITY;
    }

    public static ActivityInstance getNullActivityInstance() {
        return NULL_ACTIVITY_INSTANCE;
    }

    public static ActivityInstanceType getNullActivityInstanceType() {
        return NULL_ACTIVITY_INSTANCE_TYPE;
    }

    public static Patient getNullPatient() {
        return NULL_PATIENT;
    }
}
