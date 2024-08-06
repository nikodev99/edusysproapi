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
public class DepartmentBoss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    private AcademicYear academicYear;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher d_boss;

    private boolean current;

    private LocalDate startPeriod;

    private LocalDate endPeriod;

    @PrePersist
    public void prePersist() {
        startPeriod = LocalDate.now();
    }

}
