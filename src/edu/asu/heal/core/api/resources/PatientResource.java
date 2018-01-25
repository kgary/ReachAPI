package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/patient")
public class PatientResource {

    private HealService reachService = HealServiceFactory.getTheService("edu.asu.heal.reachv3.api.service.ReachService");

    /**
     * @apiDefine PatientNotFoundError
     * @apiError (Error 4xx) {404} PatientNotFoundError The <code>id</code> was not found
     * */

    /**
     * @api {get} /patient?trialId={id} Get Patients of Trial
     * @apiName GetPatientsOfTrial
     * @apiGroup Patient
     *
     * @apiParam {Number} id Trial Unique Id
     * @apiParam (Login) {String} pass Only logged in user can get this
     *
     * @apiUse PatientNotFoundError
     * */
    @GET
    public Response fetchPatients(@QueryParam("trialId") int trialId){
        return Response.status(Response.Status.OK).entity(reachService.getPatients(trialId)).build();
    }

    /**
     * @api {get} /patient/:patientId Patient Detail
     * @apiName GetPatientDetail
     * @apiGroup Patient
     *
     * @apiParam {Number} patientId Patient's Unique Id
     * @apiParam (Login) {String} pass Only logged in user can get this
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
     * */
    @PUT
    public Response updatePatient(String requestBody){
        return Response.status(Response.Status.OK).entity(reachService.updatePatient(requestBody)).build();
    }

    /**
     * @api {delete} /patient/:patientId Delete Patient
     * @apiName RemovePatient
     * @apiGroup Patient
     *
     * @apiParam {Number} patientId Unique Patient's Id
     * @apiParam (Login) {String} pass Only logged in user can delete this
     * */
    @DELETE
    @Path("/{patientPin}")
    public Response removePatient(@PathParam("patientPin") String patientPin){
        return Response.status(Response.Status.OK).entity(reachService.deletePatient(patientPin)).build();
    }

}
