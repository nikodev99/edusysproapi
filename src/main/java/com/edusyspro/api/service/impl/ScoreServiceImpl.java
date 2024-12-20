package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.ScoreDTO;
import com.edusyspro.api.dto.custom.ScoreBasicValue;
import com.edusyspro.api.model.Score;
import com.edusyspro.api.repository.ScoreRepository;
import com.edusyspro.api.service.interfaces.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    public Page<Score> getLastScoresByStudent(String studentId, Pageable pageable) {
        return scoreRepository.findLastFiveScoresByStudent(UUID.fromString(studentId), pageable);
    }

    @Override
    public Page<Score> getScoresByStudentPerAcademicYear(String studentId, String academicYearId, Pageable pageable) {
        return scoreRepository.findAllByStudentIdAndAcademicYear(
                UUID.fromString(academicYearId),
                UUID.fromString(studentId),
                pageable
        );
    }

    @Override
    public List<Score> getScoresByStudentPerAcademicYear(String studentId, String academicYearId, int subjectId) {
        return scoreRepository.findAllByStudentIdAcademicYearAndSubjectId(
                UUID.fromString(academicYearId),
                UUID.fromString(studentId),
                subjectId
        );
    }

    @Override
    public List<ScoreDTO> getAllTeacherMarks(long teacherId) {
        return scoreRepository.finsAllTeacherMarks(teacherId).stream()
                .map(ScoreBasicValue::toDTO)
                .toList();
    }
}
