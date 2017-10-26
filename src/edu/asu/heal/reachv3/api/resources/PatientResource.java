package edu.asu.heal.reachv3.api.resources;

import edu.asu.heal.core.api.contracts.IHealContract;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/patient")
public class PatientResource {

    private IHealContract reachService = HealServiceFactory.getTheService("edu.asu.heal.reachv3.api.service.ReachService");

    @GET
    public Response fetchPatients(@QueryParam("trialId") int trialId){
        return Response.status(Response.Status.OK).entity(reachService.getPatients(trialId)).build();
    }

    @GET
    @Path("/{patientPin}")
    public Response fetchPatient(@PathParam("patientPin") String patientPin){
        return Response.status(Response.Status.OK).entity(reachService.getPatient(patientPin)).build();
    }

    @POST
    public Response addPatient(String requestBody){
        return Response.status(Response.Status.OK).entity(reachService.createPatient(requestBody)).build();
    }

    @PUT
    public Response updatePatient(String requestBody){
        return Response.status(Response.Status.OK).entity(reachService.updatePatient(requestBody)).build();
    }

    @DELETE
    @Path("/{patientPin}")
    public Response removePatient(@PathParam("patientPin") String patientPin){
        return Response.status(Response.Status.OK).entity(reachService.deletePatient(patientPin)).build();
    }

}
