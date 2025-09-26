package com.edusyspro.api.auth.response;

import com.edusyspro.api.auth.user.UserType;
import com.edusyspro.api.dto.IndividualUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String userAgent;
    private String tokenType = "Bearer";
    private Long id;
    private String username;
    private String email;
    private boolean enabled;
    private boolean accountNonLocked;
    private List<String> roles;
    private Long accountId;
    private IndividualUser user;
    private UserType userType;
}
