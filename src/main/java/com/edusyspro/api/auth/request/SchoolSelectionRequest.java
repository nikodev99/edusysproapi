package com.edusyspro.api.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchoolSelectionRequest {
    @NotNull(message = "School id is required")
    @NotBlank
    private String schoolId;
}
