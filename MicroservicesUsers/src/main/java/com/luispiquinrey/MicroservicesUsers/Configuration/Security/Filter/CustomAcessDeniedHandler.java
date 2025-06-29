package com.luispiquinrey.MicroservicesUsers.Configuration.Security.Filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAcessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setHeader("knot-error-security-authentication", "Access denied");
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Access denied due to missing or invalid JWT token in the request header. Please ensure your token is present and valid to access this resource.");
    }
}
