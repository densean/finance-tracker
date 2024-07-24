package com.example.finance_tracker.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component("customAuthenticationEntryPoint")
public class SecurityExceptionHandlers implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("errorMessage", e.getMessage());
        errorDetails.put("errorCause", e.getCause() != null ? e.getCause().toString() : null);
        errorDetails.put("errorCode", "S03");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", "Error occurred");
        responseMap.put("success", false);
        responseMap.put("status", HttpStatus.FORBIDDEN.value());
        responseMap.put("errors", errorDetails);
        responseMap.put("payload", null);

        response.getWriter().write(objectMapper.writeValueAsString(responseMap));
    }
}

