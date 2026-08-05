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
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SchoolRepository extends JpaRepository<School, UUID> {

    @Modifying
    @Transactional
    @Query(value = "update School s set s.name = :name where s.id = :id")
    int updateSchoolNameById(@Param("name") String name, @Param("id") UUID id);

    @Query("SELECT g.section FROM Grade g WHERE g.school.id = :schoolId")
    List<Section> getSchoolSections(@Param("schoolId") UUID schoolId);

    @Query("SELECT DISTINCT s FROM AcademicYear a join a.school s where a.id = ?1")
    Optional<School> getSchoolByAcademicYear(UUID academicYear);

    @Query("SELECT s.id FROM AcademicYear a join a.school s where a.id = ?1")
    Optional<UUID> getSchoolIdByAcademicYear(UUID academicYear);

    @Query("SELECT s.id FROM Grade g JOIN g.school s WHERE g.id = ?1")
    UUID getSchoolIdByGrade(Integer gradeId);
}
