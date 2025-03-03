package com.youngpopeugene.exception;

import com.youngpopeugene.dto.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class PageExceptionMapper implements ExceptionMapper<PageException> {

    @Override
    public Response toResponse(PageException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(exception.getMessage()))
                .type(MediaType.APPLICATION_XML)
                .build();
    }
}
