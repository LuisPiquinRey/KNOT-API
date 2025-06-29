package com.luispiquinrey.MicroservicesUsers.Configuration.Security.Filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("knot-error-security-authentication", "Authentication failed");
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Error handling credentials via JWT token in the header. Remember, user, if you do not provide your corresponding token, the security system will reject your request.");
    }
}
