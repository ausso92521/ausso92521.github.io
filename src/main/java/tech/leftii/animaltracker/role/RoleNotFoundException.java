package tech.leftii.animaltracker.role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;
import java.util.UUID;

@Slf4j
public class RoleNotFoundException extends ErrorResponseException {

    public RoleNotFoundException(UUID roleId) {
        super(HttpStatus.NOT_FOUND, asProblemDetail("Role with id %s not found".formatted(roleId)), null);
        log.error("Role with id %s not found".formatted(roleId));
    }

    private static ProblemDetail asProblemDetail(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message);
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create("/api/v1/errors/not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}