package edu.asu.heal.core.api.models;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.impl.api.Support;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import java.util.List;

public class TrialResponse extends HEALResponse1 {
    @Override
    protected Object toEntity(String data) {
        return null;
    }

    @Override
    protected Object toEntity(List data) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation finalRepresentation, representation;

        finalRepresentation = factory.newRepresentation(this.getServerURI() + TRIAL_RESOURCE_PATH);
        List<Trial> trials = (List<Trial>) data;
        for (Trial a : trials) {
            representation = factory.newRepresentation()
                    .withProperty("trial", a)
                    .withLink(Support.SELF, this.getServerURI() + TRIAL_RESOURCE_PATH + "/" + a.getTrialId())
                    .withLink("domain", this.getServerURI() + DOMAIN_RESOURCE_PATH + "/" + a.getDomainId());
            for (String temp : a.getPatients())
                representation.withLink("patients", this.getServerURI() + PATIENT_RESOURCE_PATH + "/" + temp);

            finalRepresentation.withRepresentation("trials", representation);
        }
        return finalRepresentation.toString(RepresentationFactory.HAL_JSON);
    }

    @Override
    protected <T> Object toEntity(T data) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation representation;
        Trial a = (Trial) data;
        representation = factory.newRepresentation()
                .withProperty("trial", a)
                .withLink(Support.SELF, this.getServerURI() + TRIAL_RESOURCE_PATH + "/" + a.getTrialId())
                .withLink("domain", this.getServerURI() + DOMAIN_RESOURCE_PATH + "/" + a.getDomainId());
        for (String temp : a.getPatients())
            representation.withLink("patients", this.getServerURI() + PATIENT_RESOURCE_PATH + "/" + temp);


        return representation.toString(RepresentationFactory.HAL_JSON);
    }
}
