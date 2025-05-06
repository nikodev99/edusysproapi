package com.edusyspro.api.model;

import com.edusyspro.api.model.enums.Blood;
import com.edusyspro.api.utils.JpaConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class HealthCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Convert(converter = JpaConverter.class)
    private List<String> medicalConditions;

    @Convert(converter = JpaConverter.class)
    private List<String> allergies;

    @Convert(converter = JpaConverter.class)
    private List<String> medications;

    @Enumerated
    private Blood bloodType;

    private String handicap;

    private Integer weight;

    private Integer height;

}
