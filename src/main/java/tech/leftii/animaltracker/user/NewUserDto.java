package tech.leftii.animaltracker.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewUserDto(
        @NotBlank(message = "Email cannot be blank.")
        @Email(message = "Email must be formatted properly.")
        String email,
        @NotBlank(message = "Password must not be blank.")
        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters.")
        String password,
        @NotBlank(message = "First name must not be blank.")
        @Size(min = 2, max = 60, message = "First name must be between 2 and 60 characters.")
        String firstName,
        @NotBlank(message = "Last name must not be blank.")
        @Size(min = 1, max = 60, message = "Last name must be between 1 and 60 characters.")
        String lastName,
        String phone) {
}
