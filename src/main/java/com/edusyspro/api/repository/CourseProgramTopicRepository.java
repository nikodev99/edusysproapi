package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.CourseProgramTopicEssential;
import com.edusyspro.api.model.CourseProgramTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseProgramTopicRepository extends JpaRepository<CourseProgramTopic, Long> {
    @Query("""
        SELECT cpt.id as topicId, cpt.title as topicTitle, cpt.description as topicDescription, cpt.order as order,
        cpt.timing.status as topicStatus, cpt.timing.id as topicTimingId, cpt.timing.startDate as topicStartDate,
        cpt.timing.endDate as topicEndDate, cpt.timing.updatedAt as topicUpdateDate, cpt.timing.completedAt as topicCompletedDate,
        cpt.courseProgram.id as programId FROM CourseProgramTopic cpt WHERE cpt.courseProgram.id in (?1)
    """)
    List<CourseProgramTopicEssential> findAllProgramsTopics(List<Long> programsId);
}
