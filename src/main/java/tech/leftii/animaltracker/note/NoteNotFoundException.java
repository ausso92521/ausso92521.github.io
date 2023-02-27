package tech.leftii.animaltracker.note;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;
import java.util.UUID;

/**
 * Custom exception thrown when Note cannot be found in the database using the supplied ID
 */
@Slf4j
public class NoteNotFoundException extends ErrorResponseException {

    public NoteNotFoundException(UUID resourceId) {
        super(HttpStatus.NOT_FOUND, asProblemDetail("Note with ID: %s could not be found".formatted(resourceId)), null);
        log.error("Note with ID: %s could not be found".formatted(resourceId), this);
    }

    private static ProblemDetail asProblemDetail(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message);
        problemDetail.setTitle("Note Not Found");
        problemDetail.setType(URI.create("/api/v1/errors/not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
