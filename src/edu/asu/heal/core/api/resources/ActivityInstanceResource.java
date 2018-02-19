package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/activityinstance/")
@Produces(MediaType.APPLICATION_JSON)
public class ActivityInstanceResource {

    private static HealService reachService =
            HealServiceFactory.getTheService("edu.asu.heal.reachv3.api.service.ReachService");

    /**
     * @apiDefine BadRequestError
     * @apiError (Error 4xx) {400} BadRequest Bad Request Encountered
     * */

    /** @apiDefine UnAuthorizedError
     * @apiError (Error 4xx) {401} UnAuthorized The Client must be authorized to access the resource
     * */

    /** @apiDefine ActivityInstanceNotFoundError
     * @apiError (Error 4xx) {404} NotFound ActivityInstance(s) cannot be found
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
     * @api {get} /activityInstance?patientPin={patientPin}&trialId={trialId} ActivityInstances
     * @apiName GetActivityInstances
     * @apiGroup ActivityInstance
     *
     * @apiParam {Number} patientPin Patient's Unique Id
     * @apiParam {Number} trialId Trial's Unique Id
     *
     * @apiParam (Login) {String} pass Only logged in user can get this
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse ActivityInstanceNotFoundError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
    @GET
    public Response fetchActivityInstances(@QueryParam("patientPin") String patientPin,
                                           @QueryParam("trialId") int trialId){

        return Response.status(Response.Status.OK).entity(
                reachService.getActivityInstances(patientPin, trialId)).build();
    }

    /**
     * @api {get} /activityInstance/:id ActivityInstance Detail
     * @apiName ActivityInstanceDetail
     * @apiGroup ActivityInstance
     *
     * @apiParam {Number} id ActivityInstance's Unique Id
     *
     * @apiParam (Login) {String} pass Only logged in user can get this
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse ActivityInstanceNotFoundError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
    @GET
    @Path("/{id}/")
    public Response fetchActivityInstance(@PathParam("id") String activityInstanceId){
        return Response.status(Response.Status.OK).entity(reachService.getActivityInstance(activityInstanceId)).build();
    }

    /**
     * @api {post} /activityInstance Create ActivityInstance
     * @apiName CreateActivityInstance
     * @apiGroup ActivityInstance
     *
     * @apiParam {DateTime} StartTime Start Time of the Activity Instance
     * @apiParam {DateTime} EndTime End Time of the Activity Instance
     * @apiParam {DateTime} UserSubmissionTime User Submission Time of the ActivityInstance
     * @apiParam {String} Status The status of the Activity Instance from Created | Available | In Execution (Running) | Suspended | Completed | Aborted
     * @apiParam {String} Sequence The sequence of the activities
     * @apiParam {String} ActivityTitle The title of the Activity Instance
     * @apiParam {String} Description Description about the Activity Instance
     *
     * @apiParam (Login) {String} pass Only logged in user can get this
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
    @POST
    public Response addActivityInstance(String requestBody){
        String response = reachService.createActivityInstance(requestBody);
        if(response != null) {

            return Response.status(Response.Status.CREATED).entity("Success").build();
        } else {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }

    /**
     * @api {put} activityInstance Update ActivityInstance
     * @apiName UpdateActivityInstance
     * @apiGroup ActivityInstance
     *
     * @apiParam {DateTime} StartTime Start Time of the Activity Instance
     * @apiParam {DateTime} EndTime End Time of the Activity Instance
     * @apiParam {DateTime} UserSubmissionTime User Submission Time of the ActivityInstance
     * @apiParam {String} Status The status of the Activity Instance from Created | Available | In Execution (Running) | Suspended | Completed | Aborted
     * @apiParam {String} Sequence The sequence of the activities
     * @apiParam {String} ActivityTitle The title of the Activity Instance
     * @apiParam {String} Description Description about the Activity Instance
     *
     * @apiParam (Login) {String} pass Only logged in user can get this
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
    @PUT
    public Response updateActivityInstance(String requestBody){
        return Response.status(Response.Status.OK).entity(reachService.updateActivityInstance(requestBody)).build();
    }

    /**
     * @api {delete} /activityInstance/:id Delete ActivityInstance
     * @apiName DeleteActivityInstance
     * @apiGroup ActivityInstance
     *
     * @apiParam {Number} id ActivityInstance's unique id
     *
     * @apiParam (Login) {String} pass Only logged in user can get this
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse ActivityInstanceNotFoundError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
    @DELETE
    @Path("/{id}")
    public Response removeActivityInstance(@PathParam("id") String activityInstanceId){
        return Response.status(Response.Status.OK).entity(
                reachService.deleteActivityInstance(activityInstanceId)).build();
    }

    @GET
    @Path("/makebelieve/")
    public Response fetchMakeBelieveInstance(){
        String makeBelieveInstanceString = reachService.getMakeBelieveInstance();
        if(makeBelieveInstanceString == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some server error. Please contact " +
                    "administrator").build();
        return Response.status(Response.Status.OK).entity(makeBelieveInstanceString).build();
    }

    @GET
    @Path("/makebelieveanswers/")
    public Response fetchMakeBelieveInstanceAnswers(@QueryParam("situation_id") int situationId){
        String makeBelieveInstanceAnswerString = reachService.getMakeBelieveInstanceAnswer(situationId);
        if(makeBelieveInstanceAnswerString == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some server error. Please contact " +
                    "administrator").build();
        if(makeBelieveInstanceAnswerString.equals("Bad Request"))
            return Response.status(400).build();
        return Response.status(Response.Status.OK).entity(makeBelieveInstanceAnswerString).build();
    }

    @PUT()
    @Path("/makebelieveinstance/")
    public Response updateMakeBelieveInstance(@QueryParam("situation_id") int situationId, String requestBody){
        int response = reachService.updateMakeBelieveInstance(situationId, requestBody);
        return Response.status(response).build();
    }
}