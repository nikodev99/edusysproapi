package com.edusyspro.api.repository;

import com.edusyspro.api.model.CourseProgramTiming;
import com.edusyspro.api.model.enums.ProgramStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Repository
public interface CourseProgramTimingRepository extends JpaRepository<CourseProgramTiming, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE CourseProgramTiming c SET c.status = ?2, c.updatedAt = ?3 WHERE c.id = ?1")
    int updateStatus(Long programId, ProgramStatus status, ZonedDateTime updatedAt);

    @Modifying
    @Transactional
    @Query("UPDATE CourseProgramTiming c SET c.status = ProgramStatus.COMPLETED, c.updatedAt = ?2, c.completedAt = ?2 WHERE c.id = ?1")
    int completeProgram(Long programId, ZonedDateTime completedAt);
}
