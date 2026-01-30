package com.edusyspro.api.finance.entities;

import com.edusyspro.api.finance.enums.AssessmentStatus;
import com.edusyspro.api.model.EnrollmentEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "student")
@Table(name = "fee_assessment", schema = "finance")
public class FeeAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private EnrollmentEntity student;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "structure_id", referencedColumnName = "id")
    private List<FeeAssessmentStructured> structures;

    private BigDecimal totalAmount;
    private BigDecimal amountPaid;
    private ZonedDateTime assessmentDate;

    @Enumerated
    private AssessmentStatus status;
}
