package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.contracts.IHealContract;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.core.Response;

public class HealResource {

    // fetch the instance of `serviceImplementationClass` implementing the IHealContract
    protected IHealContract fetchHealService(String serviceImplementationClass){
        return HealServiceFactory.getTheService(serviceImplementationClass);
    }

    public Response getActivityInstances(String patientPin) {
        return null;
    }

    public Response getActivityInstanceDetail(Integer activityInstanceId, String patientPin) {
        return null;
    }

    public Response addActivityInstance(String requestPayload) { return null; }

    public Response updateActivities(String patientPin) {
        return null;
    }
}
