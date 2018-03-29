package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.HEALResponse;
import edu.asu.heal.core.api.models.Patient;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/patients")
public class PatientResource {

    private HealService reachService =
            HealServiceFactory.getTheService();


    /** @apiDefine PatientNotFoundError
     * @apiError (Error 4xx) {404} NotFound The patient cannot be found
     * */

    /**
     * @apiDefine PatientsNotFoundError
     * @apiError (Error 4xx) {404} NotFound No patients exist!
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
     * @api {get} /patient Get Patients
     * @apiName GetPatients
     * @apiGroup Patient
     *
     * @apiParam {Number} [trialId] Pass trialId = 'some-unique-id' as query parameter to fetch the list of
     * patients for a particular trial; eg: `/patient?trialId=1`
     *
     * @apiSuccess {Object[]} data List of Patients
     * @apiSuccess {Object[]} data.activityInstances Patient's Activity Instances
     * @apiSuccess {DateTime} data.createdAt CreatedAt
     * @apiSuccess {Number} data.pin Patient's pin
     * @apiSuccess {DateTime} data.startDate Patient's Start Date of Trial
     * @apiSuccess {String} data.state Patient's Current State in the Trial
     * @apiSuccess {DateTime} data.updatedAt UpdatedAt
     * @apiSuccess {String} message Response Message
     * @apiSuccess {String} messageType Response Message Type
     * @apiSuccess {Number} statusCode  Response Status Code
     *
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "data": [
     *              {
     *                  "activityInstances": [
     *                      {
     *                          id: 87612kjbacskhv121
     *                      }
     *                  ],
     *                  "createdAt": "2018-02-26T07:00:00Z[UTC]",
     *                  "pin": 4010,
     *                  "startDate": "2018-02-26T07:00:00Z[UTC]",
     *                  "state": "Active",
     *                  "updatedAt": "2018-02-26T07:00:00Z[UTC]"
     *              }
     *          ],
     *          "message": "Success",
     *          "messageType": "success",
     *          "statusCode": 200
     *      }
     *
     * @apiUse PatientsNotFoundError
     * */
    @GET
    public Response fetchPatients(@QueryParam("trialId") String trialId){
        HEALResponse response = reachService.getPatients(trialId);

        return Response.status(response.getStatusCode()).entity(response).build();
    }

    /**
     * @api {get} /patient/:id Patient Detail
     * @apiName GetPatientDetail
     * @apiGroup Patient
     *
     * @apiParam {Number} id Patient's Unique Id
     *
     * @apiUse PatientNotFoundError
     * */
    @GET
    @Path("/{patientPin}")
    public Response fetchPatient(@PathParam("patientPin") int patientPin){
        Patient patient = reachService.getPatient(patientPin);
        if(patient == null)
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "Some problem with the server. Please contact administrator.")
                    .build();
        return Response.status(Response.Status.OK).entity(patient).build();
    }

    /**
     * @api {post} /patient Add Patient
     * @apiName AddPatient
     * @apiGroup Patient
     *
     *
     * @apiParam {String} Trial ID of the trial to which the patient needs to be added
     *
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     * */
    @POST
    public Response createPatient(String requestBody){
        int inserted = reachService.createPatient(requestBody);
        if(inserted != -1)
            return Response.status(Response.Status.CREATED).entity("{\"patient\": \"/patients/" + inserted +"\"}").build();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

}
