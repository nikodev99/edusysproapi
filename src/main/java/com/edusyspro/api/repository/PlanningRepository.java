package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.PlanningBasic;
import com.edusyspro.api.dto.custom.PlanningEssential;
import com.edusyspro.api.model.Planning;
import com.edusyspro.api.model.enums.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {
    @Query("""
        select new com.edusyspro.api.dto.custom.PlanningBasic(p.id, p.semestre.semesterId, p.designation, p.termStartDate, p.termEndDate)
        from Planning p join p.semestre s join s.academicYear a join a.school sc where sc.id = ?1 and a.id = ?2
        order by p.termStartDate
    """)
    List<PlanningBasic> findPlanningBasicValues(UUID schoolId, UUID academicYearId);

    @Query("""
        select new com.edusyspro.api.dto.custom.PlanningEssential(p.id, p.designation, p.termStartDate, p.termEndDate, p.semestre)
        from Planning p join p.semestre s join s.academicYear a join a.school sc where sc.id = ?1 and a.current = true
        and p.grade.section = ?2 order by p.termStartDate
    """)
    List<PlanningEssential> findPlanningsByGrade(UUID schoolId, Section section);

    @Query("""
        select new com.edusyspro.api.dto.custom.PlanningEssential(p.id, p.designation, p.termStartDate, p.termEndDate, p.semestre)
        from Planning p where p.id = ?1
    """)
    Optional<PlanningEssential> findPlanningById(long planningId);

    @Query("""
        select new com.edusyspro.api.dto.custom.PlanningBasic(p.id, p.semestre.semesterId, p.designation, p.termStartDate, p.termEndDate)
        from ClasseEntity c join c.grade g join g.planning p where c.id = ?1 and p.termStartDate between ?2 and ?3
    """)
    Optional<List<PlanningBasic>> getClassePlanningByDates(int classeId, LocalDate startDate, LocalDate endDate);
}
