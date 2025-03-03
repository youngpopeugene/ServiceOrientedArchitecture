package com.youngpopeugene.exception;

import com.youngpopeugene.dto.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Internal Server Error"))
                .type(MediaType.APPLICATION_XML)
                .build();
    }
}