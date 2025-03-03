package spring.lab4.teamservicespring.service;

import org.springframework.stereotype.Component;
import spring.lab4.teamservicespring.HTTPClient;
import spring.lab4.teamservicespring.exception.ServiceFaultException;
import spring.lab4.teamservicespring.model.Response;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Component
public class TeamServiceImpl implements TeamService {

    private final String BASE_URL = "https://localhost:16239/api/v1/teams";

    private final HTTPClient httpClient;

    {
        try {
            httpClient = new HTTPClient();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response addHumanBeingToTeam(String humanbeingId, String teamId) throws ServiceFaultException {
        String url = BASE_URL + "/" + teamId + "/join/" + humanbeingId;
        return httpClient.PATCH(url, "");
    }


    @Override
    public Response removeHumanBeingFromTeam(String humanbeingId, String teamId) throws ServiceFaultException {
        String url = BASE_URL + "/" + teamId + "/unjoin/" + humanbeingId;
        return httpClient.DELETE(url);
    }

    @Override
    public Response makeTeamDepressive(String teamId) throws ServiceFaultException {
        String url = BASE_URL + "/" + teamId + "/make-depressive";
        return httpClient.PATCH(url, "");
    }

    @Override
    public Response updateTeamCars(String teamId) throws ServiceFaultException {
        String url = BASE_URL + "/" + teamId + "/car/add";
        return httpClient.PATCH(url, "");
    }

}
