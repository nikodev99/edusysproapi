package com.edusyspro.api.dto;

import com.edusyspro.api.dto.custom.SchoolBasic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndividualUser {
    private UUID userId;
    private String firstName;
    private String lastName;
    private List<SchoolBasic> schools;
}
