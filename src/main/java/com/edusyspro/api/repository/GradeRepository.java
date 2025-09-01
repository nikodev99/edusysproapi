package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.GradeBasicValue;
import com.edusyspro.api.dto.custom.PlanningEssential;
import com.edusyspro.api.model.Grade;
import com.edusyspro.api.model.enums.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GradeRepository extends JpaRepository<Grade, Integer> {

    @Query("""
        select new com.edusyspro.api.dto.custom.GradeBasicValue(g.id, g.section, g.subSection, g.createdAt, g.modifyAt)
        from Grade g where g.school.id = ?1
    """)
    List<GradeBasicValue> findAllGradeBySchool(UUID schoolId);

    @Query("""
        select new com.edusyspro.api.dto.custom.PlanningEssential(p.id, p.designation, p.termStartDate, p.termEndDate, p.semestre)
        from Grade g join g.planning p where g.id = ?1 and p.semestre.academicYear.id = ?2 order by p.termStartDate asc
    """)
    List<PlanningEssential> findPlanningsByGrade(int gradeId, UUID academicYearId);

    @Query("""
        select new com.edusyspro.api.dto.custom.PlanningEssential(p.id, p.designation, p.termStartDate, p.termEndDate, p.semestre)
        from Grade g join g.planning p where g.id = ?1 and p.semestre.academicYear.current = true order by p.termStartDate asc
    """)
    List<PlanningEssential> findPlanningsByGradeId(int gradeId);

    @Query("""
        select new com.edusyspro.api.dto.custom.GradeBasicValue(g.id, g.section, g.subSection, g.createdAt, g.modifyAt)
        from Grade g where g.id= ?1
    """)
    Optional<GradeBasicValue> findGradeById(int gradeId);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.GradeBasicValue(g.id, g.section, g.subSection, g.createdAt, g.modifyAt)
        FROM Grade g WHERE g.school.id = ?1 AND g.section = ?2 AND g.subSection = ?3
    """)
    Optional<GradeBasicValue> findBySectionName(UUID schoolId, Section section);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.GradeBasicValue(g.id, g.section, g.subSection, g.createdAt, g.modifyAt)
        FROM Grade g WHERE g.school.id = ?1 AND g.section = ?2 AND g.subSection = ?3
    """)
    Optional<GradeBasicValue> gradeExist(UUID schoolId, Section section, String subSection);

    //TEST

    @Modifying
    @Transactional
    @Query("UPDATE Grade g SET g.subSection = ?1 WHERE g.id = ?2")
    int updateGradeSubSectionById(String subSection, int id);

    Grade getGradeBySection(Section section);

}
