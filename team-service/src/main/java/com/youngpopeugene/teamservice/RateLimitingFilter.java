package com.youngpopeugene.teamservice;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Order(3)
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final int REQUEST_LIMIT = 20;
    private static final AtomicInteger CURRENT_REQUEST_COUNT = new AtomicInteger(0);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        int currentCount = CURRENT_REQUEST_COUNT.incrementAndGet();
        try {
            if (currentCount > REQUEST_LIMIT) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.setContentType("application/xml");
                response.getWriter().write("<error><message>Too Many Requests</message></error>");
                return;
            }
            filterChain.doFilter(request, response);
        } finally {
            CURRENT_REQUEST_COUNT.decrementAndGet();
        }
    }
}