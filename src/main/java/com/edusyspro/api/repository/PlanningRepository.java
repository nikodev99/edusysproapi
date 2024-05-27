package com.edusyspro.api.repository;

import com.edusyspro.api.entities.Planning;
import com.edusyspro.api.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE Planning p SET p.school = ?1 WHERE p.id = ?2")
    int updateSchoolById(School school, long id);

}
