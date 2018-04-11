package edu.asu.heal.core.api.models;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.impl.api.Support;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import edu.asu.heal.core.api.resources.*;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;

import java.util.List;

public class HEALResponse {

    private static final String ACTIVITY_RESOURCE_PATH;
    private static final String ACTIVITY_INSTANCE_RESOURCE_PATH;
    private static final String DOMAIN_RESOURCE_PATH;
    private static final String PATIENT_RESOURCE_PATH;
    private static final String TRIAL_RESOURCE_PATH;

    static{

        ACTIVITY_RESOURCE_PATH = new JerseyUriBuilder().path(ActivityResource.class).toTemplate().replace("/", "");
        ACTIVITY_INSTANCE_RESOURCE_PATH = new JerseyUriBuilder().path(ActivityInstanceResource.class).toTemplate().replace("/", "");
        DOMAIN_RESOURCE_PATH = new JerseyUriBuilder().path(DomainResource.class).toTemplate().replace("/", "");
        PATIENT_RESOURCE_PATH = new JerseyUriBuilder().path(PatientResource.class).toTemplate().replace("/", "");
        TRIAL_RESOURCE_PATH = new JerseyUriBuilder().path(TrialsResource.class).toTemplate().replace("/", "");
    }

    private int statusCode;
    private Object data;
    private String serverURI;

    private HEALResponse() {
    }

    // getters and setters
    public int getStatusCode() {
        return statusCode;
    }

    public Object getData() {
        return data;
    }

    public String getServerURI() {
        return serverURI;
    }

