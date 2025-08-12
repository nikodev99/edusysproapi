package com.edusyspro.api.repository;

import com.edusyspro.api.model.SemesterTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SemesterTemplateRepository extends JpaRepository<SemesterTemplate, Long> {
    @Query("select s from SemesterTemplate s where s.school.id = ?1")
    List<SemesterTemplate> findAllBySchoolId(UUID schoolId);
}
