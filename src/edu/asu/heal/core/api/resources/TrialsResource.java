package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.Trial;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("/trials")
@Produces(MediaType.APPLICATION_JSON)
public class TrialsResource {
    private static HealService reachService =
            HealServiceFactory.getTheService();

    /**
     * @apiDefine BadRequestError
     * @apiError (Error 4xx) {400} BadRequest Bad Request Encountered
     * */

    /** @apiDefine UnAuthorizedError
     * @apiError (Error 4xx) {401} UnAuthorized The Client must be authorized to access the resource
     * */

    /** @apiDefine TrialNotFoundError
     * @apiError (Error 4xx) {404} NotFound Trial cannot be found
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
     * @api {get} /trials?domain=domainName Get list of trials for a given domain
     * @apiName getTrials
     * @apiGroup Trials
     *
     * @apiParam {String} domain Domain name for which trials are to be fetched. Use "_" in place of space character. Case sensitive.
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
    @GET
    @QueryParam("domain")
    public Response getTrials(@QueryParam("domain") String domain){
        List<Trial> trials = reachService.getTrials(domain.replace("_", " "));
        if(trials == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Some problem on server. See logs.")
                    .build();

        return Response.status(Response.Status.OK)
                .entity(trials)
                .build();
    }

    /**
     * @api {post} /trails Create Trial
     * @apiName CreateTrial
     * @apiGroup Trial
     *
     * @apiParam {String} domainId DomainId for which the trial is being created
     * @apiParam {String} Title Title of the Trial
     * @apiParam {String} Description Description of the Trial
     * @apiParam {String} startDate Start Date for the Trial
     * @apiParam {String} endDate End Date for the Trial
     * @apiParam {Number} targetCount Target Count of the Trial
     *
     *
     * @apiParam (Login) {String} pass Only logged in user can get this
     *
     * @apiSuccess {String} text SUCCESS
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse InternalServerError
     * @apiuse TrialNotFoundError
     * @apiUse NotImplementedError
     * */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrial(@FormParam("domainId") String domainId, @FormParam("title") String title,
                             @FormParam("description") String description, @FormParam("startDate") String startDate,
                             @FormParam("endDate") String endDate, @FormParam("targetCount") int targetCount){

        String trial = reachService.addTrial(domainId, title, description, startDate, endDate, targetCount);

        if (trial != null) {
            return Response.status(Response.Status.OK).entity("Successfully Created").build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Trial Could not be created").build();
    }
}
