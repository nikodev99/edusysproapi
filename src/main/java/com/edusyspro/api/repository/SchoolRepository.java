package com.edusyspro.api.repository;

import com.edusyspro.api.school.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface SchoolRepository extends JpaRepository<School, UUID> {

    @Modifying
    @Transactional
    @Query(value = "update School s set s.name = :name where s.id = :id")
    int updateSchoolNameById(@Param("name") String name, @Param("id") UUID id);

    School getSchoolById(UUID uuid);
}
