package humanbeing.web.util;

import humanbeing.ejb.dto.*;
import humanbeing.ejb.model.*;
import humanbeing.ejb.exception.UnprocessableEntityException;
import humanbeing.ejb.exception.ValidationException;
import jakarta.enterprise.context.Dependent;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

import java.util.*;

@Dependent
public class Validator {
    public int Validate(String id, String name) {
        int numericId;
        try {
            numericId = Integer.parseInt(id);
            if (numericId <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            throw new ValidationException(name);
        }
        return numericId;
    }

    public int ValidateID(String id) {
        return Validate(id, "id");
    }

    public int ValidateTeamID(String id) {
        return Validate(id, "team-id");
    }

    public void ValidateBothIDs(String teamID, String humanBeingID) {
        String result = "";
        try {
            Validate(teamID, "");
        } catch (ValidationException e) {
            result += "team-id ";
        }

        try {
            Validate(humanBeingID, "");
        } catch (ValidationException e) {
            result += "humanbeing-id";
        }

        if (!result.isEmpty()) {
            throw new ValidationException(result);
        }
    }

    public int ValidateHumanBeingSearch(String limit, String offset, List<String> sort, List<String> filter) {
        String result = "";
        int numericOffset = 1;

        try {
            Validate(limit, "");
        } catch (ValidationException e) {
            result += "limit ";
        }

        try {
            if (offset == null || offset.isEmpty()) numericOffset = 0;
            else Validate(offset, "");
        } catch (ValidationException e) {
            result += "offset ";
        }

        try {
            ValidateHumanBeingSort(sort);
        } catch (ValidationException e) {
            result += "sort ";
        }

        try {
            ValidateHumanBeingFilter(filter);
        } catch (ValidationException e) {
            result += "filter ";
        }

        if (!result.isEmpty()) {
            throw new ValidationException(result);
        }
        return numericOffset;
    }

    public void ValidateHumanBeingSort(List<String> sort) {
        List<String> fields = Arrays.asList(
                "id",
                "name",
                "coordinates.x",
                "coordinates.y",
                "creationDate",
                "realHero",
                "hasToothpick",
                "impactSpeed",
                "soundtrackName",
                "minutesOfWaiting",
                "weaponType",
                "moodType",
                "car.name",
                "car.cool",
                "teamID",
                "-id",
                "-name",
                "-coordinates.x",
                "-coordinates.y",
                "-creationDate",
                "-realHero",
                "-hasToothpick",
                "-impactSpeed",
                "-soundtrackName",
                "-minutesOfWaiting",
                "-weaponType",
                "-moodType",
                "-car.name",
                "-car.cool",
                "-teamID"
        );
        if (sort == null || sort.isEmpty()) {
            return;
        }
        for (String s : sort) {
            s = s.trim();
            if (!fields.contains(s)) {
                throw new ValidationException();
            }
        }
    }

    public void ValidateHumanBeingFilter(List<String> filter) {
        List<String> fields = Arrays.asList(
                "id",
                "name",
                "coordinates.x",
                "coordinates.y",
                "creationDate",
                "realHero",
                "hasToothpick",
                "impactSpeed",
                "soundtrackName",
                "minutesOfWaiting",
                "weaponType",
                "moodType",
                "car.name",
                "car.cool",
                "teamID"
        );

        List<String> operators = Arrays.asList(
                "eq",
                "ne",
                "gt",
                "lt",
                "gte",
                "lte"
        );
        if (filter == null || filter.isEmpty()) {
            return;
        }
        for (String f : filter) {
            f = f.trim();
            String[] parts = f.split("\\[|\\]|=");
            if (parts.length != 4) throw new ValidationException();
            if (!parts[2].trim().isEmpty()) throw new ValidationException();
            String field = parts[0].trim();
            String operator = parts[1].trim();
            String value = parts[3].trim();

            List<String> numbers = Arrays.asList(
                    "id",
                    "coordinates.x",
                    "coordinates.y",
                    "impactSpeed",
                    "minutesOfWaiting",
                    "teamID"
            );
            if (numbers.contains(field)) {
                try {
                    Float.parseFloat(value);
                } catch (RuntimeException e) {
                    throw new ValidationException();
                }
            }
            List<String> bools = Arrays.asList(
                    "realHero",
                    "hasToothpick",
                    "car.cool"
            );
            if (bools.contains(field)) {
                if (!value.equals("true") && !value.equals("false")) throw new ValidationException();
            }
            if (field.equals("creationDate")) {
                String[] dates = value.split("/");
                if (dates.length != 3) throw new ValidationException();
                int date1, date2, date3;
                try {
                    date1 = Integer.parseInt(dates[0]);
                    date2 = Integer.parseInt(dates[1]);
                    date3 = Integer.parseInt(dates[2]);
                } catch (RuntimeException err) {
                    throw new ValidationException();
                }
                if (date1 < 1 || date1 > 31) throw new ValidationException();
                if (date2 < 1 || date2 > 12) throw new ValidationException();
                if (date3 < 1900 || date3 > 2200) throw new ValidationException();
            }
            if (field.equals("weaponType")) {
                if (WeaponType.fromString(value) == null) {
                    throw new ValidationException();
                }
            }
            if (field.equals("moodType")) {
                if (MoodType.fromString(value) == null) {
                    throw new ValidationException();
                }
            }
            if (!fields.contains(field) || !operators.contains(operator)) {
                throw new ValidationException();
            }
        }
    }

    public int ValidateTeamSearch(String limit, String offset, List<String> sort, List<String> filter) {
        String result = "";
        int numericOffset = 1;

        try {
            Validate(limit, "");
        } catch (ValidationException e) {
            result += "limit ";
        }

        try {
            if (offset == null || offset.isEmpty()) numericOffset = 0;
            else Validate(offset, "");
        } catch (ValidationException e) {
            result += "offset ";
        }

        try {
            ValidateTeamSort(sort);
        } catch (ValidationException e) {
            result += "sort ";
        }

        try {
            ValidateTeamFilter(filter);
        } catch (ValidationException e) {
            result += "filter ";
        }

        if (!result.isEmpty()) {
            throw new ValidationException(result);
        }

        return numericOffset;
    }

    public void ValidateTeamSort(List<String> sort) {
        List<String> fields = Arrays.asList(
                "id",
                "name",
                "-id",
                "-name"
        );
        if (sort == null || sort.isEmpty()) {
            return;
        }
        for (String s : sort) {
            s = s.trim();
            if (!fields.contains(s)) {
                throw new ValidationException();
            }
        }
    }

    public void ValidateTeamFilter(List<String> filter) {
        List<String> fields = Arrays.asList(
                "id",
                "name"
        );

        List<String> operators = Arrays.asList(
                "eq",
                "ne",
                "gt",
                "lt",
                "gte",
                "lte"
        );

        for (String f : filter) {
            String[] parts = f.split("\\[|\\]|=");
            if (parts.length != 4) throw new ValidationException();
            if (!parts[2].trim().isEmpty()) throw new ValidationException();
            String field = parts[0].trim();
            String operator = parts[1].trim();
            String value = parts[3].trim();

            if (field.equals("id")) {
                try {
                    Integer.parseInt(value);
                } catch (RuntimeException e) {
                    throw new ValidationException();
                }
            }

            if (!fields.contains(field) || !operators.contains(operator)) {
                throw new ValidationException();
            }
        }
    }

    public void ValidateHumanBeing(HumanBeingDTO dto) {
        StringBuilder errorMessage = new StringBuilder();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        Set<ConstraintViolation<HumanBeingDTO>> violations = factory.getValidator().validate(dto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<HumanBeingDTO> violation : violations) {
                errorMessage.append(violation.getPropertyPath()).append(" ");
            }
        }

        if (dto.getCoordinates() != null) {
            Set<ConstraintViolation<CoordinatesDTO>> violationsCoordinates = factory.getValidator().validate(dto.getCoordinates());
            if (!violationsCoordinates.isEmpty()) {
                for (ConstraintViolation<CoordinatesDTO> violation : violationsCoordinates) {
                    errorMessage.append("coordinates.").append(violation.getPropertyPath()).append(" ");
                }
            }
        }

        if (dto.getCar() != null) {
            Set<ConstraintViolation<CarDTO>> violationsCar = factory.getValidator().validate(dto.getCar());
            if (!violationsCar.isEmpty()) {
                for (ConstraintViolation<CarDTO> violation : violationsCar) {
                    errorMessage.append("car.").append(violation.getPropertyPath()).append(" ");
                }
            }
        }

        String weapon = dto.getWeaponType();
        if (WeaponType.fromString(weapon) == null && weapon != null) {
            errorMessage.append("weaponType").append(" ");
        }

        if (MoodType.fromString(dto.getMoodType()) == null) {
            errorMessage.append("moodType").append(" ");
        }

        if (!errorMessage.toString().isEmpty()) {
            throw new UnprocessableEntityException(errorMessage.toString());
        }
    }

    public void ValidateTeam(TeamDTO dto) {
        StringBuilder errorMessage = new StringBuilder();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Set<ConstraintViolation<TeamDTO>> violations = factory.getValidator().validate(dto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<TeamDTO> violation : violations) {
                errorMessage.append(violation.getPropertyPath()).append(" ");
            }
            throw new UnprocessableEntityException(errorMessage.toString());
        }

    }
}