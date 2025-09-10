package com.edusyspro.api.repository;

import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.UUID;

@Repository
public interface SchoolRepository extends JpaRepository<School, UUID> {

    @Modifying
    @Transactional
    @Query(value = "update School s set s.name = :name where s.id = :id")
    int updateSchoolNameById(@Param("name") String name, @Param("id") UUID id);

    @Query("SELECT g.section FROM School s JOIN s.grades g WHERE s.id = :schoolId")
    List<Section> getSchoolSections(@Param("schoolId") UUID schoolId);

    @Transactional
    School getSchoolById(UUID uuid);
}
