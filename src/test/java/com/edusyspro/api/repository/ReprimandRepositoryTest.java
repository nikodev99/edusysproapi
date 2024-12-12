package com.edusyspro.api.repository;

import com.edusyspro.api.model.EnrollmentEntity;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.Punishment;
import com.edusyspro.api.model.Reprimand;
import com.edusyspro.api.model.enums.PunishmentStatus;
import com.edusyspro.api.model.enums.PunishmentType;
import com.edusyspro.api.model.enums.ReprimandType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class ReprimandRepositoryTest {

    @Autowired
    private ReprimandRepository reprimandRepository;

    @Test
    public void saveReprimand() {
        Reprimand reprimand = Reprimand.builder()
                .student(EnrollmentEntity.builder()
                        .id(101)
                        .build())
                .reprimandDate(LocalDate.of(2024, 12, 1))
                .type(ReprimandType.DISRESPECT)
                .description("Un élève a harcelé plusieurs camarades en classe, utilisant des insultes répétées et des comportements intimidants, créant un environnement hostile.")
                .issuedBy(Individual.builder()
                        .id(368).build())
                .punishment(Punishment.builder()
                        .isRequire(true)
                        .type(PunishmentType.DETENTION)
                        .description("L’élève a interrompu plusieurs cours par des comportements perturbateurs répétés, refusant d’écouter les consignes et défiant l’autorité des enseignants.")
                        .startDate(LocalDate.of(2024, 12, 1))
                        .endDate(LocalDate.of(2024, 12, 25))
                        .status(PunishmentStatus.DEFERRED)
                        .appealed(false)
                        .appealedNote("")
                        .build())
                .build();

        Reprimand reprimand1 = Reprimand.builder()
                .student(EnrollmentEntity.builder()
                        .id(105)
                        .build())
                .reprimandDate(LocalDate.now())
                .type(ReprimandType.CHEATING)
                .description("Un élève a harcelé plusieurs camarades en classe, utilisant des insultes répétées et des comportements intimidants, créant un environnement hostile.")
                .issuedBy(Individual.builder()
                        .id(368).build())
                .punishment(Punishment.builder()
                        .isRequire(true)
                        .type(PunishmentType.DETENTION)
                        .description("L’élève a interrompu plusieurs cours par des comportements perturbateurs répétés, refusant d’écouter les consignes et défiant l’autorité des enseignants.")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now())
                        .status(PunishmentStatus.IN_PROGRESS)
                        .appealed(false)
                        .appealedNote("")
                        .build())
                .build();

        Reprimand reprimand2 = Reprimand.builder()
                .student(EnrollmentEntity.builder()
                        .id(42)
                        .build())
                .reprimandDate(LocalDate.of(2024, 12, 7))
                .type(ReprimandType.TRUANCY)
                .description("Un élève a harcelé plusieurs camarades en classe, utilisant des insultes répétées et des comportements intimidants, créant un environnement hostile.")
                .issuedBy(Individual.builder()
                        .id(368).build())
                .punishment(Punishment.builder()
                        .isRequire(true)
                        .type(PunishmentType.COURSE_BAN)
                        .description("L’élève a interrompu plusieurs cours par des comportements perturbateurs répétés, refusant d’écouter les consignes et défiant l’autorité des enseignants.")
                        .startDate(LocalDate.of(2024, 12, 7))
                        .endDate(LocalDate.of(2025, 2, 1))
                        .status(PunishmentStatus.APPEALED)
                        .appealed(true)
                        .appealedNote("L'élève denie")
                        .build())
                .build();
        reprimandRepository.saveAll(List.of(reprimand, reprimand1, reprimand2));
    }

}
