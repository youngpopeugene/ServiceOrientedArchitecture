package com.youngpopeugene.teamservice;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(2)
public class URILengthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("urilength filter");
        if (servletRequest instanceof HttpServletRequest httpServletRequest) {
            String uriWithQuery = "%s?%s".formatted(httpServletRequest.getRequestURI(), httpServletRequest.getQueryString());
            if (uriWithQuery.length() > 1024) {
                HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                httpResponse.setContentType("application/xml");
                httpResponse.setStatus(414);
                httpResponse.getWriter().write("<error><message>URI Too Long</message></error>");
                httpResponse.getWriter().flush();
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
