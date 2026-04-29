package com.edusyspro.api.model;

import com.edusyspro.api.model.enums.ProgramStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseProgramTiming {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.ORDINAL)
    private ProgramStatus status;

    private ZonedDateTime completedAt;
    private ZonedDateTime updatedAt;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    private AcademicYear academicYear;
}
