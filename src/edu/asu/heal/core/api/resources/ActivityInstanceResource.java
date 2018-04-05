package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.HEALResponse;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/activityinstances/")
@Produces(MediaType.APPLICATION_JSON)
public class ActivityInstanceResource {

    @Context
    private UriInfo _uri;

    private static HealService reachService = HealServiceFactory.getTheService();

    /**
     * @apiDefine BadRequestError
     * @apiError (Error 4xx) {400} BadRequest Bad Request Encountered
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
     * @apiParam {Number} patientPin Patient's Unique Id
     * @apiParam {Number} trialId Trial's Unique Id
     * @apiParam (Login) {String} pass Only logged in user can get this
     * @apiUse BadRequestError
     * @apiUse ActivityInstanceNotFoundError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     */
    @GET
    public Response fetchActivityInstances(@QueryParam("patientPin") int patientPin,
                                           @QueryParam("trialId") int trialId) {
        // XXX are the query string params required? I would think there would be a more general
        // set of query string parameters that could cut across these. Candidate for shortcut endpoint
        HEALResponse response = null;
        HEALResponse.HEALResponseBuilder builder = new HEALResponse.HEALResponseBuilder();
        if (patientPin == 0 || patientPin < -1) {
            response = builder
                    .setData(null)
                    .setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
                    .setMessage("YOUR PATIENT PIN ABSENT FROM THE REQUEST")
                    .setMessageType(HEALResponse.ERROR_MESSAGE_TYPE)
                    .build();
        } else {
            List<ActivityInstance> instances = reachService.getActivityInstances(patientPin, trialId);
            response = builder
                    .setData(instances)
                    .setStatusCode(Response.Status.OK.getStatusCode())
                    .setMessage("SUCCESS")
                    .setMessageType(HEALResponse.SUCCESS_MESSAGE_TYPE)
                    .build();
        }

        return Response.status(response.getStatusCode())
                .entity(response)
                .build();
    }

    /**
     * @api {get} /activityInstance/:id ActivityInstance Detail
     * @apiName ActivityInstanceDetail
     * @apiGroup ActivityInstance
     * @apiParam {Number} id ActivityInstance's Unique Id
     * @apiParamExample http://localhost:8080/ReachAPI/rest/activityinstances/5abd71b82e027e29ca2353a0
     * @apiUse BadRequestError
     * @apiUse ActivityInstanceNotFoundError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     */
    @GET
    @Path("/{id}")
    public Response fetchActivityInstance(@PathParam("id") String activityInstanceId) {
        HEALResponse response = null;
        HEALResponse.HEALResponseBuilder builder = new HEALResponse.HEALResponseBuilder();
        ActivityInstance instance = reachService.getActivityInstance(activityInstanceId);

        response = builder
                .setData(instance)
                .setStatusCode(Response.Status.OK.getStatusCode())
                .setMessage("SUCCESS")
                .setMessageType(HEALResponse.SUCCESS_MESSAGE_TYPE)
                .build();

        return Response.status(response.getStatusCode()).entity(response).build();
    }

