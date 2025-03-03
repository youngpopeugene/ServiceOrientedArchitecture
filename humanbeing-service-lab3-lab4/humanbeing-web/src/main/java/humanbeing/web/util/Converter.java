package humanbeing.web.util;

import humanbeing.ejb.dto.*;
import humanbeing.ejb.model.*;
import jakarta.enterprise.context.Dependent;
import lombok.NoArgsConstructor;

@Dependent
@NoArgsConstructor
public class Converter {
    public HumanBeing ToHumanBeingFromDTO(HumanBeingDTO humanBeingDTO) {
        HumanBeing humanBeing = new HumanBeing();
        if (humanBeingDTO == null) {
            return humanBeing;
        }
        humanBeing.setName(humanBeingDTO.getName());
        Coordinates coordinates = new Coordinates();
        coordinates.setX(humanBeingDTO.getCoordinates().getX());
        coordinates.setY(humanBeingDTO.getCoordinates().getY());
        humanBeing.setCoordinates(coordinates);
        humanBeing.setRealHero(humanBeingDTO.getRealHero());
        humanBeing.setHasToothpick(humanBeingDTO.getHasToothpick());
        humanBeing.setImpactSpeed(humanBeingDTO.getImpactSpeed());
        humanBeing.setSoundtrackName(humanBeingDTO.getSoundtrackName());
        humanBeing.setMinutesOfWaiting(humanBeingDTO.getMinutesOfWaiting());
        humanBeing.setWeaponType(humanBeingDTO.getWeaponType());
        humanBeing.setMoodType(humanBeingDTO.getMoodType());
        Car car = new Car();
        car.setName(humanBeingDTO.getCar().getName());
        car.setCool(humanBeingDTO.getCar().getCool());
        humanBeing.setCar(car);
        humanBeing.setTeamID(humanBeingDTO.getTeamID());
        return humanBeing;
    }

    public Team ToTeamFromDTO(TeamDTO teamDTO) {
        Team team = new Team();
        if (teamDTO == null) {
            return team;
        }
        team.setName(teamDTO.getName());
        return team;
    }
}
