package com.edusyspro.api.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserActivityRequest {
    @NotBlank(message = "Action is required")
    @Size(min = 3, max = 50, message = "Action must be between 3 and 50 characters")
    private String action;

    @NotBlank(message = "Description is required")
    @Size(min = 6, max = 255, message = "Description must be between 6 and 255 characters")
    private String description;

    private Long accountId;
}
