package com.youngpopeugene.dto;


import com.youngpopeugene.model.HumanBeing;
import com.youngpopeugene.model.Team;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement()
public class PageTeam {
    private long totalItems;
    private List<Team> teams;
    private int totalPages;
    private int currentPage;

    @XmlElementWrapper(name = "teams")
    @XmlElement(name = "team")
    public List<Team> getTeams() {
        return teams;
    }
}
