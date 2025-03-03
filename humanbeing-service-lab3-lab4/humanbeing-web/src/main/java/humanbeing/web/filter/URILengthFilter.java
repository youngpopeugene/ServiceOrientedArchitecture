package humanbeing.web.filter;

import humanbeing.ejb.dto.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Provider
@PreMatching
public class URILengthFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (requestContext.getUriInfo().getRequestUri().toString().length() > 1024) {
            requestContext.abortWith(
                    Response.status(414)
                            .type(MediaType.APPLICATION_XML)
                            .entity(new ErrorResponse("Request URI is too long"))
                            .build()
            );
        }
    }
}