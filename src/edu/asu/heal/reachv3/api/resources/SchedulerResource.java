package edu.asu.heal.reachv3.api.resources;

import edu.asu.heal.reachv3.api.service.ReachService;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/activities/")
@Produces(MediaType.APPLICATION_JSON)
public class SchedulerResource {

    ReachService reachService = new ReachService();

    /*
    *
    * Will need some form of pin to distinguish between different API clients. Pin will be passed as parameter to the
    * method below through query parameter.
    */
    @GET
    @Path("/scheduledactivities/")
    public Response getScheduledActivities(){
        Response response = null;
        JsonObject jsonResponse = reachService.checkScheduledActivities(0,0);

        response = Response.status(Response.Status.OK).entity(jsonResponse).build();

        return response;
    }

    @POST
    @Path("/scheduleactivities")
    public Response scheduleActivities(String requestPayload){
        Response response = null;
        JsonObject jsonResponse = reachService.scheduleActivities(requestPayload);

        response = Response.status(Response.Status.OK).entity(jsonResponse).build();
        return response;
    }


}
