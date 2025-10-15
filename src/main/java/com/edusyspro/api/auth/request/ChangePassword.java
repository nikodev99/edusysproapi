package com.edusyspro.api.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChangePassword(
        @NotNull(message = "User Id is required")
        @Positive(message = "User Id must be positive")
        Long userId,

        @NotBlank(message = "old password is required")
        @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-_=+{}|;:,.<>?/])[A-Za-z\\d!@#$%^&*()-_=+{}|;:,.<>?/]{6,100}$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
        )
        String oldPassword,

        @NotBlank(message = "New password is required")
        @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-_=+{}|;:,.<>?/])[A-Za-z\\d!@#$%^&*()-_=+{}|;:,.<>?/]{6,100}$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
        )
        String newPassword
) {
}
