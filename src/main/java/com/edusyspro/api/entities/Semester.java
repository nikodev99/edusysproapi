package com.edusyspro.api.entities;

import com.edusyspro.api.school.entities.AcademicYear;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "semester_id")
    private int semesterId;

    private String semesterName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    @JsonIgnore
    private AcademicYear academicYear;

    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String description;
}
