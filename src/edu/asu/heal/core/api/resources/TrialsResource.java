package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.HEALResponse;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/trials")
@Produces(MediaType.APPLICATION_JSON)
public class TrialsResource {
    private static HealService reachService =
            HealServiceFactory.getTheService();

    /**
     * @apiDefine BadRequestError
     * @apiError (Error 4xx) {400} BadRequest Bad Request Encountered
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
     * @api {get} /trials?domain={domainName} Get list of trials for a given domain
     * @apiName getTrials
     * @apiGroup Trials
     * @apiParam {String} domain Domain name for which trials are to be fetched. Use "_" in place of space character. Case sensitive.
     * @apiParamExample Request example: http://localhost:8080/ReachAPI/rest/trials?domain=Anxiety_Prevention
     * @apiUse BadRequestError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     */
    @GET
    @QueryParam("domain")
    public Response getTrials(@QueryParam("domain") String domain) {
        HEALResponse response;

        if (domain == null || domain.equals("")) {
            response = reachService.getTrials(null);
        } else {
            response = reachService.getTrials(domain.replace("_", " "));
        }

        return Response.status(response.getStatusCode())
                .entity(response)
                .build();

    }

    /**
     * @api {post} /trials Create Trial
     * @apiName CreateTrial
     * @apiGroup Trial
     * @apiParam {String} domainId DomainId for which the trial is being created
     * @apiParam {String} title Title of the Trial
     * @apiParam {String} description Description of the Trial
     * @apiParam {String} startDate Start Date for the Trial
     * @apiParam {String} endDate End Date for the Trial
     * @apiParam {Number} targetCount Target Count of the Trial
     * @apiParamExample {form-data} Request Example:
     * domainId="Preventive Anxiety
     * title="Compass"
     * description="Compass trial for Preventive Anxiety domain
     * startDate="2018-02-18 09:00:00"
     * endDate="2018-03-17 09:00:00"
     * targetCount=100
     * @apiSuccess {String} text SUCCESS
     * @apiUse BadRequestError
     * @apiUse InternalServerError
     * @apiuse TrialNotFoundError
     * @apiUse NotImplementedError
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrial(@FormParam("domainId") String domainId, @FormParam("title") String title,
                             @FormParam("description") String description, @FormParam("startDate") String startDate,
                             @FormParam("endDate") String endDate, @FormParam("targetCount") int targetCount) {

        HEALResponse response = reachService.addTrial(domainId, title, description, startDate, endDate, targetCount);

        return Response.status(response.getStatusCode()).entity(response).build();
    }
}
