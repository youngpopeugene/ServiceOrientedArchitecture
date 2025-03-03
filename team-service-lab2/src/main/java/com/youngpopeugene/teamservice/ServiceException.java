package com.youngpopeugene.teamservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceException extends RuntimeException {
    private int code;
    public ServiceException(String message, int code) {
        super(message);
        this.code = code;
    }
}

