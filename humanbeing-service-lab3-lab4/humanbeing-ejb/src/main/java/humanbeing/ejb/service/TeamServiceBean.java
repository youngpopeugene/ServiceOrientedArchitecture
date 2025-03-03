package humanbeing.ejb.service;

import humanbeing.ejb.dto.PageTeam;
import humanbeing.ejb.exception.NotFoundException;
import humanbeing.ejb.exception.PageException;
import humanbeing.ejb.model.Car;
import humanbeing.ejb.model.HumanBeing;
import humanbeing.ejb.model.MoodType;
import humanbeing.ejb.model.Team;
import humanbeing.ejb.repository.HumanBeingRepository;
import humanbeing.ejb.repository.TeamRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;

@Stateless(name = "TeamServiceBean")
public class TeamServiceBean implements TeamServiceRemote{
    @Inject
    TeamRepository repository;

    @Inject
    HumanBeingRepository humanBeingRepository;

    private static final Logger logger = Logger.getLogger(TeamServiceBean.class.getName());

    @PostConstruct
    public void init() {
        logger.info("Создан экземпляр бина TeamServiceBean: " + this);
    }

    @Transactional
    public Team createTeam(Team team) {
        return repository.save(team);
    }

    @Transactional
    public PageTeam getTeams(int limit, int offset, List<String> sort, List<String> filter) {
        String query = buildQueryString(sort, filter);
        int firstResult = offset;
        List<Team> teams = repository.createTeamQuery(query, firstResult, limit);
        PageTeam page = new PageTeam();
        page.setTeams(teams);
        page.setTotalItems(repository.countTeams(query));
        page.setTotalPages((int) Math.ceil((double) page.getTotalItems() / limit));
        page.setCurrentPage(offset == 0 ? 0 : offset/limit + 1);
        if (page.getCurrentPage() > page.getTotalPages()) {
            throw new PageException("Current page is greater than total pages");
        }
        return page;
    }

    private String buildQueryString(List<String> sort, List<String> filter) {
        StringBuilder queryStr =
                new StringBuilder("SELECT t FROM Team t");

        if (filter != null && !filter.isEmpty()) {
            queryStr.append(" WHERE ");
            queryStr.append(buildFilterConditions(filter));
        }

        if (sort != null && !sort.isEmpty()) {
            queryStr.append(" ORDER BY ");
            queryStr.append(buildSortConditions(sort));
        }

        return queryStr.toString();
    }

    private String buildFilterConditions(List<String> filter) {
        StringJoiner filterConditions = new StringJoiner(" AND ");

        for (String f : filter) {
            String[] parts = f.split("\\[|\\]|=");
            String field = parts[0].trim();
            String operator = parts[1].trim();
            String value = parts[3].trim();
            if (field.equals("name")) value = "'" + value + "'";

            String jpqlOperator;
            switch (operator) {
                case "eq":
                    jpqlOperator = "=";
                    break;
                case "ne":
                    jpqlOperator = "<>";
                    break;
                case "gt":
                    jpqlOperator = ">";
                    break;
                case "lt":
                    jpqlOperator = "<";
                    break;
                case "gte":
                    jpqlOperator = ">=";
                    break;
                case "lte":
                    jpqlOperator = "<=";
                    break;
                default:
                    jpqlOperator = "";
            }

            filterConditions.add("t." + field + " " + jpqlOperator + " " + value);
        }

        return filterConditions.toString();
    }

    private String buildSortConditions(List<String> sort) {
        StringJoiner sortConditions = new StringJoiner(", ");

        for (String field : sort) {
            field = field.trim();
            String direction = "ASC";
            if (field.startsWith("-")) {
                direction = "DESC";
                field = field.substring(1);
            }
            sortConditions.add("t." + field + " " + direction);
        }

        return sortConditions.toString();
    }

    @Transactional
    public Team getTeamById(int id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteTeam(int id) {
        repository.deleteById(id);
    }

    @Transactional
    public Team updateTeam(Team team) {
        return repository.update(team);
    }

    @Transactional
    public void addHumanBeingToTeam(int teamId, int humanBeingId) {
        Team team = repository.findById(teamId);
        HumanBeing humanBeing = humanBeingRepository.findById(humanBeingId);
        humanBeing.setTeamID(team.getId());
        humanBeingRepository.update(humanBeing);
    }

    @Transactional
    public void removeHumanBeingFromTeam(int teamId, int humanBeingId) {
        Team team = repository.findById(teamId);
        HumanBeing humanBeing = humanBeingRepository.findById(humanBeingId);

        if (humanBeing.getTeamID() == null || humanBeing.getTeamID() != team.getId()) {
            throw new NotFoundException("HumanBeing does not belong to the specified team");
        }

        humanBeing.setTeamID(null);
        humanBeingRepository.update(humanBeing);
    }

    @Transactional
    public void updateTeamCars(int teamId) {
        repository.findById(teamId);
        List<HumanBeing> teamMembers = humanBeingRepository.findByTeamId(teamId);

        for (HumanBeing human : teamMembers) {
            Car ladaKalina = new Car();
            ladaKalina.setName("Lada Kalina");
            ladaKalina.setCool(true);
            human.setCar(ladaKalina);
            humanBeingRepository.update(human);
        }
    }

    @Transactional
    public void makeTeamDepressive(int teamId) {
        repository.findById(teamId);
        List<HumanBeing> teamMembers = humanBeingRepository.findByTeamId(teamId);

        for (HumanBeing human : teamMembers) {
            human.setMoodType(MoodType.DEPRESSIVE.toString());
            humanBeingRepository.update(human);
        }
    }
}
