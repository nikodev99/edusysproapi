package com.edusyspro.api.classes;

import com.edusyspro.api.school.entities.AcademicYear;
import com.edusyspro.api.entities.Teacher;
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
    private long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    private AcademicYear academicYear;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher principalTeacher;

    private boolean current;

    private LocalDate startPeriod;

    private LocalDate endPeriod;
}