    public Object toEntity() {
        if (this.data instanceof List) {
            RepresentationFactory factory = new StandardRepresentationFactory();
            Representation finalRepresentation, representation;

            List data = (List) this.data;
            Object data0 = data.get(0);
            if (data0 instanceof Activity) {
                finalRepresentation = factory.newRepresentation(this.serverURI + ACTIVITY_RESOURCE_PATH);
                List<Activity> activities = (List<Activity>) data;
                for (Activity a : activities) {
                    representation = factory.newRepresentation()
                            .withProperty("activity", a)
                            .withLink(Support.SELF, this.serverURI + ACTIVITY_RESOURCE_PATH + "/" + a.getActivityId());

                    finalRepresentation.withRepresentation("activities", representation);
                }
                return finalRepresentation.toString(RepresentationFactory.HAL_JSON);
            } else if (data0 instanceof ActivityInstance) {
                finalRepresentation = factory.newRepresentation(this.serverURI + ACTIVITY_INSTANCE_RESOURCE_PATH);
                List<ActivityInstance> activityInstances = (List<ActivityInstance>) data;
                for (ActivityInstance a : activityInstances) {
                    representation = factory.newRepresentation()
                            .withProperty("activity_instance", a)
                            .withLink(Support.SELF, this.serverURI + ACTIVITY_INSTANCE_RESOURCE_PATH + "/" + a.getActivityInstanceId())
                            .withLink("patient_pin", this.serverURI + PATIENT_RESOURCE_PATH + "/" + String.valueOf(a.getPatientPin()))
                            .withLink("activity_type", this.serverURI + ACTIVITY_RESOURCE_PATH + "/" + a.getInstanceOf().getActivityId());

                    finalRepresentation.withRepresentation("activity_instances", representation);
                }
                return finalRepresentation.toString(RepresentationFactory.HAL_JSON);
            } else if (data0 instanceof Domain) {
                finalRepresentation = factory.newRepresentation(this.serverURI + DOMAIN_RESOURCE_PATH);
                List<Domain> domains = (List<Domain>) data;
                for (Domain a : domains) {
                    representation = factory.newRepresentation()
                            .withProperty("domain", a)
                            .withLink(Support.SELF, this.serverURI + DOMAIN_RESOURCE_PATH + "/" + a.getDomainId());
                    for (String temp : a.getActivities())
                        representation.withLink("activity", this.serverURI + ACTIVITY_RESOURCE_PATH + "/" + temp);
                    for (String temp : a.getTrials())
                        representation.withLink("trial", this.serverURI + TRIAL_RESOURCE_PATH + "/" + temp);

                    finalRepresentation.withRepresentation("domains", representation);
                }
                return finalRepresentation.toString(RepresentationFactory.HAL_JSON);
            } else if (data0 instanceof Patient) {
                finalRepresentation = factory.newRepresentation(this.serverURI + PATIENT_RESOURCE_PATH);
                List<Patient> patients = (List<Patient>) data;
                for (Patient a : patients) {
                    representation = factory.newRepresentation()
                            .withProperty("patient", a)
                            .withLink(Support.SELF, this.serverURI + PATIENT_RESOURCE_PATH + "/" + String.valueOf(a.getPin()));
                    for (String temp : a.getActivityInstances())
                        representation.withLink("activity_instance", this.serverURI + ACTIVITY_INSTANCE_RESOURCE_PATH + "/" + temp);

                    finalRepresentation.withRepresentation("patients", representation);
                }
                return finalRepresentation.toString(RepresentationFactory.HAL_JSON);
            } else if (data0 instanceof Trial) {
                finalRepresentation = factory.newRepresentation(this.serverURI + TRIAL_RESOURCE_PATH);
                List<Trial> trials = (List<Trial>) data;
                for (Trial a : trials) {
                    representation = factory.newRepresentation()
                            .withProperty("trial", a)
                            .withLink(Support.SELF, this.serverURI + TRIAL_RESOURCE_PATH + "/" + a.getTrialId())
                            .withLink("domain", this.serverURI + DOMAIN_RESOURCE_PATH + "/" + a.getDomainId());
                    for (String temp : a.getPatients())
                        representation.withLink("patients", this.serverURI + PATIENT_RESOURCE_PATH + "/" + temp);

                    finalRepresentation.withRepresentation("trials", representation);
                }
                return finalRepresentation.toString(RepresentationFactory.HAL_JSON);
            }

        } else if (this.data instanceof Activity) {
            RepresentationFactory factory = new StandardRepresentationFactory();
            Representation representation;

            Activity a = (Activity) this.data;

            representation = factory.newRepresentation()
                    .withProperty("activity", a)
                    .withLink(Support.SELF, this.serverURI + ACTIVITY_RESOURCE_PATH + "/" + a.getActivityId());

            return representation.toString(RepresentationFactory.HAL_JSON);
        } else if (this.data instanceof ActivityInstance) {
            RepresentationFactory factory = new StandardRepresentationFactory();
            Representation representation;
            ActivityInstance activityInstance = (ActivityInstance) data;

            representation = factory.newRepresentation()
                    .withProperty("activity_instance", activityInstance)
                    .withLink(Support.SELF, this.serverURI + ACTIVITY_INSTANCE_RESOURCE_PATH + "/" + activityInstance.getActivityInstanceId())
                    .withLink("patient_pin", this.serverURI + PATIENT_RESOURCE_PATH + "/" + String.valueOf(activityInstance.getPatientPin()))
                    .withLink("activity_type", this.serverURI + ACTIVITY_RESOURCE_PATH + "/" + activityInstance.getInstanceOf().getActivityId());

            return representation.toString(RepresentationFactory.HAL_JSON);
        } else if (this.data instanceof Domain) {
            RepresentationFactory factory = new StandardRepresentationFactory();
            Representation representation;
            Domain a = (Domain) data;

            representation = factory.newRepresentation()
                    .withProperty("domain", a)
                    .withLink(Support.SELF, this.serverURI + DOMAIN_RESOURCE_PATH + "/" + a.getDomainId());
            for (String temp : a.getActivities())
                representation.withLink("activity", this.serverURI + ACTIVITY_RESOURCE_PATH + "/" + temp);
            for (String temp : a.getTrials())
                representation.withLink("trial", this.serverURI + TRIAL_RESOURCE_PATH + "/" + temp);


            return representation.toString(RepresentationFactory.HAL_JSON);
        } else if (this.data instanceof Patient) {
            RepresentationFactory factory = new StandardRepresentationFactory();
            Representation representation;

            Patient a = (Patient) data;

            representation = factory.newRepresentation()
                    .withProperty("patient", a)
                    .withLink(Support.SELF, this.serverURI + PATIENT_RESOURCE_PATH + "/" + String.valueOf(a.getPin()));
            for (String temp : a.getActivityInstances())
                representation.withLink("activity_instance", this.serverURI + ACTIVITY_INSTANCE_RESOURCE_PATH + "/" + temp);


            return representation.toString(RepresentationFactory.HAL_JSON);
        } else if (this.data instanceof Trial) {
            RepresentationFactory factory = new StandardRepresentationFactory();
            Representation representation;
            Trial a = (Trial) data;
            representation = factory.newRepresentation()
                    .withProperty("trial", a)
                    .withLink(Support.SELF, this.serverURI + TRIAL_RESOURCE_PATH + "/" + a.getTrialId())
                    .withLink("domain", this.serverURI + DOMAIN_RESOURCE_PATH + "/" + a.getDomainId());
            for (String temp : a.getPatients())
                representation.withLink("patients", this.serverURI + PATIENT_RESOURCE_PATH + "/" + temp);


            return representation.toString(RepresentationFactory.HAL_JSON);
        }

        return this;

    }

    public static class HEALResponseBuilder {
        private HEALResponse _response;

        public HEALResponseBuilder() {
            _response = new HEALResponse();
        }

        public HEALResponseBuilder setStatusCode(int statusCode) {
            this._response.statusCode = statusCode;
            return this;
        }

        public HEALResponseBuilder setData(Object data) {
            this._response.data = data;
            return this;
        }

        public HEALResponseBuilder setServerURI(String serverURI) {
            this._response.serverURI = serverURI;
            return this;
        }

        public HEALResponse build() {
            return this._response;
        }
    }
}