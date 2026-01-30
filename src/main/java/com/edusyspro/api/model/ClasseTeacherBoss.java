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
public class ClasseTeacherBoss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    private AcademicYear academicYear;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher principalTeacher;

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
