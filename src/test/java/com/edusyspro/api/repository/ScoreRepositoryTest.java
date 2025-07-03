package com.edusyspro.api.repository;

import com.edusyspro.api.dto.EnrollmentDTO;
import com.edusyspro.api.model.Assignment;
import com.edusyspro.api.model.Score;
import com.edusyspro.api.model.StudentEntity;
import com.edusyspro.api.service.interfaces.EnrollmentService;
import com.edusyspro.api.utils.MockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class ScoreRepositoryTest {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private EnrollmentService studentService;

    @Test
    public void insertScoreData() {
        List<EnrollmentDTO> students = studentService.getClasseEnrolledStudents(
                MockUtils.STC.getId(),
                String.valueOf(MockUtils.ACADEMIC_YEAR_MOCK.getId()),
                PageRequest.of(0, 21)).toList();

        List<Score> scores = new ArrayList<>();
        List<Long> assignmentIds = List.of(34L, 35L, 36L, 37L, 38L, 39L, 40L, 41L);

        for (EnrollmentDTO student : students) {
            for (Long assignmentId : assignmentIds) {
                scores.add(Score.builder()
                        .assignment(Assignment.builder()
                                .id(assignmentId)
                                .build())
                        .studentEntity(StudentEntity.builder()
                                .id(student.getStudent().getId())
                                .build())
                        .obtainedMark(getMark())
                        .build());
            }
        }

        scoreRepository.saveAll(scores);
    }

    public Double getMark() {
        final Random random = new Random();
        return random.nextDouble(21);
    }
}
