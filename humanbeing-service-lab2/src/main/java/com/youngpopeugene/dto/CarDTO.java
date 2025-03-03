package com.youngpopeugene.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CarDTO {
    @NotNull
    private String name;
    private Boolean cool;
}