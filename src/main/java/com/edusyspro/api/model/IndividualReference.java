package com.edusyspro.api.model;

import com.edusyspro.api.model.enums.IndividualType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IndividualReference {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private UUID schoolId;
    private String schoolAbbr;

    @Enumerated
    @Column(name = "individual_type", nullable = false)
    private IndividualType individualType;

    @Column(name = "reference_year", nullable = false)
    private int year = Year.now().getValue();

    @Column(nullable = false)
    private Long counter = 0L;
}
