package com.edusyspro.api.finance.repos;

import com.edusyspro.api.finance.enums.AssessmentStatus;
import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.EnrollmentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "fee_assessment", schema = "finance")
public class FeeAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private EnrollmentEntity student;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "structure_id", referencedColumnName = "id")
    private FeeStructures structures;

    private BigDecimal totalAmount;
    private BigDecimal discount;
    private BigDecimal amountPaid;
    private ZonedDateTime assessmentDate;

    @Enumerated
    private AssessmentStatus status;
}
