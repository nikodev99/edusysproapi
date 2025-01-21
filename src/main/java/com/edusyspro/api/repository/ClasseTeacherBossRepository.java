package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.TeacherBossEssential;
import com.edusyspro.api.model.ClasseTeacherBoss;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClasseTeacherBossRepository extends JpaRepository<ClasseTeacherBoss, Long> {
    @Query("""
        select new com.edusyspro.api.dto.custom.TeacherBossEssential(c.id, c.principalTeacher.personalInfo.lastName,
        c.principalTeacher.personalInfo.firstName, c.current, c.startPeriod, c.endPeriod)
        from ClasseTeacherBoss c join c.principalTeacher.aClasses cl where cl.id = ?1
    """)
    Page<TeacherBossEssential> findAllTeacherBossByClasse(int classeId, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.TeacherBossEssential(c.id, c.principalTeacher.personalInfo.lastName,
        c.principalTeacher.personalInfo.firstName, c.current, c.startPeriod, c.endPeriod) from ClasseTeacherBoss c
        join c.principalTeacher.aClasses cl where cl.id = ?1 and c.academicYear.id = ?2
    """)
    List<TeacherBossEssential> findTeacherBossByClasseId(int classeId, UUID academicYearId);

    @Query("""
        select new com.edusyspro.api.dto.custom.TeacherBossEssential(c.id, c.principalTeacher.personalInfo.lastName,
        c.principalTeacher.personalInfo.firstName, c.current, c.startPeriod, c.endPeriod) from ClasseTeacherBoss c
        join c.principalTeacher p join p.aClasses cl where cl.id = ?1 and c.current = true
    """)
    Optional<TeacherBossEssential> findCurrentTeacherBoss(int classeId);
}
