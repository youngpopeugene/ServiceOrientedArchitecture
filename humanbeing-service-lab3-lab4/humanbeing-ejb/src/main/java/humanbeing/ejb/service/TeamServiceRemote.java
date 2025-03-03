package humanbeing.ejb.service;

import humanbeing.ejb.dto.PageTeam;
import humanbeing.ejb.model.Team;
import jakarta.ejb.Local;
import jakarta.ejb.Remote;

import java.util.List;

@Local
public interface TeamServiceRemote {
    Team createTeam(Team team);
    PageTeam getTeams(int limit, int offset, List<String> sort, List<String> filter);
    Team getTeamById(int id);
    void deleteTeam(int id);
    Team updateTeam(Team team);
    void addHumanBeingToTeam(int teamId, int humanBeingId);
    void removeHumanBeingFromTeam(int teamId, int humanBeingId);
    void updateTeamCars(int teamId);
    void makeTeamDepressive(int teamId);
}
