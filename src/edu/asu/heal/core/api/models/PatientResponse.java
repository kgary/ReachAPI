package edu.asu.heal.core.api.models;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.impl.api.Support;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import java.util.List;

public class PatientResponse extends HEALResponse1 {
    @Override
    protected String toEntity(String data) {
        return data;
    }

    @Override
    protected String toEntity(List<IHealModelType> data) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation finalRepresentation, representation;

        finalRepresentation = factory.newRepresentation(this.getServerURI() + PATIENT_RESOURCE_PATH);
        List<Patient> patients = (List<Patient>)(List<?>) data;
        for (Patient a : patients) {
            representation = factory.newRepresentation()
                    .withProperty("patient", a)
                    .withLink(Support.SELF, this.getServerURI() + PATIENT_RESOURCE_PATH + "/" + String.valueOf(a.getPin()));
            for (String temp : a.getActivityInstances())
                representation.withLink("activity_instance", this.getServerURI() + ACTIVITY_INSTANCE_RESOURCE_PATH + "/" + temp);

            finalRepresentation.withRepresentation("patients", representation);
        }
        return finalRepresentation.toString(RepresentationFactory.HAL_JSON);
    }

    @Override
    protected String toEntity(IHealModelType data) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation representation;

        Patient a = (Patient) data;

        representation = factory.newRepresentation()
                .withProperty("patient", a)
                .withLink(Support.SELF, this.getServerURI() + PATIENT_RESOURCE_PATH + "/" + String.valueOf(a.getPin()));
        for (String temp : a.getActivityInstances())
            representation.withLink("activity_instance", this.getServerURI() + ACTIVITY_INSTANCE_RESOURCE_PATH + "/" + temp);


        return representation.toString(RepresentationFactory.HAL_JSON);
    }
}
