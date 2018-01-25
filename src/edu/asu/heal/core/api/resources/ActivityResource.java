package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/activity")
@Produces(MediaType.APPLICATION_JSON)
public class ActivityResource {

    private static HealService reachService =
            HealServiceFactory.getTheService("edu.asu.heal.reachv3.api.service.ReachService");

    /**
     * @api {post} /activity Create Activity
     * @apiName CreateActivity
     * @apiGroup Activity
     *
     * @apiParam {String} ActivityId Id of the Activity
     * @apiParam {String} ActivityName Name of the Activity
     * @apiParam {String} ActivityType Type of the Activity
     * @apiParam {String} Description Description of the Activity
     * @apiParam {String} CanonicalOrder Canonical Order of the Activity
     * @apiParam {String} MetaData Meta Data of the Activity
     *
     * @apiParam (Login) {String} pass Only logged in user can post this
     *
     * */
    @POST
    public Response scheduleActivity(String requestBody){
        // schedules activity for a patient of a trial
        return Response.status(Response.Status.OK).entity(reachService.createActivity(requestBody)).build();
    }
}
