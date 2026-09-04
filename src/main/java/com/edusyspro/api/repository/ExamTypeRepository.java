package com.edusyspro.api.repository;

import com.edusyspro.api.model.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExamTypeRepository extends JpaRepository<ExamType, Integer> {
    @Query("select e from ExamType e where e.school.id = ?1")
    List<ExamType> findAllBySchoolId(UUID schoolId);

    @Query("select e.id from ExamType e where e.school.id = ?1 and e.name = ?2")
    Optional<Integer> findExistsBySchoolIdAndName(UUID schoolId, String name);
}
