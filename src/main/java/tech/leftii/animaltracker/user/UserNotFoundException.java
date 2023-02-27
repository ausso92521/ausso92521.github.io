package tech.leftii.animaltracker.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;

@Slf4j
public class UserNotFoundException extends ErrorResponseException {

    public UserNotFoundException(String userId) {
        super(HttpStatus.NOT_FOUND, asProblemDetail("User with id " + userId + " not found"), null);
        log.error("User not found exception thrown for user id: %s".formatted(userId), this);
    }

    private static ProblemDetail asProblemDetail(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message);
        problemDetail.setTitle("User Not Found");
        problemDetail.setType(URI.create("/api/v1/errors/not-found"));
        problemDetail.setProperty("errorCategory", "Generic");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}