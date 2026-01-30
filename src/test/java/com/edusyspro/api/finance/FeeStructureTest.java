package com.edusyspro.api.finance;

import com.edusyspro.api.finance.entities.FeeCategories;
import com.edusyspro.api.finance.entities.FeeStructures;
import com.edusyspro.api.finance.enums.PaymentFrequency;
import com.edusyspro.api.finance.repos.FeeStructureRepository;
import com.edusyspro.api.model.ClasseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class FeeStructureTest {
    @Autowired
    private FeeStructureRepository feeStructureRepository;

    @Test
    public void createFeeStructureTest() {
        List<FeeStructures> feeStructures = List.of(
                FeeStructures.builder()
                    .classe(getClasseEntity(11))
                    .category(FeeCategories.builder().id(1L).build())
                    .amount(new BigDecimal("25000"))
                    .validFrom(LocalDate.now())
                    .validTo(null)
                    .isActive(true)
                    .frequency(PaymentFrequency.MONTHLY)
                    .numberOfInstallments(9)
                    .dueDate(10)
                    .createdAt(ZonedDateTime.now())
                    .build(),

                FeeStructures.builder()
                        .classe(getClasseEntity(11))
                        .category(FeeCategories.builder().id(2L).build())
                        .amount(new BigDecimal("10000"))
                        .validFrom(LocalDate.now())
                        .validTo(null)
                        .isActive(true)
                        .frequency(PaymentFrequency.ONCE)
                        .numberOfInstallments(1)
                        .dueDate(null)
                        .build(),

                FeeStructures.builder()
                        .classe(getClasseEntity(19))
                        .category(FeeCategories.builder().id(1L).build())
                        .amount(new BigDecimal("35000"))
                        .validFrom(LocalDate.now())
                        .validTo(null)
                        .frequency(PaymentFrequency.MONTHLY)
                        .numberOfInstallments(9)
                        .dueDate(10)
                        .createdAt(ZonedDateTime.now())
                        .build()
        );

        List<FeeStructures> savedFeeStructures = feeStructureRepository.saveAll(feeStructures);
        assertFalse(savedFeeStructures.isEmpty());

    }

    public ClasseEntity getClasseEntity(Integer classeId) {
        return ClasseEntity.builder().id(classeId).build();
    }

}
