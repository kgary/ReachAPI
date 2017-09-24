package edu.asu.heal.reachv3.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/activities/")
@Produces(MediaType.APPLICATION_JSON)
public class SchedulerResource {

    /*
    *
    * Will need some form of pin to distinguish between different API clients. Pin will be passed as parameter to the
    * method below through query parameter.
    */
    @GET
    @Path("/scheduledactivities/")
    public Response getScheduledActivities(){
        return null;
    }
}
