package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.ReprimandEssential;
import com.edusyspro.api.model.Reprimand;
import com.edusyspro.api.model.enums.PunishmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReprimandRepository extends JpaRepository<Reprimand, Long> {

    Optional<List<Reprimand>> findReprimandsByStudentId(long student_id);

    @Query("""
        select new com.edusyspro.api.dto.custom.ReprimandEssential(r.id, r.student.student.id, r.student.student.personalInfo, r.student.classe.name,
        r.student.classe.grade.section, r.reprimandDate, r.type,r.description, r.issuedBy, r.punishment) from Reprimand r
        where r.issuedBy.id = :teacherId and r.student.academicYear.current = true and (:status is null or (:operator = 'EQUAL' and r.punishment.status = :status) or
        (:operator = 'DIFF' and r.punishment.status != :status)) order by r.reprimandDate desc
    """)
    Page<ReprimandEssential> findStudentReprimandByTeacher(
            @Param("teacherId") long teacherId,
            @Param("status") PunishmentStatus status,
            @Param("operator") String operator,
            Pageable pageable
    );

    @Query("""
        select new com.edusyspro.api.dto.custom.ReprimandEssential(r.id, r.student.student.id, r.student.student.personalInfo, r.student.classe.name,
        r.student.classe.grade.section, r.reprimandDate, r.type,r.description, r.issuedBy, r.punishment) from Reprimand r
        where r.issuedBy.id = :teacherId and r.student.classe.id = :classId and r.student.academicYear.current = true order by r.reprimandDate desc
    """)
    Page<ReprimandEssential> findStudentReprimandByTeacher(
            @Param("teacherId") long teacherId,
            @Param("classId") int classId,
            Pageable pageable
    );

}
