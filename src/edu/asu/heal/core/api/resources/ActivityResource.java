package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.Activity;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/activities")
@Produces(MediaType.APPLICATION_JSON)
public class ActivityResource {

    private static HealService reachService =
            HealServiceFactory.getTheService("edu.asu.heal.reachv3.api.service.ReachService");

    /**
     * @apiDefine BadRequestError
     * @apiError (Error 4xx) {400} BadRequest Bad Request Encountered
     * */

    /** @apiDefine UnAuthorizedError
     * @apiError (Error 4xx) {401} UnAuthorized The Client must be authorized to access the resource
     * */

    /** @apiDefine ActivityNotFoundError
     * @apiError (Error 4xx) {404} NotFound Activity cannot be found
     * */

    /**
     * @apiDefine InternalServerError
     * @apiError (Error 5xx) {500} InternalServerError Something went wrong at server, Please contact the administrator!
     * */

    /**
     * @apiDefine NotImplementedError
     * @apiError (Error 5xx) {501} NotImplemented The resource has not been implemented. Please keep patience, our developers are working hard on it!!
     * */

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
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
    @POST
    public Response scheduleActivity(String requestBody){
        // schedules activity for a patient of a trial
        return Response.status(Response.Status.OK).entity(reachService.createActivity(requestBody)).build();
    }

    /**
     * @apiDefine BadRequestError
     * @apiError (Error 4xx) {400} BadRequest Bad Request Encountered
     * */

    /** @apiDefine UnAuthorizedError
     * @apiError (Error 4xx) {401} UnAuthorized The Client must be authorized to access the resource
     * */

    /** @apiDefine ActivityNotFoundError
     * @apiError (Error 4xx) {404} NotFound Activity cannot be found
     * */

    /**
     * @apiDefine InternalServerError
     * @apiError (Error 5xx) {500} InternalServerError Something went wrong at server, Please contact the administrator!
     * */

    /**
     * @apiDefine NotImplementedError
     * @apiError (Error 5xx) {501} NotImplemented The resource has not been implemented. Please keep patience, our developers are working hard on it!!
     * */

    /**
     * @api {get} /activities?domain=domainName Get list of Activities for a given domain
     * @apiName getActivities
     * @apiGroup Activity
     *
     * @apiParam {String} domain Domain name for which activities are to be fetched. Use "_" in place of space character. Case sensitive.
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
    @GET
    @QueryParam("domain")
    public Response getActivities(@QueryParam("domain") String domain){
        List<Activity> activities = null;
        activities = reachService.getActivities(domain.replace("_", " "));
        if(activities == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Some problem in error. See logs.")
                    .build();

        return Response.status(Response.Status.OK)
                .entity(activities)
                .build();
    }

    /**
     * @api {post} /activities Create Activity
     * @apiName CreateActivity
     * @apiGroup Activity
     *
     * @apiParam {String} Title Title of the Activity
     * @apiParam {String} Description Description of the Activity
     *
     *
     * @apiParam (Login) {String} pass Only logged in user can get this
     *
     * @apiSuccess {String} text SUCCESS
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addActivity(@FormParam("title") String title, @FormParam("description") String description){

        String activity = reachService.addActivity(title, description);
        if (activity != null) {
            return Response.status(Response.Status.OK).entity("Successfully Created Activity!").build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something Went Wrong!!!").build();
    }
}
