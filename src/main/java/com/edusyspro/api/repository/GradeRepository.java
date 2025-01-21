package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.GradeBasicValue;
import com.edusyspro.api.dto.custom.PlanningEssential;
import com.edusyspro.api.model.Grade;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface GradeRepository extends JpaRepository<Grade, Integer> {

    @Query("""
        select new com.edusyspro.api.dto.custom.GradeBasicValue(g.id, g.section, g.subSection)
        from Grade g where g.school.id = ?1
    """)
    List<GradeBasicValue> findAllGradeBySchool(UUID schoolId);

    @Query("""
        select new com.edusyspro.api.dto.custom.PlanningEssential(p.id, p.designation, p.termStartDate, p.termEndDate,
        p.semestre.semesterName, p.semestre.academicYear.years) from Grade g join g.planning p where g.id = ?1 and p.semestre.academicYear.id = ?2
    """)
    List<PlanningEssential> findPlanningsByGrade(int gradeId, UUID academicYearId);

    //TEST

    @Modifying
    @Transactional
    @Query("UPDATE Grade g SET g.subSection = ?1 WHERE g.id = ?2")
    int updateGradeSubSectionById(String subSection, int id);

    @Query("SELECT g FROM Grade g LEFT JOIN FETCH g.planning WHERE g.section = ?1")
    List<Grade> findAllBySectionName(Section section);

    Grade getGradeBySection(Section section);

}
