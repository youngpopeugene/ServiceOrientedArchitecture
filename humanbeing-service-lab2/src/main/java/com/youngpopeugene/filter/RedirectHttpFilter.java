package com.youngpopeugene.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;

import java.net.URI;


@Provider
@PreMatching
public class RedirectHttpFilter implements ContainerRequestFilter {
    private static final String HTTPS_PORT = ":8443";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        URI requestUri = requestContext.getUriInfo().getRequestUri();

        if ("http".equalsIgnoreCase(requestUri.getScheme()) &&
                !requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            String httpQuery = requestUri.getRawQuery();
            String httpsQuery = httpQuery == null ? "" : "?" + httpQuery;

            URI httpsUri = URI.create("https://" + requestUri.getHost() + HTTPS_PORT + requestUri.getRawPath() + httpsQuery);
            requestContext.abortWith(Response.status(307).location(httpsUri).build());
        }
    }
}
