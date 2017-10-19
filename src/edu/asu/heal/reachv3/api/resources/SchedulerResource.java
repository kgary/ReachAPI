package edu.asu.heal.reachv3.api.resources;

import com.sun.org.apache.regexp.internal.RE;
import edu.asu.heal.reachv3.api.model.ScheduleModel;
import edu.asu.heal.reachv3.api.service.ReachService;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/activityinstance/")
@Produces(MediaType.APPLICATION_JSON)
public class SchedulerResource {

    ReachService reachService = new ReachService();

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

        return Response.status(Response.Status.OK).entity(reachService.getActivityInstances("1011")).build();

    }

    @POST
    public Response addActivityInstance(String requestPayload){
        /*Response response = null;
        JsonObject jsonResponse = reachService.scheduleActivities(requestPayload);

        response = Response.status(Response.Status.OK).entity(jsonResponse).build();
        return response;*/
        String response = reachService.createActivityInstance(requestPayload);
        if(response != null)
            return Response.status(Response.Status.OK).entity("Success").build();

        else
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();


    }


}
