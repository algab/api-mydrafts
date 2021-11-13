package br.com.mydrafts.apimydrafts.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

public class AuthException implements AuthenticationEntryPoint, AccessDeniedHandler {

    private static final Integer STATUS_UNAUTHORIZED = 401;
    private static final String MESSAGE_UNAUTHORIZED = "Unauthorized";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        ResponseException exception = createResponse(response);
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, exception);
        out.flush();
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        ResponseException exception = createResponse(response);
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, exception);
        out.flush();
    }

    public ResponseException createResponse(HttpServletResponse response) {
        response.setStatus(STATUS_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return ResponseException.builder()
                .status(STATUS_UNAUTHORIZED)
                .error(MESSAGE_UNAUTHORIZED)
                .message(MESSAGE_UNAUTHORIZED)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
}
