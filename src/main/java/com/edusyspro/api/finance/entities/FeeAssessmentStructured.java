package com.edusyspro.api.finance.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = "feeStructures")
@Table(name = "fee_assessment_structured", schema = "finance")
public class FeeAssessmentStructured {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "fee_structure_id", referencedColumnName = "id")
    private FeeStructures feeStructures;

    private BigDecimal installmentTotalAmount;
    private Integer discount;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
}
