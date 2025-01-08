package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.ScoreDTO;
import com.edusyspro.api.dto.custom.ScoreBasicValue;
import com.edusyspro.api.dto.custom.ScoreEssential;
import com.edusyspro.api.repository.ScoreRepository;
import com.edusyspro.api.service.interfaces.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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
    public List<ScoreDTO> getScoresByStudentPerAcademicYear(String studentId, String academicYearId, int subjectId) {
        return scoreRepository.findAllByStudentIdAcademicYearAndSubjectId(
                UUID.fromString(academicYearId),
                UUID.fromString(studentId),
                subjectId
        ).stream()
            .map(ScoreEssential::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<ScoreDTO> getAllTeacherMarks(long teacherId) {
        return scoreRepository.finsAllTeacherMarks(teacherId).stream()
                .map(ScoreBasicValue::toDTO)
                .toList();
    }

    @Override
    public Page<ScoreDTO> getAllAssignmentScores(long assignmentId, Pageable pageable) {
        return scoreRepository.findScoresByAssignment(assignmentId, pageable)
                .map(ScoreBasicValue::toDTO);
    }

    @Override
    public List<ScoreDTO> getBestStudentBySubjectScore(long teacherId, int subjectId) {
        return scoreRepository.findBestStudentByScores(teacherId, subjectId, PageRequest.of(0, 5))
                .map(ScoreBasicValue::toDTO)
                .toList();
    }

    @Override
    public List<ScoreDTO> getBestStudentByScore(long teacherId) {
        return scoreRepository.findBestStudentByScores(teacherId, PageRequest.of(0, 5))
                .map(ScoreBasicValue::toDTO)
                .toList();
    }
}
