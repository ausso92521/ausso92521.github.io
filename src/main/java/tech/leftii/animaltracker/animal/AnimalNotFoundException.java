package tech.leftii.animaltracker.animal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;

@Slf4j
public class AnimalNotFoundException extends ErrorResponseException {

    public AnimalNotFoundException(String animalId) {
        super(HttpStatus.NOT_FOUND, asProblemDetail("Animal with id " + animalId + " not found"), null);
        log.error("animal not found exception thrown searching for: %s".formatted(animalId), this);
    }

    private static ProblemDetail asProblemDetail(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message);
        problemDetail.setTitle("Animal Not Found");
        problemDetail.setType(URI.create("/api/v1/errors/not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}