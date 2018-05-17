package edu.asu.heal.core.api.responses;

import edu.asu.heal.core.api.hal.HALHelperFactory;
import edu.asu.heal.core.api.models.IHealModelType;
import edu.asu.heal.core.api.models.Trial;

import java.util.List;

public class TrialResponse extends HEALResponse {
    @Override
    protected String toEntity(String data) {
        return null;
    }

    @Override
    protected String toEntity(List<IHealModelType> data) {
        List<Trial> trials = (List<Trial>)(List<?>) data;
        return HALHelperFactory
                .getHALGenerator()
                .getTrialsJSON(trials, this.getServerURI() + TRIAL_RESOURCE_PATH, this.getServerURI() + DOMAIN_RESOURCE_PATH, this.getServerURI() + PATIENT_RESOURCE_PATH);
    }

    @Override
    protected String toEntity(IHealModelType data) {
        Trial a = (Trial) data;
        return HALHelperFactory
                .getHALGenerator()
                .getTrialsJSON(a, this.getServerURI() + TRIAL_RESOURCE_PATH, this.getServerURI() + DOMAIN_RESOURCE_PATH, this.getServerURI() + PATIENT_RESOURCE_PATH);
    }
}
