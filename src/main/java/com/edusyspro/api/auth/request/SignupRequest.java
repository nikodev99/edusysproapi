package com.edusyspro.api.auth.request;

import com.edusyspro.api.auth.user.UserSchoolRole;
import com.edusyspro.api.auth.user.UserType;
import com.edusyspro.api.auth.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain alphanumeric characters and underscores")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    private String password;

    @Email(message = "Please provide a valid email address")
    @Size(min = 5, max = 254, message = "Email must be between 5 and 254 characters")
    private String email;

    @Pattern(regexp = "^[+]?[0-9]{9,15}$", message = "Please provide a valid phone number")
    private String phoneNumber;

    private Long personalInfoId;

    private UserType userType;

    private UserSchoolRole roles;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .personalInfoId(personalInfoId)
                .userType(userType)
                .schoolAffiliations(new ArrayList<>())
                .build();
    }

    public List<UserSchoolRole> toUserSchoolRole(Long userId) {
        UserSchoolRole userRoles =  UserSchoolRole.builder()
                .userId(userId)
                .schoolId(roles.getSchoolId())
                .roles(roles.getRoles())
                .enabled(true)
                .accountNonLocked(true)
                .failedLoginAttempts(0)
                .build();

        return new ArrayList<>(List.of(userRoles));
    }
}
