package com.edusyspro.api.dto;

import com.edusyspro.api.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrolledStudentGuardian {
    private UUID id;
    private String firstName;
    private String lastName;
    private String maidenName;
    private Gender genre;
    private String emailId;
    private String jobTitle;
    private String company;
    private String telephone;
    private String mobile;
}
