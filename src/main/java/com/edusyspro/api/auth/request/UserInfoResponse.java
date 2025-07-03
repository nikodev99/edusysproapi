package com.edusyspro.api.auth.request;

import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private List<Role> roles;
    private School school;
}
