package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.ScoreDTO;
import com.edusyspro.api.dto.custom.ScoreAvg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScoreService {

    boolean saveAllScores(List<ScoreDTO> scores, long assignmentId);

    boolean updateAllScores(List<ScoreDTO> scores, long assignmentId);

    Page<ScoreDTO> getLastScoresByStudent(String studentId, Pageable pageable);

    Page<ScoreDTO> getScoresByStudentPerAcademicYear(String studentId, String academicYearId, Pageable pageable);

    List<ScoreDTO> getScoresByStudentPerSubjectPerAcademicYear(String studentId, String academicYearId, int subjectId);

    List<ScoreDTO> getAllTeacherMarks(Long teacherId, List<Long> teacherIds);

    Page<ScoreDTO> getAllAssignmentScores(long assignmentId, Pageable pageable);

    List<ScoreDTO> getAssignmentScores(long assignmentId);

    List<ScoreDTO> getAllAssignmentScores(List<Long> assignmentId);

    List<ScoreDTO> getBestStudentBySubjectScore(long teacherId, int subjectId);

    List<ScoreDTO> getBestStudentByScore(long teacherId);

    List<ScoreDTO> getClasseBestStudents(int classeId, String academicYearId);

    List<ScoreDTO> getClasseBestStudentsByCourse(int classeId, String academicYearId, int courseId);

    List<ScoreDTO> getClassePoorStudents(int classeId, String academicYearId);

    List<ScoreDTO> getCourseBestStudents(int courseId, String academicYearId);

    List<ScoreDTO> getCoursePoorStudents(int courseId, String academicYearId);

    List<ScoreAvg> getClasseAvgScore(int classeId, String academicYearId);

}