    /**
     * @api {post} /activityInstance Create ActivityInstance
     * @apiName CreateActivityInstance
     * @apiGroup ActivityInstance
     * @apiParam {DateTime} StartTime Start Time of the Activity Instance
     * @apiParam {DateTime} EndTime End Time of the Activity Instance
     * @apiParam {DateTime} UserSubmissionTime User Submission Time of the ActivityInstance
     * @apiParam {String} Status The status of the Activity Instance from Created | Available | In Execution (Running) | Suspended | Completed | Aborted
     * @apiParam {String} Sequence The sequence of the activities
     * @apiParam {String} ActivityTitle The title of the Activity Instance
     * @apiParam {String} Description Description about the Activity Instance
     * @apiParam (Login) {String} pass Only logged in user can get this
     * @apiParamExample {json} Activity Instance Example:
     * {
     *      "createdAt": "2018-02-26T07:00:00.000Z",
     *      "updatedAt": "2018-02-26T07:00:00.000Z",
     *      "startTime": "2018-02-26T07:00:00.000Z",
     *      "endTime": "2018-02-27T07:00:00.000Z",
     *      "userSubmissionTime": "2000-01-01T07:00:00.000Z",
     *      "actualSubmissionTime": "2000-01-01T07:00:00.000Z",
     *      "instanceOf": {
     *      "name": "Relaxation"
     *      "activityId": 5a9499e066684905df626003
     *          },
     *      "state": "New",
     *      "description": "Relaxation instance"
     * }
     * @apiUse BadRequestError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createActivityInstance(ActivityInstance activityInstanceJson) {
        ActivityInstance instance = reachService.createActivityInstance(activityInstanceJson);
        HEALResponse response = null;
        HEALResponse.HEALResponseBuilder builder = new HEALResponse.HEALResponseBuilder();

        response = builder
                .setData(instance)
                .setStatusCode(Response.Status.CREATED.getStatusCode())
                .setMessage("SUCCESS")
                .setMessageType(HEALResponse.SUCCESS_MESSAGE_TYPE)
                .build();

//        if (instance.getStatusCode() == Response.Status.CREATED.getStatusCode()) {
//            return Response
//                    .status(Response.Status.CREATED)
//                    .header("Location",
//                            String.format("%s/%s",_uri.getAbsolutePath().toString(),
//                                    activityInstanceJson.getActivityInstanceId()))
//                    .entity(instance)
//                    .build();
//        } else {
//            // XXX can we improve our error handling? Why would the service return null? Can it tell us anything?
//            // for example, what if the requestBody makes no sense? Shouldn't that be a 400?
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
//        }
        return Response.status(response.getStatusCode()).header("Location",
                            String.format("%s/%s",_uri.getAbsolutePath().toString(),
                                    instance.getActivityInstanceId())).entity(response).build();
    }

//    /**
//     * @api {put} activityInstance Update ActivityInstance
//     * @apiName UpdateActivityInstance
//     * @apiGroup ActivityInstance
//     * @apiParam {DateTime} StartTime Start Time of the Activity Instance
//     * @apiParam {DateTime} EndTime End Time of the Activity Instance
//     * @apiParam {DateTime} UserSubmissionTime User Submission Time of the ActivityInstance
//     * @apiParam {String} Status The status of the Activity Instance from Created | Available | In Execution (Running) | Suspended | Completed | Aborted
//     * @apiParam {String} Sequence The sequence of the activities
//     * @apiParam {String} ActivityTitle The title of the Activity Instance
//     * @apiParam {String} Description Description about the Activity Instance
//     * @apiParam (Login) {String} pass Only logged in user can get this
//     * @apiUse BadRequestError
//     * @apiUse InternalServerError
//     * @apiUse NotImplementedError
//     */
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response updateActivityInstance(String requestBody) {
//        // XXX No error cases possible on the call to the service? You list 4 above
//        // XXX we return OK but we should distinguish an update from a created on PUT (200 vs 201)
//        return Response.status(Response.Status.NO_CONTENT).entity(reachService.updateActivityInstance(requestBody)).build();
//    }

    /**
     * @api {delete} /activityInstance/:id Delete ActivityInstance
     * @apiName DeleteActivityInstance
     * @apiGroup ActivityInstance
     * @apiParam {String} id ActivityInstance's unique id
     * @apiParamExample http://localhost:8080/ReachAPI/rest/activityinstances/5abd71b82e027e29ca2353a0
     * @apiUse BadRequestError
     * @apiUse ActivityInstanceNotFoundError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     */
    @DELETE
    @Path("/{id}")
    public Response removeActivityInstance(@PathParam("id") String activityInstanceId) {
        boolean removed = reachService.deleteActivityInstance(activityInstanceId);

        HEALResponse response = null;
        HEALResponse.HEALResponseBuilder builder = new HEALResponse.HEALResponseBuilder();

        response = builder
                .setData(null)
                .setStatusCode(Response.Status.NO_CONTENT.getStatusCode())
                .setMessage("SUCCESS")
                .setMessageType(HEALResponse.SUCCESS_MESSAGE_TYPE)
                .build();

        return Response.status(response.getStatusCode()).build();

    }

    // XXX Why not use a query param like type=makebelieve instead of making a new endpoint?
    // when we extend our endpoint we think of it as a path param usually, whereas this is a subtype
    @GET
    @Path("/makebelieve/")
    public Response fetchMakeBelieveInstance() {
        String makeBelieveInstanceString = reachService.getMakeBelieveInstance();
        if (makeBelieveInstanceString == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some server error. Please contact " +
                    "administrator").build();
        return Response.status(Response.Status.OK).entity(makeBelieveInstanceString).build();
    }

    // XXX I am pretty confused why we need a new endpoint at all. Why can't the service distinguish the special case
    // of a MB AI?
    @GET
    @Path("/makebelieveanswers/")  // XXX this would be /makebelieve/answers?situation_id=...
    public Response fetchMakeBelieveInstanceAnswers(@QueryParam("situation_id") int situationId) {
        String makeBelieveInstanceAnswerString = reachService.getMakeBelieveInstanceAnswer(situationId);
        if (makeBelieveInstanceAnswerString == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some server error. Please contact " +
                    "administrator").build();
        if (makeBelieveInstanceAnswerString.equals("Bad Request"))
            return Response.status(400).build();
        return Response.status(Response.Status.OK).entity(makeBelieveInstanceAnswerString).build();
    }

    // XXX if we do keep a /makebelieve endpoint then you PUT on that, not on a separate one here
    @PUT
    @Path("/makebelieveinstance/")
    public Response updateMakeBelieveInstance(@QueryParam("situation_id") int situationId, String requestBody) {
        int response = reachService.updateMakeBelieveInstance(situationId, requestBody);
        return Response.status(response).build();
    }

    // XXX again why a new endpoint? WorryHeads is just an acivityinstance from the API perspective. Yes, we
    // will need to route to the right service call, but we do not have to expose a new endpoint. Rather,
    // the subtype we are looking for (WH, MB, etc.) is a query filter
    @GET
    @Path("/worryheads")
    public Response fetchWorryHeadsInstance() {
        // XXX what WH instance would this even return? A single or a collection? How would it be scoped?
        String worryHeadsInstanceString = reachService.getWorryHeadsInstance();
        if (worryHeadsInstanceString == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some server error. Please contact "
                    + "administrator").build();

        if (worryHeadsInstanceString.equals("Bad Request"))
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.status(Response.Status.OK).entity(worryHeadsInstanceString).build();
    }
}
