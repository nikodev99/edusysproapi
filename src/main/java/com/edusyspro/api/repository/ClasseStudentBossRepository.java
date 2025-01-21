package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.StudentBossEssential;
import com.edusyspro.api.dto.custom.TeacherBossEssential;
import com.edusyspro.api.model.ClasseStudentBoss;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClasseStudentBossRepository extends JpaRepository<ClasseStudentBoss, Long> {
    @Query("""
        select new com.edusyspro.api.dto.custom.StudentBossEssential(c.id, c.principalStudent.personalInfo.lastName,
        c.principalStudent.personalInfo.firstName, c.current, c.startPeriod, c.endPeriod)
        from ClasseStudentBoss c join c.principalStudent.enrollmentEntities cl where cl.classe.id = ?1
    """)
    Page<StudentBossEssential> findAllStudentBossByClasse(int classeId, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.StudentBossEssential(c.id, c.principalStudent.personalInfo.lastName,
        c.principalStudent.personalInfo.firstName, c.current, c.startPeriod, c.endPeriod) from ClasseStudentBoss c
        join c.principalStudent.enrollmentEntities cl where cl.classe.id = ?1 and c.academicYear.id = ?2
    """)
    List<StudentBossEssential> findStudentBossByClasseId(int classeId, UUID academicYearId);

    @Query("""
        select new com.edusyspro.api.dto.custom.StudentBossEssential(c.id, c.principalStudent.personalInfo.lastName,
        c.principalStudent.personalInfo.firstName, c.current, c.startPeriod, c.endPeriod) from ClasseStudentBoss c
        join c.principalStudent p join p.enrollmentEntities cl where cl.classe.id = ?1 and c.current = true
    """)
    Optional<StudentBossEssential> findCurrentStudentBoss(int classeId);
}
