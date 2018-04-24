package edu.asu.heal.core.api.models;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.impl.api.Support;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import java.util.List;

public class DomainResponse extends HEALResponse {
    @Override
    protected String toEntity(String data) {
        return data;
    }

    @Override
    protected String toEntity(List<IHealModelType> data) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation finalRepresentation, representation;

        finalRepresentation = factory.newRepresentation(this.getServerURI() + DOMAIN_RESOURCE_PATH);
        List<Domain> domains = (List<Domain>)(List<?>) data;
        for (Domain a : domains) {
            representation = factory.newRepresentation()
                    .withProperty("domain", a)
                    .withLink(Support.SELF, this.getServerURI() + DOMAIN_RESOURCE_PATH + "/" + a.getDomainId());
            for (String temp : a.getActivities())
                representation.withLink("activity", this.getServerURI() + ACTIVITY_RESOURCE_PATH + "/" + temp);
            for (String temp : a.getTrials())
                representation.withLink("trial", this.getServerURI() + TRIAL_RESOURCE_PATH + "/" + temp);

            finalRepresentation.withRepresentation("domains", representation);
        }
        return finalRepresentation.toString(RepresentationFactory.HAL_JSON);    }

    @Override
    protected String toEntity(IHealModelType data) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation representation;
        Domain a = (Domain) data;

        representation = factory.newRepresentation()
                .withProperty("domain", a)
                .withLink(Support.SELF, this.getServerURI() + DOMAIN_RESOURCE_PATH + "/" + a.getDomainId());
        for (String temp : a.getActivities())
            representation.withLink("activity", this.getServerURI() + ACTIVITY_RESOURCE_PATH + "/" + temp);
        for (String temp : a.getTrials())
            representation.withLink("trial", this.getServerURI() + TRIAL_RESOURCE_PATH + "/" + temp);


        return representation.toString(RepresentationFactory.HAL_JSON);
    }
}
