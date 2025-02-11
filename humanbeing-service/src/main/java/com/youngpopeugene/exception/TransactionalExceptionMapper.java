package com.youngpopeugene.exception;

import com.youngpopeugene.dto.ErrorResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class TransactionalExceptionMapper implements ExceptionMapper<TransactionalException> {
    @Override
    public Response toResponse(TransactionalException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Internal Server Error"))
                .type(MediaType.APPLICATION_XML)
                .build();
    }
}
