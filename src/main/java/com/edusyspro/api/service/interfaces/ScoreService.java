package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.model.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScoreService {

    Page<Score> getLastScoresByStudent(String studentId, Pageable pageable);

}
