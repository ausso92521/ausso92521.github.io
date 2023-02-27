package tech.leftii.animaltracker.animal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tech.leftii.animaltracker.note.NoteDto;
import tech.leftii.animaltracker.user.ResponseUserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AnimalDto(
        UUID id,
        @NotBlank
        @Size(min = 2, max = 60, message = "Animal's name must be between 2 and 60 characters long.")
        String name,
        @NotNull(message = "Animal date of birth must be specified.")
        LocalDateTime dateOfBirth,
        @NotNull
        ResponseUserDto owner,
        List<NoteDto> notes
) {
}
