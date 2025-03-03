package com.youngpopeugene.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CoordinatesDTO {
    @Min(1)
    private long x;
    @Max(252)
    @Min(1)
    private int y;
}