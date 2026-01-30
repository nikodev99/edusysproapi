package com.edusyspro.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClasseStudentBoss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    private AcademicYear academicYear;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentEntity principalStudent;

    private Boolean current;

    private LocalDate startPeriod;

    private LocalDate endPeriod;

    @PrePersist
    public void onInserting() {
        current = true;
    }

    @PreUpdate
    public void onUpdating() {
        current = false;
    }
}
