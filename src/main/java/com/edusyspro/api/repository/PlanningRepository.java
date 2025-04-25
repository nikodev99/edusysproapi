package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.PlanningBasic;
import com.edusyspro.api.model.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {
    @Query("""
        select new com.edusyspro.api.dto.custom.PlanningBasic(p.id, p.designation, p.termStartDate, p.termEndDate)
        from Planning p join p.semestre s join s.academicYear a join a.school sc where sc.id = ?1 and a.id = ?2
        order by p.termStartDate
    """)
    List<PlanningBasic> findPlanningBasicValues(UUID schoolId, UUID academicYearId);
}
