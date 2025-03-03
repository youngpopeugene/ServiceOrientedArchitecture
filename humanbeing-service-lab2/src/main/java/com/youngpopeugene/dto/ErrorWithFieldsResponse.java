package com.youngpopeugene.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "error")
@XmlType(propOrder = {"message", "field"})
public class ErrorWithFieldsResponse {
    private String message;
    private List<String> field;
}
