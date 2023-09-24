package com.forever.dadamda.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.JwtErrorResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(
                ErrorCode.INVALID_AUTH_TOKEN.getCode(), ErrorCode.INVALID_AUTH_TOKEN.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(jwtErrorResponse);

        response.getWriter().write(result);
    }
}
