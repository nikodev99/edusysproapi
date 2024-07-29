package com.edusyspro.api.school.repos;

import com.edusyspro.api.school.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("select s from Score s where s.studentEntity.id = ?1 order by s.id desc limit 5")
    List<Score> findLastFiveScoresByStudent(UUID studentId);

}
