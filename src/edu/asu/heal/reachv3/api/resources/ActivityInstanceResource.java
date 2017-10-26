package edu.asu.heal.reachv3.api.resources;

import edu.asu.heal.core.api.contracts.IHealContract;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/activityinstance")
@Produces(MediaType.APPLICATION_JSON)
public class ActivityInstanceResource {

    private static IHealContract reachService =
            HealServiceFactory.getTheService("edu.asu.heal.reachv3.api.service.ReachService");

    @GET
    public Response fetchActivityInstances(@QueryParam("patientPin") String patientPin,
                                           @QueryParam("trialId") int trialId){

        // URI pattern: /activityisntance?patientPin=1011&trialId=1

        return Response.status(Response.Status.OK).entity(
                reachService.getActivityInstances(patientPin, trialId)).build();
    }

    @GET
    @Path("/{activityInstanceId}")
    public Response fetchActivityInstance(@PathParam("activityInstanceId") String activityInstanceId){
        return Response.status(Response.Status.OK).entity(reachService.getActivityInstance(activityInstanceId)).build();
    }

    @POST
    public Response addActivityInstance(String requestBody){
        String response = reachService.createActivityInstance(requestBody);
        if(response != null) {

            return Response.status(Response.Status.OK).entity("Success").build();
        } else {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }

    @PUT
    public Response updateActivityInstance(String requestBody){
        return Response.status(Response.Status.OK).entity(reachService.updateActivityInstance(requestBody)).build();
    }

    @DELETE
    @Path("/{activityInstanceId}")
    public Response removeActivityInstance(@PathParam("activityInstanceId") String activityInstanceId){
        return Response.status(Response.Status.OK).entity(
                reachService.deleteActivityInstance(activityInstanceId)).build();
    }

    @GET
    @Path("/schedule")
    public Response scheduleActivities(String requestBody){
        return Response.status(Response.Status.OK).entity(
                reachService.scheduleActivityInstancesForPatient(requestBody)).build();
    }

}
