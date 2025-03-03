package humanbeing.web.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class RateLimitingResponseFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (!requestContext.getMethod().equalsIgnoreCase("OPTIONS")) RateLimitingFilter.CURRENT_REQUEST_COUNT.decrementAndGet();
        System.out.println("resp count " + RateLimitingFilter.CURRENT_REQUEST_COUNT);
    }
}

