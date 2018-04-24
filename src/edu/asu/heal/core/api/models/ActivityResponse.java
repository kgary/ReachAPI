package edu.asu.heal.core.api.models;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.impl.api.Support;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import java.util.List;

public class ActivityResponse extends HEALResponse {
    @Override
    protected String toEntity(String data) {
        return data;
    }

    @Override
    protected String toEntity(List<IHealModelType> data) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation finalRepresentation, representation;

        finalRepresentation = factory.newRepresentation(this.getServerURI() + ACTIVITY_RESOURCE_PATH);

        List<Activity> activities = (List<Activity>)(List<?>) data;
        for (Activity a : activities) {
            representation = factory.newRepresentation()
                    .withProperty("activity", a)
                    .withLink(Support.SELF, this.getServerURI() + ACTIVITY_RESOURCE_PATH + "/" + a.getActivityId());

            finalRepresentation.withRepresentation("activities", representation);
        }
        return finalRepresentation.toString(RepresentationFactory.HAL_JSON);
    }

    @Override
    protected String toEntity(IHealModelType data) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation representation;

        Activity a = (Activity) data;

        representation = factory.newRepresentation()
                .withProperty("activity", a)
                .withLink(Support.SELF, this.getServerURI() + ACTIVITY_RESOURCE_PATH + "/" + a.getActivityId());

        return representation.toString(RepresentationFactory.HAL_JSON);
    }
}
