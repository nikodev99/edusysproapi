package com.edusyspro.api.dto;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuardianEssential {
    private UUID id;
    private String firstName;
    private String lastName;
    private String maidenName;
    private Status status;
    private Gender genre;
    private String emailId;
    private String jobTitle;
    private String company;
    private String telephone;
    private String mobile;
    private Address address;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;
}
