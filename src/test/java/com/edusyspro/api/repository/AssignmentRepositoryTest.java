package com.edusyspro.api.repository;

import com.edusyspro.api.model.*;
import com.edusyspro.api.utils.MockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
public class AssignmentRepositoryTest {

    @Autowired
    private AssignmentRepository repository;

    @Autowired
    private PlanningRepository planningRepository;

    @Test
    public void addAssignment() {
        Assignment assignment = Assignment.builder()
                .semester(Planning.builder()
                        .id(6)
                        .build())
                .exam(Exam.builder()
                        .id(2)
                        .build())
                .preparedBy(Individual.builder()
                        .id(398)
                        .build())
                .classeEntity(MockUtils.TERD)
                .subject(MockUtils.ANG)
                .examName("Deuxième Devoir d'Anglais du 2e Trimestre")
                .examDate(LocalDate.now().plusDays(35))
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(12, 0))
                .build();

        repository.save(assignment);
    }

    private Planning getPlanning(long id) {
        return planningRepository.findById(id).orElse(null);
    }

}
