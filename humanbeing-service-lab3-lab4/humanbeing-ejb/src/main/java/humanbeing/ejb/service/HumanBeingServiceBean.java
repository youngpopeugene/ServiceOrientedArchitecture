package humanbeing.ejb.service;

import humanbeing.ejb.dto.PageHumanBeing;
import humanbeing.ejb.exception.PageException;
import humanbeing.ejb.model.HumanBeing;
import humanbeing.ejb.repository.HumanBeingRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;

@Stateless(name = "HumanBeingServiceBean")
public class HumanBeingServiceBean implements HumanBeingServiceRemote {
    @Inject
    HumanBeingRepository repository;

    private static final Logger logger = Logger.getLogger(HumanBeingServiceBean.class.getName());

    @PostConstruct
    public void init() {
        logger.info("Создан экземпляр бина HumanBeingServiceBean: " + this);
    }

    @Transactional
    public HumanBeing createHumanBeing(HumanBeing humanBeing) {
        return repository.save(humanBeing);
    }

    @Transactional
    public PageHumanBeing getHumanBeings(int limit, int offset, List<String> sort, List<String> filter) {
        String query = buildQueryString(sort, filter);
        int firstResult = offset;
        List<HumanBeing> humanBeings = repository.createHumanBeingQuery(query, firstResult, limit);
        PageHumanBeing page = new PageHumanBeing();
        page.setHumanbeings(humanBeings);
        page.setTotalItems(repository.countHumanBeings(query));
        page.setTotalPages((int) Math.ceil((double) page.getTotalItems() / limit));
        page.setCurrentPage(offset == 0 ? 0 : offset / limit + 1);
        if (page.getCurrentPage() > page.getTotalPages()) {
            throw new PageException("Current page is greater than total pages");
        }
        return page;
    }

    private String buildQueryString(List<String> sort, List<String> filter) {
        StringBuilder queryStr =
                new StringBuilder(
                        "SELECT h FROM HumanBeing h " +
                                "JOIN h.car hcar " +
                                "JOIN h.coordinates hcoordinates"
                );

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
            f = f.trim();
            String[] parts = f.split("\\[|\\]|=");
            String field = parts[0].trim();
            String operator = parts[1].trim();
            String value = parts[3].trim();
            List<String> strings = Arrays.asList("name", "soundtrackName", "creationDate", "weaponType", "moodType", "car.name");
            if (field.equals("weaponType") || field.equals("moodType")) value = value.toUpperCase();
            if (strings.contains(field)) value = "'" + value + "'";

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

            if (field.contains(".")) filterConditions.add("h" + field + " " + jpqlOperator + " " + value);
            else filterConditions.add("h." + field + " " + jpqlOperator + " " + value);
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
            sortConditions.add("h." + field + " " + direction);
        }

        return sortConditions.toString();
    }

    @Transactional
    public HumanBeing getHumanBeingById(int id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteHumanBeing(int id) {
        repository.deleteById(id);
    }

    @Transactional
    public HumanBeing updateHumanBeing(HumanBeing humanBeing) {
        return repository.update(humanBeing);
    }

    @Transactional
    public double calculateImpactSpeedSum() {
        return repository.calculateImpactSpeedSum();
    }

    @Transactional
    public int countHumanBeingsBySoundtrackNameLessThan(String soundtrackName) {
        return repository.countBySoundtrackNameLessThan(soundtrackName);
    }
}
