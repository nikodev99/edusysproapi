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
                        .id(389)
                        .build())
                .classeEntity(MockUtils.FIRSTA)
                .subject(MockUtils.MATH)
                .examName("Premier Devoir de Mathématique du 2e Trimestre")
                .examDate(LocalDate.of(2025, 1, 14))
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(11, 0))
                .passed(true)
                .build();

        repository.save(assignment);
    }

    private Planning getPlanning(long id) {
        return planningRepository.findById(id).orElse(null);
    }

}
