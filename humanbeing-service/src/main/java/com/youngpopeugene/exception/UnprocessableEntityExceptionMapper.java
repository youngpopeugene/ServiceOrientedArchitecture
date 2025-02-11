package com.youngpopeugene.exception;

import com.youngpopeugene.dto.ErrorWithFieldsResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UnprocessableEntityExceptionMapper implements ExceptionMapper<UnprocessableEntityException> {
    @Override
    public Response toResponse(UnprocessableEntityException exception) {
        List<String> list = Arrays.stream(exception.getMessage().trim().split(" "))
                .collect(Collectors.toList());
        return Response.status(422)
                .entity(new ErrorWithFieldsResponse("Validation error", list))
                .type(MediaType.APPLICATION_XML)
                .build();
    }
}