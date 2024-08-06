package com.edusyspro.api.repository;

import com.edusyspro.api.model.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("select s from Score s where s.studentEntity.id = ?1 order by s.id desc")
    Page<Score> findLastFiveScoresByStudent(UUID studentId, Pageable pageable);

}
