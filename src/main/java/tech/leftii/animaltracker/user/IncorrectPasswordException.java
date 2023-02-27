package tech.leftii.animaltracker.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;

@Slf4j
public class IncorrectPasswordException extends ErrorResponseException {

    public IncorrectPasswordException() {
        super(HttpStatus.UNAUTHORIZED, asProblemDetail(), null);
        log.error("Password submitted is incorrect.", this);
    }

    private static ProblemDetail asProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,
                "Current password does not match our records.");
        problemDetail.setTitle("Incorrect password");
        problemDetail.setType(URI.create("/api/v1/errors/incorrect-password"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}