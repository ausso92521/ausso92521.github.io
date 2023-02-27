package tech.leftii.animaltracker.note;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Record used for sending Not Data tot the client
 * Records chosen for immutability to ensure secure transfers to and from the client
 * @param id
 * @param content
 * @param creationDate
 * @param animalId
 * @param noteDate
 * @param noteDateAsLong
 */
public record NoteDto(
        UUID id,
        @NotBlank(message = "Note content cannot be blank.")
        @Size(min = 1, max = 3000)
        String content,
        LocalDateTime creationDate,
        @NotNull(message = "Note must be assigned to an animal.")
        UUID animalId,
        LocalDateTime noteDate,
        Long noteDateAsLong

) {
}
