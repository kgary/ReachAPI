package edu.asu.heal.reachv3.api.resources;

import edu.asu.heal.core.api.contracts.IHealContract;
import edu.asu.heal.core.api.resources.HealResource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/activityinstance/")
@Produces(MediaType.APPLICATION_JSON)
public class ReachResource extends HealResource {

    // instantiate the ReachService from ServiceFactory of heal.core package
    private IHealContract reachService = super.fetchHealService("edu.asu.heal.reachv3.api.service.ReachService");

    @Override
    @GET
    public Response getActivityInstances(String patientPin) {

        return Response.status(Response.Status.OK).entity(reachService.getActivityInstances()).build();
    }

    @Override
    public Response getActivityInstanceDetail(Integer activityInstanceId, String patientPin) {
        return super.getActivityInstanceDetail(activityInstanceId, patientPin);
    }

    @Override
    @POST
    public Response addActivityInstance(String requestPayload) {
        String response = reachService.createActivityInstance(requestPayload);
        if(response != null) {

            return Response.status(Response.Status.OK).entity("Success").build();
        } else {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }

    @Override
    public Response updateActivities(String patientPin) {
        return super.updateActivities(patientPin);
    }
}