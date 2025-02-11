package com.youngpopeugene.teamservice;

import org.springframework.stereotype.Service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Service
public class ApplicationService {

    private final String BASE_URL = "https://localhost:16239/api/v1/teams";

    private final HTTPClient httpClient;

    {
        try {
            httpClient = new HTTPClient();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    public Response addHumanBeingToTeam(String teamId, String humanBeingId) {
        String url = BASE_URL + "/" + teamId + "/join/" + humanBeingId;
        return httpClient.PATCH(url, "");
    }

    public Response removeHumanBeingFromTeam(String teamId, String humanBeingId) {
        String url = BASE_URL + "/" + teamId + "/unjoin/" + humanBeingId;
        return httpClient.DELETE(url);
    }

    public Response updateTeamCars(String teamId) {
        String url = BASE_URL + "/" + teamId + "/car/add";
        return httpClient.PATCH(url, "");
    }

    public Response makeTeamDepressive(String teamId) {
        String url = BASE_URL + "/" + teamId + "/make-depressive";
        return httpClient.PATCH(url, "");
    }
}

