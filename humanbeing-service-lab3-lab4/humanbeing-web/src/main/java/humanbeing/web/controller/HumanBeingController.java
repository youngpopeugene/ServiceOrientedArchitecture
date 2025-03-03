package humanbeing.web.controller;

import humanbeing.ejb.dto.*;
import humanbeing.ejb.model.*;
import humanbeing.ejb.service.*;
import humanbeing.web.util.Converter;
import humanbeing.web.util.Validator;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/humanbeings")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class HumanBeingController {

    @EJB
    HumanBeingServiceRemote service;

    @EJB
    TeamServiceRemote teamService;

    @Inject
    private Converter converter;

    @Inject
    private Validator validator;


    @POST
    public Response createHumanBeing(HumanBeingDTO dto) {
        validator.ValidateHumanBeing(dto);
        if (dto.getTeamID() != null) {
            teamService.getTeamById(dto.getTeamID());
        }
        return Response.status(Response.Status.CREATED).entity(service.createHumanBeing(converter.ToHumanBeingFromDTO(dto))).build();
    }

    @GET
    public Response getHumanBeings(
            @QueryParam("limit") String limit,
            @QueryParam("offset") String offset,
            @QueryParam("sort") List<String> sort,
            @QueryParam("filter") List<String> filter) {
        int numericOffset = validator.ValidateHumanBeingSearch(limit, offset, sort, filter);
        if (numericOffset != 0) numericOffset = Integer.parseInt(offset);
        PageHumanBeing pageResult = service.getHumanBeings(Integer.parseInt(limit), numericOffset, sort, filter);
        return Response.ok(pageResult).build();
    }

    @GET
    @Path("/{id}")
    public Response getHumanBeingById(@PathParam("id") String id) {
        return Response.ok().entity(service.getHumanBeingById(validator.ValidateID(id))).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteHumanBeing(@PathParam("id") String id) {
        service.deleteHumanBeing(validator.ValidateID(id));
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateHumanBeing(@PathParam("id") String id, HumanBeingDTO dto) {
        validator.ValidateHumanBeing(dto);
        HumanBeing humanBeing = converter.ToHumanBeingFromDTO(dto);
        humanBeing.setId(validator.ValidateID(id));
        if (dto.getTeamID() != null) {
            teamService.getTeamById(dto.getTeamID());
        }
        return Response.ok(service.updateHumanBeing(humanBeing)).build();
    }

    @GET
    @Path("/sum/impact")
    public Response getImpactSpeedSum() {
        double sum = service.calculateImpactSpeedSum();
        return Response.ok("<result>" + sum + "</result>").build();
    }

    @GET
    @Path("/amount/soundtrack/less")
    public Response getHumanBeingsCountBySoundtrackName(@QueryParam("soundtrackName") String soundtrackName) {
        int count = service.countHumanBeingsBySoundtrackNameLessThan(soundtrackName);
        return Response.ok("<result>" + count + "</result>").build();
    }
}
