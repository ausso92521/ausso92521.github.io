package tech.leftii.animaltracker.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ResponseUserDto(
        @NotNull(message = "ID cannot be null")
        UUID id,
        @NotBlank(message = "Email must not be blank.")
        @Email(message = "Email must be formatted properly.")
        String email,
        @NotBlank(message = "First name must not be blank.")
        @Size(min = 2, max = 60, message = "First name must be between 2 and 60 characters.")
        String firstName,
        @NotBlank(message = "Last name must not be blank.")
        @Size(min = 1, max = 60, message = "Last name must be between 1 and 60 characters.")
        String lastName,
        @NotBlank(message = "Phone number must not be blank.")
        @Size(min = 10, max = 10, message = "Phone must be 10 digits.")
        String phone
) {
}
