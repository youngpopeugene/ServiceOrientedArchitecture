package humanbeing.web.controller;

import humanbeing.ejb.model.*;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class EnumController {
    @GET
    @Path("weapons")
    public Response getWeaponTypes() {
        StringBuilder result = new StringBuilder("<weapons>");
        for (WeaponType weapon : WeaponType.values()) {
            result.append("<weapon>").append(weapon.name()).append("</weapon>");
        }
        result.append("</weapons>");
        return Response.ok(result.toString()).build();
    }

    @GET
    @Path("moods")
    public Response getMoodTypes() {
        StringBuilder result = new StringBuilder("<moods>");
        for (MoodType mood : MoodType.values()) {
            result.append("<mood>").append(mood.name()).append("</mood>");
        }
        result.append("</moods>");
        return Response.ok(result.toString()).build();
    }

    @GET
    @Path("health")
    public Response health() {
        return Response.ok().build();
    }
}
