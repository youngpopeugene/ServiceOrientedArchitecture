package humanbeing.web.exception;

import humanbeing.ejb.dto.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class AnotherExceptionMapper implements ExceptionMapper<jakarta.ws.rs.NotFoundException> {

    @Override
    public Response toResponse(jakarta.ws.rs.NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse("Not Found"))
                .type(MediaType.APPLICATION_XML)
                .build();
    }
}