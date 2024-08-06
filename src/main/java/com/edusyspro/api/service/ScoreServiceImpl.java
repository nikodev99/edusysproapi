package com.edusyspro.api.service;

import com.edusyspro.api.model.Score;
import com.edusyspro.api.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


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
}
