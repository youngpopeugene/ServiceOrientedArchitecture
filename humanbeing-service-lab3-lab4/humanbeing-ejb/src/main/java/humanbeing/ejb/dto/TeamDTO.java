package humanbeing.ejb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "team")
public class TeamDTO {
    @NotNull
    @Size(min = 1)
    private String name;
}
