package edu.asu.heal.core.api.responses;

import edu.asu.heal.core.api.models.Domain;
import edu.asu.heal.core.api.hal.HALHelperFactory;
import edu.asu.heal.core.api.models.IHealModelType;

import java.util.List;

public class DomainResponse extends HEALResponse {
    @Override
    protected String toEntity(String data) {
        return data;
    }

    @Override
    protected String toEntity(List<IHealModelType> data) {
        List<Domain> domains = (List<Domain>)(List<?>) data;

        return HALHelperFactory
                .getHALGenerator()
                .getDomainsJSON(domains, this.getServerURI() + DOMAIN_RESOURCE_PATH, this.getServerURI() + ACTIVITY_RESOURCE_PATH, this.getServerURI() + TRIAL_RESOURCE_PATH);
    }

    @Override
    protected String toEntity(IHealModelType data) {
        Domain a = (Domain) data;

        return HALHelperFactory
                .getHALGenerator()
                .getDomainsJSON(a, this.getServerURI() + DOMAIN_RESOURCE_PATH, this.getServerURI() + ACTIVITY_RESOURCE_PATH, this.getServerURI() + TRIAL_RESOURCE_PATH);
    }
}
