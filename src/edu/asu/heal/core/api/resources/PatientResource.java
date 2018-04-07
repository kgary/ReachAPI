package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.HEALResponse;
import edu.asu.heal.core.api.models.NullObjects;
import edu.asu.heal.core.api.models.Patient;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/patients")
public class PatientResource {
    @Context
    private UriInfo _uri;

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
     * @apiParam {Number} [trialId] Pass trialId = 'some-unique-id' as query parameter to fetch the list of
     * patients for a particular trial; eg: `/patient?trialId=1`
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
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "data": [
     * {
     * "activityInstances": [
     * {
     * id: 87612kjbacskhv121
     * }
     * ],
     * "createdAt": "2018-02-26T07:00:00Z[UTC]",
     * "pin": 4010,
     * "startDate": "2018-02-26T07:00:00Z[UTC]",
     * "state": "Active",
     * "updatedAt": "2018-02-26T07:00:00Z[UTC]"
     * }
     * ],
     * "message": "Success",
     * "messageType": "success",
     * "statusCode": 200
     * }
     * @apiUse PatientsNotFoundError
     */
    @GET
    public Response fetchPatients(@QueryParam("trialId") String trialId) {
        HEALResponse response = null;
        HEALResponse.HEALResponseBuilder builder = new HEALResponse.HEALResponseBuilder();

        List<Patient> patients = reachService.getPatients(trialId);

        if (patients == null) {
            response = builder
                    .setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
                    .build();
        } else if (patients.isEmpty()) {
            response = builder
                    .setStatusCode(Response.Status.OK.getStatusCode())
                    .setData("NO PATIENTS FOUND")
                    .build();
        } else if (patients.size() == 1) {
            if (patients.get(0).equals(NullObjects.getNullPatient())) {
                response = builder
                        .setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
                        .setData("THE TRIAL ID YOU'VE PASSED IN IS INCORRECT OR DOES NOT EXIST")
                        .build();
            } else {
                response = builder
                        .setStatusCode(Response.Status.OK.getStatusCode())
                        .setData(patients)
                        .build();
            }
        } else {
            response = builder
                    .setStatusCode(Response.Status.OK.getStatusCode())
                    .setData(patients)
                    .build();
        }

        return Response.status(response.getStatusCode()).entity(response).build();
    }

    /**
     * @api {get} /patient/:id Patient Detail
     * @apiName GetPatientDetail
     * @apiGroup Patient
     * @apiParam {Number} id Patient's Unique Id
     * @apiUse PatientNotFoundError
     */
    @GET
    @Path("/{patientPin}")
    public Response fetchPatient(@PathParam("patientPin") int patientPin) {
        HEALResponse response = null;
        HEALResponse.HEALResponseBuilder builder = new HEALResponse.HEALResponseBuilder();

        Patient patient = reachService.getPatient(patientPin);
        if(patient == null){
            response = builder
                    .setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
                    .build();
        }else if(patient.equals(NullObjects.getNullPatient())){
            response = builder
                    .setStatusCode(Response.Status.NOT_FOUND.getStatusCode())
                    .setData("THE PATIENT YOU'RE REQUESTING DOES NOT EXIST")
                    .build();
        }else{
            response = builder
                    .setStatusCode(Response.Status.OK.getStatusCode())
                    .setData(patient)
                    .build();
        }

        return Response.status(response.getStatusCode()).entity(response).build();
    }

    /**
     * @api {post} /patient Add Patient
     * @apiName AddPatient
     * @apiGroup Patient
     * @apiParam {String} Trial ID of the trial to which the patient needs to be added
     * @apiUse BadRequestError
     * @apiUse UnAuthorizedError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     */
    @POST
    public Response createPatient() {
        HEALResponse response = null;
        HEALResponse.HEALResponseBuilder builder = new HEALResponse.HEALResponseBuilder();

        Patient insertedPatient = reachService.createPatient();

        if(insertedPatient == null){
            response = builder
                    .setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .setData("SOME ERROR CREATING NEW ACTIVITY INSTANCE. CONTACT ADMINISTRATOR")
                    .build();
        }else if(insertedPatient.equals(NullObjects.getNullPatient())){
            response = builder
                    .setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
                    .setData("INCORRECT TRIAL ID IN THE REQUEST")
                    .build();
        }else{
            response = builder
                    .setStatusCode(Response.Status.CREATED.getStatusCode())
                    .setData(String.format("%s/%s",_uri.getAbsolutePath().toString(),
                            insertedPatient.getPin()))
                    .build();
        }

        return Response.status(response.getStatusCode()).entity(response).build();
    }

    // TODO: PUT PENDING

}
