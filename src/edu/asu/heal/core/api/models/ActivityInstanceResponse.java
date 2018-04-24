package edu.asu.heal.core.api.models;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.impl.api.Support;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import java.util.List;

public class ActivityInstanceResponse extends HEALResponse {

    @Override
    protected String toEntity(String data) {
        return data;
    }

    @Override
    protected String toEntity(List<IHealModelType> data) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation finalRepresentation, representation;

        finalRepresentation = factory.newRepresentation(this.getServerURI() + ACTIVITY_INSTANCE_RESOURCE_PATH);
        List<ActivityInstance> activityInstances = (List<ActivityInstance>)(List<?>) data;
        for (ActivityInstance a : activityInstances) {
            representation = factory.newRepresentation()
                    .withProperty("activity_instance", a)
                    .withLink(Support.SELF, this.getServerURI() + ACTIVITY_INSTANCE_RESOURCE_PATH + "/" + a.getActivityInstanceId())
                    .withLink("patient_pin", this.getServerURI() + PATIENT_RESOURCE_PATH + "/" + String.valueOf(a.getPatientPin()))
                    .withLink("activity_type", this.getServerURI() + ACTIVITY_RESOURCE_PATH + "/" + a.getInstanceOf().getActivityId());

            finalRepresentation.withRepresentation("activity_instances", representation);
        }
        return finalRepresentation.toString(RepresentationFactory.HAL_JSON);
    }

    @Override
    protected String toEntity(IHealModelType instance) {
        ActivityInstance activityInstance = (ActivityInstance) instance;
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation representation;

        representation = factory.newRepresentation()
                .withProperty("activity_instance", activityInstance)
                .withLink(Support.SELF, this.getServerURI() + ACTIVITY_INSTANCE_RESOURCE_PATH + "/" + activityInstance.getActivityInstanceId())
                .withLink("patient_pin", this.getServerURI() + PATIENT_RESOURCE_PATH + "/" + String.valueOf(activityInstance.getPatientPin()))
                .withLink("activity_type", this.getServerURI() + ACTIVITY_RESOURCE_PATH + "/" + activityInstance.getInstanceOf().getActivityId());

        return representation.toString(RepresentationFactory.HAL_JSON);
    }
}
