package tech.leftii.animaltracker.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

@Slf4j
public class FormLoginFailureHandler implements AuthenticationFailureHandler {

    /**
     * Handles Authentication Failures when user attempts to login
     *
     * @param request the request during which the authentication attempt occurred.
     * @param response the response.
     * @param exception the exception which was thrown to reject the authentication
     * request.
     * @throws IOException thrown if jackson json serialization fails
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        response.getWriter().write(objectMapper.writeValueAsString(asProblemDetail()));
        response.getWriter().flush();
    }

    /**
     * Method used to create a {@link ProblemDetail} object containing the details of
     * the error response to return to the client on failed authentication
     * @return {@link ProblemDetail} contains data used to create response body for API
     */
    private static ProblemDetail asProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,
                "Login Failed. Check your email and password and try again.");
        problemDetail.setTitle("Login failed.");
        problemDetail.setType(URI.create("/api/v1/errors/login-failed"));
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }
}
