package com.edusyspro.api.finance;

import com.edusyspro.api.finance.entities.AccountCharts;
import com.edusyspro.api.finance.entities.FeeCategories;
import com.edusyspro.api.finance.repos.AccountChartRepository;
import com.edusyspro.api.finance.repos.FeeCategoryRepository;
import com.edusyspro.api.model.School;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class FeeCategoryTest {
    @Autowired
    private FeeCategoryRepository feeCategoryRepository;

    @Autowired
    private AccountChartRepository accountChartRepository;

    @Test
    public void testCreateFeeCategories() {
        List<FeeCategories> feeCategories = List.of(
                FeeCategories.builder()
                        .name("Frais Scolaire")
                        .category_code("TUITION")
                        .description("Annual tuition fee covering all academic instruction")
                        .isMandatory(true)
                        .isActive(true)
                        .accountCode(AccountCharts.builder().id(2L).build())
                        .build(),
                FeeCategories.builder()
                        .name("Frais d'inscriptions")
                        .category_code("REGISTRATION")
                        .description("One-time registration fee for new students")
                        .isMandatory(true)
                        .isActive(true)
                        .accountCode(AccountCharts.builder().id(3L).build())
                        .build()
        );

        List<FeeCategories> savedCategories = feeCategoryRepository.saveAll(feeCategories);
        assertFalse(savedCategories.isEmpty());
    }

    private School getSchool() {
        return School.builder()
                .id(UUID.fromString("81148a1b-bdb9-4be1-9efd-fdf4106341d6"))
                .build();
    }
}
