package com.edusyspro.api.model;

import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Status;
import com.edusyspro.api.utils.JpaConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Individual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String maidenName;

    @Enumerated
    private Gender gender;

    @Enumerated
    private Status status;

    private String emailId;

    private LocalDate birthDate;

    @Column(length = 100)
    private String birthCity;

    @Column(length = 100)
    private String nationality;

    @Column(length = 50)
    private String telephone;

    @Column(length = 50)
    private String mobile;

    @Column(length = 50)
    private String reference;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column(length = 100)
    private String image;

    @Convert(converter = JpaConverter.class)
    private List<String> attachments;

    @PrePersist
    @PreUpdate
    public void adjustDateTimeZone() {
        birthDate = convertToTimeZone(birthDate, ZoneId.systemDefault());
    }

    private LocalDate convertToTimeZone(LocalDate localDate, ZoneId zoneId) {
        if (localDate == null) return null;
        LocalDateTime localDateTime = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"));
        return zonedDateTime
                .withZoneSameInstant(zoneId)
                .toLocalDate();
    }
}
