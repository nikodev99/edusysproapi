package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.ScoreDTO;
import com.edusyspro.api.dto.custom.ClasseRanking;
import com.edusyspro.api.dto.custom.GradeRanking;
import com.edusyspro.api.dto.custom.RadarAxis;
import com.edusyspro.api.dto.custom.ScoreAvg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ScoreService {

    boolean saveAllScores(List<ScoreDTO> scores, long assignmentId);

    boolean updateAllScores(List<ScoreDTO> scores, long assignmentId);

    Page<ScoreDTO> getLastScoresByStudent(String studentId, Pageable pageable);

    List<RadarAxis> getStudentCourseStats(String studentId, UUID academicYear);

    Page<ScoreDTO> getScoresByStudentPerAcademicYear(String studentId, String academicYearId, Pageable pageable);

    List<ScoreDTO> getScoresByStudentPerSubjectPerAcademicYear(String studentId, String academicYearId, int subjectId);

    List<ScoreDTO> getAllTeacherMarks(Long teacherId, List<Long> teacherIds);

    Page<ScoreDTO> getAllAssignmentScores(long assignmentId, Pageable pageable);

    List<ScoreDTO> getAssignmentScores(long assignmentId);

    List<ScoreDTO> getAllAssignmentScores(List<Long> assignmentId);

    List<ScoreDTO> getAssignmentScoresByStudent(List<Long> assignmentId, String studentId);

    List<GradeRanking> getBestStudentBySubjectScore(long teacherId, int subjectId, String academicYear);

    List<GradeRanking> getBestStudentByScore(long teacherId, String academicYear);

    List<ClasseRanking> getClasseBestStudents(int classeId, String academicYearId);

    List<ClasseRanking> getClasseBestStudentsByCourse(int classeId, String academicYearId, int courseId);

    List<GradeRanking> getStudentCourseStats(int courseId, String academicYearId);

    List<ScoreAvg> getClasseAvgScore(int classeId, String academicYearId);

    ScoreDTO getStudentAssignmentScore(long assignmentId, String studentId);

    Long countAssignmentSCores(long assignmentId);
}
