package com.edusyspro.api.school.services;

import com.edusyspro.api.school.entities.Score;

import java.util.List;

public interface ScoreService {

    List<Score> getLastScoresByStudent(String studentId);

}
