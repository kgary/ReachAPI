package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/trials")
@Produces(MediaType.APPLICATION_JSON)
public class TrialsResource {
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
     * @api {get} /trials/{domain} Get list of trials for a given domain
     * @apiName getTrials
     * @apiGroup Trials
     *
     * @apiParam {String} domain Domain name for which trials are to be fetched
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
    @GET
    @Path("/{domain}")
    public Response getTrials(@PathParam("domain") String domain){
        String trials = reachService.getTrials(domain);
        if(trials == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Some problem on server. See logs.")
                    .build();

        return Response.status(Response.Status.OK)
                .entity(trials)
                .build();
    }

    // TODO -- add api doc entry
    @POST
    @Path("/{domain}/trial")
    public Response addTrial(@PathParam("domain") String domain){
        // TODO

        return Response.status(Response.Status.NOT_IMPLEMENTED).entity("Not Implemented").build();
    }
}
