package com.edusyspro.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "exam_type_id", referencedColumnName = "id")
    private ExamType examType;

    @OneToMany(mappedBy = "exam")
    private List<Assignment> assignments;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    private AcademicYear academicYear;
}
