package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/activityinstances/")
@Produces(MediaType.APPLICATION_JSON)
public class ActivityInstanceResource {

	// XXX the classname should not be hardcoded
    private static HealService reachService =
            HealServiceFactory.getTheService("edu.asu.heal.reachv3.api.service.ReachService");

    // XXX does apidoc let you put the definitions after references, like at the bottom of the class?
    // are these definitions repeated in every resource class? Seems like there would be a shortcut,
    // and they are in the way here. Finally, the apidoc needs to give JSON examples.
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
    public Response fetchActivityInstances(@QueryParam("patientPin") int patientPin,
                                           @QueryParam("trialId") int trialId){
    	// XXX are the query string params required? I would think there would be a more general
    	// set of query string parameters that could cut across these. Candidate for shortcut endpoint
        List<ActivityInstance> instances = reachService.getActivityInstances(patientPin, trialId);
        if(instances == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("SOME PROBLEM IN SERVER. CHECK LOGS")  // XXX add more info, you have patientPin and trialId
            .build();

        return Response.status(Response.Status.OK)
                .entity(instances)  // XXX need to talk JSON payload
                .build();
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
    @Path("/{id}/")  // XXX why the / at the end?
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
    public Response addActivityInstance(String requestBody){  // XXX I prefer "create" over "add"
        String response = reachService.createActivityInstance(requestBody);
        // XXX why are we using String and not an @Consumes of JSON?
        if(response != null) {
        		// XXX we should add the Location header here, or at the least decide if we are going to link in the JSON
        		// But the new resource link should be in the response somewhere
        		// XXX We HAVE to get away from wrapped responses like "Success" and "Error". Return the resource representation
            return Response.status(Response.Status.CREATED).entity("Success").build();
        } else {
        		// XXX can we improve our error handling? Why would the service return null? Can it tell us anything?
        		// for example, what if the requestBody makes no sense? Shouldn't that be a 400?
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
    public Response updateActivityInstance(String requestBody){ // XXX see comment above regarding use of String
    		// XXX No error cases possible on the call to the service? You list 4 above
    		// XXX we return OK but we should distinguish an update from a created on PUT (200 vs 201)
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
        // XXX proper response code is 204. Again, no error handling?
    }

    // XXX Why not use a query param like type=makebelieve instead of making a new endpoint?
    // when we extend our endpoint we think of it as a path param usually, whereas this is a subtype
    @GET
    @Path("/makebelieve/")
    public Response fetchMakeBelieveInstance(){
        String makeBelieveInstanceString = reachService.getMakeBelieveInstance();
        if(makeBelieveInstanceString == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some server error. Please contact " +
                    "administrator").build();
        return Response.status(Response.Status.OK).entity(makeBelieveInstanceString).build();
    }

    // XXX I am pretty confused why we need a new endpoint at all. Why can't the service distinguish the special case
    // of a MB AI?
    @GET
    @Path("/makebelieveanswers/")  // XXX this would be /makebelieve/answers?situation_id=...
    public Response fetchMakeBelieveInstanceAnswers(@QueryParam("situation_id") int situationId){
        String makeBelieveInstanceAnswerString = reachService.getMakeBelieveInstanceAnswer(situationId);
        if(makeBelieveInstanceAnswerString == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some server error. Please contact " +
                    "administrator").build();
        if(makeBelieveInstanceAnswerString.equals("Bad Request"))
            return Response.status(400).build();
        return Response.status(Response.Status.OK).entity(makeBelieveInstanceAnswerString).build();
    }

    // XXX if we do keep a /makebelieve endpoint then you PUT on that, not on a separate one here
    @PUT()
    @Path("/makebelieveinstance/")
    public Response updateMakeBelieveInstance(@QueryParam("situation_id") int situationId, String requestBody){
        int response = reachService.updateMakeBelieveInstance(situationId, requestBody);
        return Response.status(response).build();
    }

    // XXX again why a new endpoint? WorryHeads is just an acivityinstance from the API perspective. Yes, we
    // will need to route to the right service call, but we do not have to expose a new endpoint. Rather,
    // the subtype we are looking for (WH, MB, etc.) is a query filter
    @GET
    @Path("/worryheads")
    public Response fetchWorryHeadsInstance(){
    		// XXX what WH instance would this even return? A single or a collection? How would it be scoped?
        String worryHeadsInstanceString = reachService.getWorryHeadsInstance();
        if(worryHeadsInstanceString == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some server error. Please contact "
            + "administrator").build();

        if(worryHeadsInstanceString.equals("Bad Request"))
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.status(Response.Status.OK).entity(worryHeadsInstanceString).build();
    }
}
