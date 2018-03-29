package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.HEALResponse;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/activities")
@Produces(MediaType.APPLICATION_JSON)
public class ActivityResource {

    private static HealService reachService =
            HealServiceFactory.getTheService();

    /** @apiDefine ActivityNotFoundError
     * @apiError (Error 4xx) {404} NotFound Activity cannot be found
     * */

    /**
     * @apiDefine InternalServerError
     * @apiError (Error 5xx) {500} InternalServerError Something went wrong at server, Please contact the administrator!
     * */

    /**
     * @api {get} /activities?domain=domainName Get list of Activities for a given domain
     * @apiName GetActivities
     * @apiGroup Activity
     * @apiParam {String} domain Domain name for which activities are to be fetched. Use "_" in place of space
     * character. Case sensitive.
     * @apiUse ActivityNotFoundError
     * @apiUse InternalServerError
     */
    @GET
    @QueryParam("domain")
    public Response getActivities(@QueryParam("domain") String domain) {
        HEALResponse response = reachService.getActivities(domain);

        return Response.status(response.getStatusCode()).entity(response).build();
    }

    /**
     * @api {post} /activities Create Activity
     * @apiName CreateActivity
     * @apiGroup Activity
     * @apiParam {String} Title Title of the Activity
     * @apiParam {String} Description Description of the Activity
     * @apiParam (Login) {String} pass Only logged in user can get this
     * @apiSuccess {Object[]} data null
     * @apiSuccess {String} message Response Message
     * @apiSuccess {String} messageType Response Message Type
     * @apiSuccess {Number} statusCode  Response Status Code
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 201 CREATED
     * {
     * "data": null,
     * "message": "Created",
     * "messageType": "success",
     * "statusCode": 201
     * }
     * @apiUse InternalServerError
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createActivity(@FormParam("title") String title, @FormParam("description") String description) {
        HEALResponse response = reachService.createActivity(title, description);

        return Response.status(response.getStatusCode()).entity(response).build();
    }
}
