package com.youngpopeugene.exception;

import com.youngpopeugene.dto.ErrorWithFieldsResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception) {
        List<String> list = Arrays.stream(exception.getMessage().trim().split(" "))
                .collect(Collectors.toList());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorWithFieldsResponse("Validation error", list))
                .type(MediaType.APPLICATION_XML)
                .build();
    }
}
