package com.edusyspro.api.finance;

import com.edusyspro.api.finance.entities.FeeAssessment;
import com.edusyspro.api.finance.entities.FeeAssessmentStructured;
import com.edusyspro.api.finance.entities.FeeStructures;
import com.edusyspro.api.finance.enums.AssessmentStatus;
import com.edusyspro.api.finance.repos.FeeAssessmentRepository;
import com.edusyspro.api.model.EnrollmentEntity;
import com.edusyspro.api.utils.Datetime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class FeeAssessmentTest {
    @Autowired
    private FeeAssessmentRepository feeAssessmentRepository;

    @Test
    public void testCreateFeeAssessment(){
        BigDecimal firstStructureTotalAmount = (new BigDecimal("25000"))
                .multiply(new BigDecimal(9));

        BigDecimal secondStructureTotalAmount = new BigDecimal("10000");

        BigDecimal discountAmount = firstStructureTotalAmount.multiply(BigDecimal.valueOf(0.2));
        BigDecimal amountTotal = firstStructureTotalAmount.subtract(discountAmount);

        List<FeeAssessment> feeAssessments = List.of(
                FeeAssessment.builder()
                        .student(getStudent(117L))
                        .structures(List.of(
                                FeeAssessmentStructured.builder()
                                        .feeStructures(getFeeStructures(1L))
                                        .installmentTotalAmount(firstStructureTotalAmount)
                                        .discount(0)
                                        .discountAmount(BigDecimal.ZERO)
                                        .totalAmount(firstStructureTotalAmount)
                                        .build(),
                                FeeAssessmentStructured.builder()
                                        .feeStructures(getFeeStructures(2L))
                                        .installmentTotalAmount(secondStructureTotalAmount)
                                        .discount(0)
                                        .discountAmount(BigDecimal.ZERO)
                                        .totalAmount(secondStructureTotalAmount)
                                        .build()
                        ))
                        .totalAmount(firstStructureTotalAmount.add(secondStructureTotalAmount))
                        .amountPaid(new BigDecimal(85000))
                        .assessmentDate(Datetime.brazzavilleDatetime())
                        .status(AssessmentStatus.ACTIVE)
                        .build(),

                FeeAssessment.builder()
                        .student(getStudent(113L))
                        .structures(List.of(
                                FeeAssessmentStructured.builder()
                                        .feeStructures(getFeeStructures(1L))
                                        .installmentTotalAmount(firstStructureTotalAmount)
                                        .discount(20)
                                        .discountAmount(discountAmount)
                                        .totalAmount(amountTotal)
                                        .build(),
                                FeeAssessmentStructured.builder()
                                        .feeStructures(getFeeStructures(2L))
                                        .installmentTotalAmount(secondStructureTotalAmount)
                                        .discount(0)
                                        .discountAmount(BigDecimal.ZERO)
                                        .totalAmount(secondStructureTotalAmount)
                                        .build()
                        ))
                        .totalAmount(amountTotal.add(secondStructureTotalAmount))
                        .amountPaid(new BigDecimal(60000))
                        .assessmentDate(Datetime.brazzavilleDatetime())
                        .status(AssessmentStatus.ACTIVE)
                        .build()
        );

        List<FeeAssessment> savedAssessments = feeAssessmentRepository.saveAll(feeAssessments);
        assertFalse(savedAssessments.isEmpty());
    }

    @Test
    public void testGetAllAssessments(){
        List<FeeAssessment> feeAssessments = feeAssessmentRepository.findAll();
        System.out.println("ASSESSMENTS: " + feeAssessments);
    }

    private EnrollmentEntity getStudent(Long studentId) {
        return EnrollmentEntity.builder().id(studentId).build();
    }

    private FeeStructures getFeeStructures(Long feeId) {
        return FeeStructures.builder().id(feeId).build();
    }
}
