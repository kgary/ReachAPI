package edu.asu.heal.core.api.responses;

import edu.asu.heal.core.api.hal.HALHelperFactory;
import edu.asu.heal.core.api.models.IHealModelType;
import edu.asu.heal.core.api.models.Patient;

import java.util.List;

public class PatientResponse extends HEALResponse {
    @Override
    protected String toEntity(String data) {
        return data;
    }

    @Override
    protected String toEntity(List<IHealModelType> data) {
        List<Patient> patients = (List<Patient>)(List<?>) data;

        return HALHelperFactory
                .getHALGenerator()
                .getPatientsJSON(patients, this.getServerURI() + PATIENT_RESOURCE_PATH, this.getServerURI() + ACTIVITY_INSTANCE_RESOURCE_PATH);
    }

    @Override
    protected String toEntity(IHealModelType data) {

        Patient a = (Patient) data;

        return HALHelperFactory
                .getHALGenerator()
                .getPatientsJSON(a, this.getServerURI() + PATIENT_RESOURCE_PATH, this.getServerURI() + ACTIVITY_INSTANCE_RESOURCE_PATH);
    }
}
