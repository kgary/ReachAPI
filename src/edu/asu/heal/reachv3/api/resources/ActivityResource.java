package edu.asu.heal.reachv3.api.resources;

import edu.asu.heal.core.api.contracts.IHealContract;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/activity")
@Produces(MediaType.APPLICATION_JSON)
public class ActivityResource {

    private static IHealContract reachService =
            HealServiceFactory.getTheService("edu.asu.heal.reachv3.api.service.ReachService");

    @POST
    public Response scheduleActivity(String requestBody){
        // schedules activity for a patient of a trial
        return Response.status(Response.Status.OK).entity(reachService.createActivity(requestBody)).build();
    }
}
