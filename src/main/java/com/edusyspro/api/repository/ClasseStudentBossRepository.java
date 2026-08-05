package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.StudentBossEssential;
import com.edusyspro.api.model.ClasseStudentBoss;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClasseStudentBossRepository extends JpaRepository<ClasseStudentBoss, Long> {
    @Query("""
        select new com.edusyspro.api.dto.custom.StudentBossEssential(c.id, c.principalStudent.id, c.academicYear.years, c.principalStudent.personalInfo.lastName,
        c.principalStudent.personalInfo.firstName, c.current, c.startPeriod, c.endPeriod)
        from ClasseStudentBoss c where c.classe.id = ?1
    """)
    Page<StudentBossEssential> findAllStudentBossByClasse(int classeId, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.StudentBossEssential(c.id, c.principalStudent.id, c.academicYear.years, c.principalStudent.personalInfo.lastName,
        c.principalStudent.personalInfo.firstName, c.current, c.startPeriod, c.endPeriod) from ClasseStudentBoss c
        where c.classe.id = ?1 and c.academicYear.id = ?2
    """)
    List<StudentBossEssential> findStudentBossByClasseId(int classeId, UUID academicYearId);

    @Query("""
        select new com.edusyspro.api.dto.custom.StudentBossEssential(c.id, c.principalStudent.id, c.academicYear.years, c.principalStudent.personalInfo.lastName,
        c.principalStudent.personalInfo.firstName, c.current, c.startPeriod, c.endPeriod) from ClasseStudentBoss c
        where c.classe.id = ?1 and c.current = true
    """)
    Optional<StudentBossEssential> findCurrentStudentBoss(int classeId);

    @Query("select e.id from EnrollmentEntity e where e.isArchived = false and e.classe.id = ?1 and e.student.id = ?2")
    Optional<Long> findStudentInClasse(int classeId, UUID studentId);

    @Modifying
    @Transactional
    @Query("update ClasseStudentBoss c set c.current = false, c.endPeriod = ?2 where c.id = ?1")
    void inactivateClasseStudentBoss(int bossId, LocalDate endDate);

    @Query("select c.id from ClasseStudentBoss c where c.classe.id = ?1 and c.current = true")
    List<Integer> fetchAllBossesIdByClasseId(int classeId);

}
