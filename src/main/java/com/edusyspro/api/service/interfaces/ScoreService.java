package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.model.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScoreService {

    Page<Score> getLastScoresByStudent(String studentId, Pageable pageable);

    Page<Score> getScoresByStudentPerAcademicYear(String studentId, String academicYearId, Pageable pageable);

    List<Score> getScoresByStudentPerAcademicYear(String studentId, String academicYearId, int subjectId);

}
