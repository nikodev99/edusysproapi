package com.edusyspro.api.student.entities;

import com.edusyspro.api.entities.enums.Blood;
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
    private long id;

    @Convert(converter = JpaConverter.class)
    private List<String> medicalConditions;

    @Convert(converter = JpaConverter.class)
    private List<String> allergies;

    @Convert(converter = JpaConverter.class)
    private List<String> medications;

    @Enumerated
    private Blood bloodType;

    private String handicap;

    private int weight;

    private int height;

}
