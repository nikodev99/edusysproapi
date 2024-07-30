package com.edusyspro.api.school.services;

import com.edusyspro.api.school.entities.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScoreService {

    Page<Score> getLastScoresByStudent(String studentId, Pageable pageable);

}
