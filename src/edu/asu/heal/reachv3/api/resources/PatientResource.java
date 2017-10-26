package edu.asu.heal.reachv3.api.resources;

import edu.asu.heal.core.api.contracts.IHealContract;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/patient")
public class PatientResource {

    private IHealContract reachService = HealServiceFactory.getTheService("edu.asu.heal.reachv3.api.service.ReachService");

    @GET
    public Response fetchPatients(){
        return Response.status(Response.Status.OK).entity(reachService.getPatients()).build();
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

    /*@GET
    @Path("/{patientPin}/activities")
    public Response fetchPatientActivities(@PathParam("patientPin") String patientPin){
        return Response.status(Response.Status.OK).entity(reachService.getPatientActivities(patientPin)).build();
    }

    @POST
    @Path("/{patientPin}/activities/schedule")
    public Response schedulePatientActivities(@PathParam("patientPin") String patientPin, String requestBody){
        return Response.status(Response.Status.OK).entity(reachService.schedulePatientActivities(patientPin, requestBody)).build();
    }*/


}
