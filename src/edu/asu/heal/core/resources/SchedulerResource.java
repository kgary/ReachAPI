package edu.asu.heal.core.resources;

import edu.asu.heal.core.service.HealService;
import edu.asu.heal.core.service.HealServiceFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/activityinstance/")
@Produces(MediaType.APPLICATION_JSON)
public class SchedulerResource {

    HealService service = HealServiceFactory.getTheService();

    /*
    *
    * Will need some form of pin to distinguish between different API clients. Pin will be passed as parameter to the
    * method below through query parameter.
    */
    @GET
    public Response fetchActivityInstances(){
        /*Response response = null;
        String jsonResponse = reachService.checkScheduledActivities(0,0);

        response = Response.status(Response.Status.OK).entity(jsonResponse).build();
        return response;*/

        return Response.status(Response.Status.OK).entity(service.getActivityInstances("1011")).build();

    }

    @POST
    public Response addActivityInstance(String requestPayload){
        /*Response response = null;
        JsonObject jsonResponse = reachService.scheduleActivities(requestPayload);

        response = Response.status(Response.Status.OK).entity(jsonResponse).build();
        return response;*/
        String response = service.createActivityInstance(requestPayload);
        if(response != null)
            return Response.status(Response.Status.OK).entity("Success").build();

        else
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();


    }


}
