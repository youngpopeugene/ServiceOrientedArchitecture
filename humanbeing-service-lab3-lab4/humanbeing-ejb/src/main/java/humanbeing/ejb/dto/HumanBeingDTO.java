package humanbeing.ejb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "humanBeing")
public class HumanBeingDTO {
    @NotNull
    @Size(min = 1)
    private String name;
    @NotNull
    private CoordinatesDTO coordinates;
    @NotNull
    private Boolean realHero;
    @NotNull
    private Boolean hasToothpick;
    @NotNull
    private Double impactSpeed;
    @NotNull
    private String soundtrackName;
    @NotNull
    private Float minutesOfWaiting;
    private String weaponType;
    @NotNull
    private String moodType;
    @NotNull
    private CarDTO car;
    private Integer teamID;
}
