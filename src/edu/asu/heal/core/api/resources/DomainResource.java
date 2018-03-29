package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.Domain;
import edu.asu.heal.core.api.models.HEALResponse;
import edu.asu.heal.core.api.models.Trial;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;
import org.bson.types.ObjectId;

import javax.json.Json;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/domain")
@Produces(MediaType.APPLICATION_JSON)
public class DomainResource {

    private static HealService reachService =
            HealServiceFactory.getTheService();

    /**
     * @apiDefine DomainNotFoundError
     * @apiError (Error 4xx) {404} DomainNotFoundError Domain Not Found!
     * */

    /**
     * @apiDefine NotImplementedError
     * @apiError (Error 5xx) {501} NotImplemented The resource has not been implemented. Please keep patience, our developers are working hard on it!!
     * */

    /**
     * @api {get} /domain Domains
     * @apiName GetDomains
     * @apiGroup Domain
     *
     * @apiParam (Login) {String} pass Only logged in user can get this
     *
     * @apiSuccess {Object[]} domains List of Domains
     * @apiSuccess {Object} id  Domain Id
     * @apiSuccess {String} title Domain's Title
     * @apiSuccess {String} description Domain's Description
     * @apiSuccess {String} state Domain's current State
     *
     * @apiSuccess {Object[]} activities List of Domain activities
     * @apiSuccess {String} activities.title Activity's Title
     * @apiSuccess {String} activities.description Activity's Description
     *
     * @apiSuccess {Object[]} trials List of Domain trials
     * @apiSuccess {String} trials.title Trial's title
     * @apiSuccess {String} trials.description Trial's Description
     * @apiSuccess {String} trials.startDate Trial's startDate
     * @apiSuccess {String} trials.endDate Trial's endDate
     * @apiSuccess {Number} trials.targetCount Target count of patients
     *
     * @apiSuccess {Object[]} trials.patients Patient List of Trial
     * @apiSuccess {Number} trials.patients.pin De-Identified pin of patient
     * @apiSuccess {Number} trials.patients.startDate Start Date of patient
     * @apiSuccess {Number} trials.patients.endDate End Date of patient
     * @apiSuccess {String} trials.patients.state Current State of the patient
     *
     * @apiSuccess {Object[]} trials.patients.activityInstances ActivityInstances List of Patient
     * @apiSuccess {String} trials.patients.activityInstances.title ActivityInstance Title
     * @apiSuccess {String} trials.patients.activityInstances.startTime ActivityInstance Start Time
     * @apiSuccess {String} trials.patients.activityInstances.endTime ActivityInstance End Time
     * @apiSuccess {String} trials.patients.activityInstances.sequence ActivityInstance Sequence
     * @apiSuccess {String} trials.patients.activityInstances.status The status of the Activity Instance from Created | Available | In Execution (Running) | Suspended | Completed | Aborted
     *
     * @apiSuccess {Object} trials.patients.activityInstances.result Result for ActivityInstance of Patient
     *
     * @apiUse DomainNotFoundError
     * @apiUse InternalServerError
     * */
    @GET
    public Response fetchDomains(){
        HEALResponse response = reachService.getDomains();

        return Response.status(response.getStatusCode()).entity(response).build();
    }

    /**
     * @api {get} /domain/:id Domain Detail
     * @apiName DomainDetail
     * @apiGroup Domain
     *
     * @apiParam {Number} id Domain's Unique Id
     *
     * @apiParam (Login) {String} pass Only logged in user can get this
     *
     * @apiSuccess {Object} _id List of Domains
     * @apiSuccess {String} id.$oid  Domain Id
     * @apiSuccess {String} title Domain's Title
     * @apiSuccess {String} description Domain's Description
     * @apiSuccess {String} state Domain's current State
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
    @GET
    @Path("/{id}")
    public Response fetchDomain(@PathParam("id") String id){
        HEALResponse response = reachService.getDomain(id);

        return Response.status(response.getStatusCode()).entity(response).build();
    }


    /**
     * @api {post} /domain Create Domain
     * @apiName CreateDomain
     * @apiGroup Domain
     *
     * @apiParam {String} Title Title of the Domain
     * @apiParam {String} Description Description of the Domain
     * @apiParam {String} Status The status of the Domain from Active | InActive
     *
     * @apiParam (Login) {String} pass Only logged in user can get this
     *
     * @apiSuccess {String} text SUCCESS
     *
     * @apiUse DomainNotFoundError
     * @apiUse InternalServerError
     * */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addDomain(@FormParam("title") String title, @FormParam("description") String description,
                              @FormParam("state") String state){

        return Response.status(Response.Status.OK).entity(
                reachService.addDomain(title, description, state)
        ).build();
    }

}
