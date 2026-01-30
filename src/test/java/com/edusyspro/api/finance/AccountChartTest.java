package com.edusyspro.api.finance;

import com.edusyspro.api.finance.entities.AccountCharts;
import com.edusyspro.api.finance.enums.AccountParents;
import com.edusyspro.api.finance.enums.AccountType;
import com.edusyspro.api.finance.repos.AccountChartRepository;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AccountChartTest {

    @Autowired
    private AccountChartRepository accountChartRepository;

    @Test
    public void testCreateAccountChart() {
        AccountCharts accountCharts = AccountCharts.builder()
                .school(School.builder()
                        .id(UUID.fromString("81148a1b-bdb9-4be1-9efd-fdf4106341d6"))
                        .build())
                .accountCode("411000")
                .accountName("Créances sur frais de scolarité")
                .accountClass(4)
                .parentAccountCode("411")
                .accountType(AccountType.ASSET)
                .isActive(true)
                .isSystemAccount(true)
                .createdBy(Individual.builder()
                        .id(419L)
                        .build())
                .createdAt(ZonedDateTime.now())
                .build();

        AccountCharts createdAccountCharts = accountChartRepository.save(accountCharts);

        System.out.println("createdAccountCharts: " + createdAccountCharts);
        assertTrue(createdAccountCharts.getIsActive());
    }

    @Test
    public void testCreateAnotherAccountChart() {
        AccountCharts accountCharts = AccountCharts.builder()
                .school(School.builder()
                        .id(UUID.fromString("81148a1b-bdb9-4be1-9efd-fdf4106341d6"))
                        .build())
                .accountCode("706000")
                .accountName("Revenue Frais de scolarité")
                .accountClass(7)
                .parentAccountCode(AccountParents.CLASS_7.getParentCode())
                .accountType(AccountType.INCOME)
                .isActive(true)
                .isSystemAccount(true)
                .createdBy(Individual.builder()
                        .id(419L)
                        .build())
                .createdAt(ZonedDateTime.now())
                .build();

        AccountCharts createdAccountCharts = accountChartRepository.save(accountCharts);

        System.out.println("createdAccountCharts: " + createdAccountCharts);
        assertTrue(createdAccountCharts.getIsActive());
    }

    @Test
    public void testCreateOtherAccountChart() {
        AccountCharts accountCharts = AccountCharts.builder()
                .school(School.builder()
                        .id(UUID.fromString("81148a1b-bdb9-4be1-9efd-fdf4106341d6"))
                        .build())
                .accountCode("706100")
                .accountName("Revenue Frais d'inscription")
                .accountClass(7)
                .parentAccountCode(AccountParents.CLASS_7.getParentCode())
                .accountType(AccountType.INCOME)
                .isActive(true)
                .isSystemAccount(true)
                .createdBy(Individual.builder()
                        .id(419L)
                        .build())
                .createdAt(ZonedDateTime.now())
                .build();

        AccountCharts createdAccountCharts = accountChartRepository.save(accountCharts);

        System.out.println("createdAccountCharts: " + createdAccountCharts);
        assertTrue(createdAccountCharts.getIsActive());
    }

}
