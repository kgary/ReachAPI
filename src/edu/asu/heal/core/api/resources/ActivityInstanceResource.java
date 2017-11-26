package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/activityinstance/")
@Produces(MediaType.APPLICATION_JSON)
public class ActivityInstanceResource {

    private static HealService reachService =
            HealServiceFactory.getTheService("edu.asu.heal.reachv3.api.service.ReachService");

    @GET
    public Response fetchActivityInstances(@QueryParam("patientPin") String patientPin,
                                           @QueryParam("trialId") int trialId){

        // URI pattern: /activityisntance?patientPin=1011&trialId=1

        return Response.status(Response.Status.OK).entity(
                reachService.getActivityInstances(patientPin, trialId)).build();
    }

    @GET
    @Path("/{activityInstanceId}/")
    public Response fetchActivityInstance(@PathParam("activityInstanceId") String activityInstanceId){
        return Response.status(Response.Status.OK).entity(reachService.getActivityInstance(activityInstanceId)).build();
    }

    @POST
    public Response addActivityInstance(String requestBody){
        String response = reachService.createActivityInstance(requestBody);
        if(response != null) {

            return Response.status(Response.Status.CREATED).entity("Success").build();
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
    @Path("/makebelieve/")
    public Response fetchMakeBelieveInstance(){
        String makeBelieveInstanceString = reachService.getMakeBelieveInstance();
        if(makeBelieveInstanceString == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some server error. Please contact administrator").build();
        return Response.status(Response.Status.OK).entity(makeBelieveInstanceString).build();
    }

    @GET
    @Path("/makebelieveanswers/")
    public Response fetchMakeBelieveInstanceAnswers(@QueryParam("situation_id") int situationId){
        String makeBelieveInstanceAnswerString = reachService.getMakeBelieveInstanceAnswer(situationId);
        if(makeBelieveInstanceAnswerString== null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some server error. Please contact administrator").build();
        if(makeBelieveInstanceAnswerString.equals("Bad Request"))
            return Response.status(400).build();
        return Response.status(Response.Status.OK).entity(makeBelieveInstanceAnswerString).build();
    }

    @PUT()
    @Path("/makebelieveinstance/")
    public Response updateMakeBelieveInstance(@QueryParam("situation_id") int situationId, String requestBody){
        int response = reachService.updateMakeBelieveInstance(situationId, requestBody);
        return Response.status(response).build();
    }
}
