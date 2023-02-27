package tech.leftii.animaltracker.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserDto(
        @Email
        String email,
        @Size(min = 2, max = 60, message = "First name must be between 2 and 60 characters.")
        String firstName,
        @Size(min = 2, max = 60, message = "Last name must be between 2 and 60 characters.")
        String lastName,
        @Size(min = 10, max = 10, message = "Phone number must be 10 digits.")
        String phone,
        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long.")
        String currentPassword,
        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long.")
        String newPassword
) {
}
