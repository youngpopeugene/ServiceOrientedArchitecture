package com.youngpopeugene.controller;

import com.youngpopeugene.dto.TeamDTO;
import com.youngpopeugene.dto.PageTeam;
import com.youngpopeugene.model.Team;
import com.youngpopeugene.service.TeamService;
import com.youngpopeugene.util.Converter;
import com.youngpopeugene.util.Validator;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/teams")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class TeamController {
    @Inject
    TeamService service;

    @Inject
    private Converter converter;

    @Inject
    private Validator validator;

    @POST
    public Response createTeam(TeamDTO dto) {
        validator.ValidateTeam(dto);
        return Response.status(Response.Status.CREATED).entity(service.createTeam(converter.ToTeamFromDTO(dto))).build();
    }

    @GET
    public Response getTeams(
            @QueryParam("limit") String limit,
            @QueryParam("offset") String offset,
            @QueryParam("sort") List<String> sort,
            @QueryParam("filter") List<String> filter) {
        int numericOffset = validator.ValidateTeamSearch(limit, offset, sort, filter);
        int numericLimit = Integer.parseInt(limit);
        if (numericOffset != 0) numericOffset = Integer.parseInt(offset);

        PageTeam pageResult = service.getTeams(numericLimit, numericOffset, sort, filter);
        return Response.ok(pageResult).build();
    }

    @GET
    @Path("/{id}")
    public Response getTeamById(@PathParam("id") String id) {
        int numericID = validator.ValidateID(id);
        return Response.ok().entity(service.getTeamById(numericID)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTeam(@PathParam("id") String id) {
        int numericID = validator.ValidateID(id);
        service.deleteTeam(numericID);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTeam(@PathParam("id") String id, TeamDTO dto) {
        int numericID = validator.ValidateID(id);
        Team team = converter.ToTeamFromDTO(dto);
        team.setId(numericID);
        return Response.ok(service.updateTeam(team)).build();
    }

    @PATCH
    @Path("/{team-id}/join/{humanbeing-id}")
    public Response addHumanBeingToTeam(@PathParam("team-id") String teamId, @PathParam("humanbeing-id") String humanBeingId) {
        validator.ValidateBothIDs(teamId, humanBeingId);
        int numericTeamID = Integer.parseInt(teamId);
        int numericHumanBeingID = Integer.parseInt(humanBeingId);
        service.addHumanBeingToTeam(numericTeamID, numericHumanBeingID);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{team-id}/unjoin/{humanbeing-id}")
    public Response removeHumanBeingFromTeam(@PathParam("team-id") String teamId, @PathParam("humanbeing-id") String humanBeingId) {
        validator.ValidateBothIDs(teamId, humanBeingId);
        int numericTeamID = Integer.parseInt(teamId);
        int numericHumanBeingID = Integer.parseInt(humanBeingId);
        service.removeHumanBeingFromTeam(numericTeamID, numericHumanBeingID);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/{team-id}/car/add")
    public Response updateTeamCars(@PathParam("team-id") String teamId) {
        int numericTeamID = validator.ValidateTeamID(teamId);
        service.updateTeamCars(numericTeamID);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/{team-id}/make-depressive")
    public Response makeTeamDepressive(@PathParam("team-id") String teamId) {
        int numericTeamID = validator.ValidateTeamID(teamId);
        service.makeTeamDepressive(numericTeamID);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
