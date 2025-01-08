package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.ScoreDTO;
import com.edusyspro.api.model.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScoreService {

    Page<ScoreDTO> getLastScoresByStudent(String studentId, Pageable pageable);

    Page<ScoreDTO> getScoresByStudentPerAcademicYear(String studentId, String academicYearId, Pageable pageable);

    List<ScoreDTO> getScoresByStudentPerAcademicYear(String studentId, String academicYearId, int subjectId);

    List<ScoreDTO> getAllTeacherMarks(long teacherId);

    Page<ScoreDTO> getAllAssignmentScores(long assignmentId, Pageable pageable);

    List<ScoreDTO> getBestStudentBySubjectScore(long teacherId, int subjectId);

    List<ScoreDTO> getBestStudentByScore(long teacherId);

}
