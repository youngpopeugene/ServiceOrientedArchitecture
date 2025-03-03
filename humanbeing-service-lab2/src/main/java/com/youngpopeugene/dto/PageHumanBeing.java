package com.youngpopeugene.dto;


import com.youngpopeugene.model.HumanBeing;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement()
public class PageHumanBeing {
    private long totalItems;
    private List<HumanBeing> humanbeings;
    private int totalPages;
    private int currentPage;

    @XmlElementWrapper(name = "humanBeings")
    @XmlElement(name = "humanBeing")
    public List<HumanBeing> getHumanbeings() {
        return humanbeings;
    }
}