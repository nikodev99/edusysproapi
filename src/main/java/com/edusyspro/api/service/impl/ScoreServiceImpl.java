package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.ScoreDTO;
import com.edusyspro.api.dto.custom.ScoreAvg;
import com.edusyspro.api.dto.custom.ScoreBasic;
import com.edusyspro.api.dto.custom.ScoreBasicValue;
import com.edusyspro.api.dto.custom.ScoreEssential;
import com.edusyspro.api.repository.ScoreRepository;
import com.edusyspro.api.service.interfaces.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    public Page<ScoreDTO> getLastScoresByStudent(String studentId, Pageable pageable) {
        return scoreRepository.findLastFiveScoresByStudent(UUID.fromString(studentId), pageable)
                .map(ScoreEssential::toDTO);
    }

    @Override
    public Page<ScoreDTO> getScoresByStudentPerAcademicYear(String studentId, String academicYearId, Pageable pageable) {
        return scoreRepository.findAllByStudentIdAndAcademicYear(
                UUID.fromString(academicYearId),
                UUID.fromString(studentId),
                pageable
        ).map(ScoreEssential::toDTO);
    }

    @Override
    public List<ScoreDTO> getScoresByStudentPerSubjectPerAcademicYear(String studentId, String academicYearId, int subjectId) {
        return scoreRepository.findAllByStudentIdAcademicYearAndSubjectId(
                UUID.fromString(studentId),
                subjectId,
                UUID.fromString(academicYearId)
        ).stream()
        .map(ScoreEssential::toDTO)
        .collect(Collectors.toList());
    }

    @Override
    public List<ScoreDTO> getAllTeacherMarks(Long teacherId, List<Long> teacherIds) {
        if (teacherId != null) {
            return scoreRepository.findAllTeacherMarks(teacherId).stream()
                    .map(ScoreBasicValue::toDTO)
                    .toList();
        }
        return scoreRepository.findAllTeacherMarks(teacherIds).stream()
                .map(ScoreBasicValue::toDTO)
                .toList();
    }

    @Override
    public Page<ScoreDTO> getAllAssignmentScores(long assignmentId, Pageable pageable) {
        return scoreRepository.findScoresByAssignment(assignmentId, pageable)
                .map(ScoreBasicValue::toDTO);
    }

    @Override
    public List<ScoreDTO> getAssignmentScores(long assignmentId) {
        return scoreRepository.findScoresByAssignment(assignmentId).stream()
                .map(ScoreBasic::toDTO)
                .toList();
    }

    @Override
    public List<ScoreDTO> getAllAssignmentScores(List<Long> assignmentId) {
        return scoreRepository.findScoresByAssignmentIds(assignmentId).stream()
                .map(ScoreBasic::toDTO)
                .toList();
    }

    @Override
    public List<ScoreDTO> getBestStudentBySubjectScore(long teacherId, int subjectId) {
        return scoreRepository.findBestStudentByTeacherScores(teacherId, subjectId, PageRequest.of(0, 5))
                .map(ScoreBasicValue::toDTO)
                .toList();
    }

    @Override
    public List<ScoreDTO> getBestStudentByScore(long teacherId) {
        return scoreRepository.findBestStudentByTeacherScores(teacherId, PageRequest.of(0, 5))
                .map(ScoreBasicValue::toDTO)
                .toList();
    }

    @Override
    public List<ScoreDTO> getClasseBestStudents(int classeId, String academicYearId) {
        Pageable pageable = PageRequest.of(0, 5, JpaSort.unsafe("sum(s.obtainedMark)").descending());
        return scoreRepository.findBestStudentByClasseScores(classeId, UUID.fromString(academicYearId), pageable)
                .map(ScoreBasicValue::toDTO)
                .toList();
    }

    @Override
    public List<ScoreDTO> getClasseBestStudentsByCourse(int classeId, String academicYearId, int courseId) {
        Pageable pageable = PageRequest.of(0, 5, JpaSort.unsafe("sum(s.obtainedMark)").descending());
        return scoreRepository.findBestStudentByClasseBySubjectScores(classeId, UUID.fromString(academicYearId), courseId, pageable)
                .map(ScoreBasicValue::toDTO)
                .toList();
    }

    @Override
    public List<ScoreDTO> getClassePoorStudents(int classeId, String academicYearId) {
        Pageable pageable = PageRequest.of(0, 5, JpaSort.unsafe("sum(s.obtainedMark)"));
        return scoreRepository.findBestStudentByClasseScores(classeId, UUID.fromString(academicYearId), pageable)
                .map(ScoreBasicValue::toDTO)
                .toList();
    }

    @Override
    public List<ScoreDTO> getCourseBestStudents(int courseId, String academicYearId) {
        Pageable pageable = PageRequest.of(0, 5, JpaSort.unsafe("sum(s.obtainedMark)").descending());
        return scoreRepository.findBestStudentByCourseScores(courseId, UUID.fromString(academicYearId), pageable)
                .map(ScoreBasicValue::toDTO)
                .toList();
    }

    @Override
    public List<ScoreDTO> getCoursePoorStudents(int courseId, String academicYearId) {
        Pageable pageable = PageRequest.of(0, 5, JpaSort.unsafe("sum(s.obtainedMark)"));
        return scoreRepository.findBestStudentByCourseScores(courseId, UUID.fromString(academicYearId), pageable)
                .map(ScoreBasicValue::toDTO)
                .toList();
    }

    @Override
    public List<ScoreAvg> getClasseAvgScore(int classeId, String academicYearId) {
        return scoreRepository.findClasseScoresAvgByClasse(classeId, UUID.fromString(academicYearId))
                .stream()
                .map(score -> new ScoreAvg((String) score[0], (long) score[1]))
                .toList();
    }

}
