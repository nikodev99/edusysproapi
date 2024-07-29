package com.edusyspro.api.school.impl;

import com.edusyspro.api.school.entities.Score;
import com.edusyspro.api.school.repos.ScoreRepository;
import com.edusyspro.api.school.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Score> getLastScoresByStudent(String studentId) {
        return scoreRepository.findLastFiveScoresByStudent(UUID.fromString(studentId));
    }
}
