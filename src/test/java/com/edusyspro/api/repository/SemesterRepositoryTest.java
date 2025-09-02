package com.edusyspro.api.repository;

import com.edusyspro.api.model.Semester;
import com.edusyspro.api.utils.MockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SemesterRepositoryTest {

    @Autowired
    private SemesterRepository semesterRepository;

    @Test
    public void SaveYearSemesters() {
        Semester semester = Semester.builder()
                .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                .build();
        Semester semester2 = Semester.builder()
                .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                .build();
        Semester semester3 = Semester.builder()
                .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                .build();

        semesterRepository.saveAll(List.of(semester, semester2, semester3));
    }

}
