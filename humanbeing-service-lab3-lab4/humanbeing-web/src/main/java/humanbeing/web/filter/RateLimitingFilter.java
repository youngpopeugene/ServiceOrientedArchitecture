package humanbeing.web.filter;

import humanbeing.ejb.dto.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Provider
public class RateLimitingFilter implements ContainerRequestFilter {
    private static final int REQUEST_LIMIT = 20;
    static final AtomicInteger CURRENT_REQUEST_COUNT = new AtomicInteger(0);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        int currentCount = CURRENT_REQUEST_COUNT.incrementAndGet();
        System.out.println("req count " + CURRENT_REQUEST_COUNT);
        if (currentCount > REQUEST_LIMIT) {
            requestContext.abortWith(Response.status(Response.Status.TOO_MANY_REQUESTS)
                    .type(MediaType.APPLICATION_XML)
                    .entity(new ErrorResponse("Too Many Requests"))
                    .build());
        }
    }
}
