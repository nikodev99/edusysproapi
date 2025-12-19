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

    @Query("""
        select new com.edusyspro.api.dto.custom.ReprimandEssential(r.id, s.academicYear.id, s.academicYear.years, s.student.id,
        s.student.personalInfo.lastName, s.student.personalInfo.firstName, s.student.personalInfo.image, s.student.personalInfo.reference,
        s.classe.id, s.classe.name, s.classe.grade.section, r.reprimandDate, r.type, r.description, r.issuedBy.id, r.issuedBy.firstName,
        r.issuedBy.lastName, r.issuedBy.image, r.issuedBy.reference, r.punishment) from Reprimand r join r.student s
        where r.student.id = ?1 and s.academicYear.id = ?2 order by r.reprimandDate desc
    """)
    Page<ReprimandEssential> findReprimandsByStudentId(UUID student_id, UUID academicYear_id, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.ReprimandEssential(r.id, s.academicYear.id, s.academicYear.years, s.student.id,
        s.student.personalInfo.lastName, s.student.personalInfo.firstName, s.student.personalInfo.image, s.student.personalInfo.reference,
        s.classe.id, s.classe.name, s.classe.grade.section, r.reprimandDate, r.type, r.description, r.issuedBy.id, r.issuedBy.firstName,
        r.issuedBy.lastName, r.issuedBy.image, r.issuedBy.reference, r.punishment) from Reprimand r join r.student s
        where r.issuedBy.id = :teacherId and s.academicYear.current = true and (:status is null or (:operator = 'EQUAL' and r.punishment.status = :status) or
        (:operator = 'DIFF' and r.punishment.status != :status)) order by r.reprimandDate desc
    """)
    Page<ReprimandEssential> findStudentReprimandByTeacher(
            @Param("teacherId") long teacherId,
            @Param("status") PunishmentStatus status,
            @Param("operator") String operator,
            Pageable pageable
    );

    @Query("""
        select new com.edusyspro.api.dto.custom.ReprimandEssential(r.id, s.academicYear.id, s.academicYear.years, s.student.id,
        s.student.personalInfo.lastName, s.student.personalInfo.firstName, s.student.personalInfo.image, s.student.personalInfo.reference,
        s.classe.id, s.classe.name, s.classe.grade.section, r.reprimandDate, r.type, r.description, r.issuedBy.id, r.issuedBy.firstName,
        r.issuedBy.lastName, r.issuedBy.image, r.issuedBy.reference, r.punishment) from Reprimand r join r.student s
        where r.issuedBy.id = :teacherId and s.academicYear.id = :academicYear order by r.reprimandDate desc
    """)
    Page<ReprimandEssential> findStudentReprimandByTeacher(
            @Param("teacherId") long teacherId,
            @Param("academicYear") UUID academicYear,
            Pageable pageable
    );

}
