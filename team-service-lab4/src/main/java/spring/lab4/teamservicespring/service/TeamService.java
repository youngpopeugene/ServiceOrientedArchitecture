package spring.lab4.teamservicespring.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.springframework.stereotype.Controller;
import spring.lab4.teamservicespring.exception.ServiceFaultException;
import spring.lab4.teamservicespring.model.Response;

@Controller
@WebService(name="teamService")
public interface TeamService {
    @WebMethod(action = "urn:addHuman")
    Response addHumanBeingToTeam(@WebParam(name = "humanbeingId") String humanbeingId, @WebParam(name = "teamId") String teamId) throws ServiceFaultException;;

    @WebMethod(action = "urn:removeHuman")
    Response removeHumanBeingFromTeam(@WebParam(name = "humanbeingId") String humanbeingId, @WebParam(name = "teamId") String teamId) throws ServiceFaultException;

    @WebMethod(action = "urn:makeDepressive")
    Response makeTeamDepressive(@WebParam(name = "teamId") String teamId) throws ServiceFaultException;

    @WebMethod(action = "urn:updateCars")
    Response updateTeamCars(@WebParam(name = "teamId") String teamId) throws ServiceFaultException;
}
