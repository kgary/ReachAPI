package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/patients")
public class PatientResource {

    private HealService reachService = HealServiceFactory.getTheService("edu.asu.heal.reachv3.api.service.ReachService");

    /**
     * @apiDefine BadRequestError
     * @apiError (Error 4xx) {400} BadRequest Bad Request Encountered
     * */

    /** @apiDefine UnAuthorizedError
     * @apiError (Error 4xx) {401} UnAuthorized The Client must be authorized to access the resource
     * */

    /** @apiDefine PatientNotFoundError
     * @apiError (Error 4xx) {404} NotFound The patient cannot be found
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
     * @api {get} /patient?trialId={id} Get Patients of Trial
     * @apiName GetPatientsOfTrial
     * @apiGroup Patient
     *
     * @apiParam {Number} id Trial Unique Id
     * @apiParam (Login) {String} pass Only logged in user can get this
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse PatientNotFoundError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
//    @GET
//    public Response fetchPatients(@QueryParam("trialId") String trialId){
//        String patients = reachService.getPatients(trialId);
//        if(patients == null)
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//                    .entity("Some problem on server. Check logs")
//                    .build();
//
//        return Response.status(Response.Status.OK)
//                .entity(patients)
//                .build();
//    }

    /**
     * @api {get} /patient/:id Patient Detail
     * @apiName GetPatientDetail
     * @apiGroup Patient
     *
     * @apiParam {Number} id Patient's Unique Id
     *
     * @apiParam (Login) {String} pass Only logged in user can get this
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse PatientNotFoundError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
    * */
    @GET
    @Path("/{patientPin}")
    public Response fetchPatient(@PathParam("patientPin") String patientPin){
        return Response.status(Response.Status.OK).entity(reachService.getPatient(patientPin)).build();
    }

    /**
     * @api {post} /patient Add Patient
     * @apiName AddPatient
     * @apiGroup Patient
     *
     * @apiParam {String} DeviceType Type of Device used by the patient
     * @apiParam {String} [DeviceVersion] Version of the Device
     * @apiParam {String} DateStarted DateTime at which the patient has started the participation
     * @apiParam {String} DateCompleted DateTime at which the patient is expected to complete the trial
     * @apiParam {String} Type Patient Type: Child | Adult | Parent Proxy
     * @apiParam {Number} StageId Unique Trial's Stage Id
     * @apiParam {Number} ParentPinId Patient's Unique Id
     *
     * @apiParam (Login) {String} pass Only logged in user can post this
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
    @POST
    public Response addPatient(String requestBody){
        return Response.status(Response.Status.OK).entity(reachService.createPatient(requestBody)).build();
    }

    /**
     * @api {put} /patient Update Patient
     * @apiName UpdatePatient
     * @apiGroup Patient
     *
     * @apiParam {String} DeviceType Type of Device used by the patient
     * @apiParam {String} [DeviceVersion] Version of the Device
     * @apiParam {String} DateStarted DateTime at which the patient has started the participation
     * @apiParam {String} DateCompleted DateTime at which the patient is expected to complete the trial
     * @apiParam {String} Type Patient Type: Child | Adult | Parent Proxy
     * @apiParam {Number} StageId Unique Trial's Stage Id
     * @apiParam {Number} ParentPinId Patient's Unique Id
     *
     * @apiParam (Login) {String} pass Only logged in user can put this
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
    @PUT
    public Response updatePatient(String requestBody){
        return Response.status(Response.Status.OK).entity(reachService.updatePatient(requestBody)).build();
    }

    /**
     * @api {delete} /patient/:id Delete Patient
     * @apiName RemovePatient
     * @apiGroup Patient
     *
     * @apiParam {Number} patientId Unique Patient's Id
     * @apiParam (Login) {String} pass Only logged in user can delete this
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse PatientNotFoundError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
    @DELETE
    @Path("/{patientPin}")
    public Response removePatient(@PathParam("patientPin") String patientPin){
        return Response.status(Response.Status.OK).entity(reachService.deletePatient(patientPin)).build();
    }

}
