package com.edusyspro.api.repository;

import com.edusyspro.api.model.TeachingReport;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeachingReportRepository extends JpaRepository<TeachingReport, Long> {
    @Query("""
        SELECT tr.id AS id, tr.schedule.id AS schedule_id, tr.isLateSubmission AS late, tr.reportStatus AS status, tr.notes AS notes,
        tr.sessionDate AS date FROM TeachingReport tr
        WHERE tr.teacher.id = ?1 AND tr.sessionDate BETWEEN ?2 AND ?3
    """)
    List<Tuple> findAllWeekReport(UUID teacherId, LocalDate startDate, LocalDate endDate);

    @Query("""
        SELECT tr.id, tr.courseProgram.name, tr.courseProgramTopic.title, tr.sessionDate, tr.duration_minutes, tr.reportStatus,
        tr.isLateSubmission, tr.notes, tr.createdAt FROM TeachingReport tr WHERE tr.id = ?1
    """)
    Optional<Tuple> findReportById(Long id);
}
