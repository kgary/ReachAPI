package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.Domain;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.json.Json;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/domain")
@Produces(MediaType.APPLICATION_JSON)
public class DomainResource {

    private static HealService reachService =
            HealServiceFactory.getTheService("edu.asu.heal.reachv3.api.service.ReachService");

    @GET
    public Response fetchDomains(){
        return Response.status(Response.Status.NOT_IMPLEMENTED).entity(
                reachService.getDomains()
        ).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addDomain(@FormParam("title") String title, @FormParam("description") String description,
                                    @FormParam("state") String state){

        return Response.status(Response.Status.OK).entity(
                reachService.addDomain(title, description, state)
        ).build();
    }

    @Path("/provision")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addTestDomain(@FormParam("title") String title, @FormParam("description") String description,
                                   @FormParam("state") String state){

        return Response.status(Response.Status.OK).entity(
                reachService.addTestDomain(title, description, state)
        ).build();
    }

}
