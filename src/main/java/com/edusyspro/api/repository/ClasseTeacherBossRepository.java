package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.TeacherBossEssential;
import com.edusyspro.api.model.ClasseTeacherBoss;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClasseTeacherBossRepository extends JpaRepository<ClasseTeacherBoss, Long> {
    @Query("""
        select new com.edusyspro.api.dto.custom.TeacherBossEssential(c.id, c.principalTeacher.id, c.principalTeacher.personalInfo.lastName,
        c.principalTeacher.personalInfo.firstName, c.current, c.startPeriod, c.endPeriod)
        from ClasseTeacherBoss c where c.classe.id = ?1
    """)
    Page<TeacherBossEssential> findAllTeacherBossByClasse(int classeId, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.TeacherBossEssential(c.id, c.principalTeacher.id, c.principalTeacher.personalInfo.lastName,
        c.principalTeacher.personalInfo.firstName, c.current, c.startPeriod, c.endPeriod) from ClasseTeacherBoss c
        where c.classe.id = ?1 and c.academicYear.school.id = ?2
    """)
    List<TeacherBossEssential> findTeacherBossByClasseId(int classeId, UUID schoolId);

    @Query("""
        select new com.edusyspro.api.dto.custom.TeacherBossEssential(c.id, c.principalTeacher.id, c.principalTeacher.personalInfo.lastName,
        c.principalTeacher.personalInfo.firstName, c.current, c.startPeriod, c.endPeriod) from ClasseTeacherBoss c
        where c.classe.id = ?1 and c.current = true
    """)
    Optional<TeacherBossEssential> findCurrentTeacherBoss(int classeId);

    @Query("SELECT t.id FROM ClasseTeacherBoss ct JOIN ct.principalTeacher t WHERE t.id = ?1 AND ct.classe.id = ?2 AND ct.current = true")
    Optional<UUID> findTeacherIsBoss(UUID teacherId, int classeId);

    @Query("SELECT t.id FROM ClasseTeacherBoss ct JOIN ct.principalTeacher t WHERE t.id = ?1 AND ct.current = true AND ct.academicYear.school.id = ?2")
    Optional<UUID> findTeacherIsBoss(UUID teacherId, UUID schoolId);

    @Query("select s.id from TeacherSchoolAffiliation s join s.aClasses tc where tc.classe.id = ?1 and s.teacher.id = ?2")
    Optional<Long> findTeacherInClasse(int classeId, UUID teacherId);

    @Modifying
    @Transactional
    @Query("update ClasseTeacherBoss c set c.current = false, c.endPeriod = ?2 where c.id = ?1")
    void inactivateClasseTeacherBoss(int bossId, LocalDate endDate);

    @Query("select c.id from ClasseTeacherBoss c where c.classe.id = ?1 and c.current = true")
    List<Integer> fetchAllBossesIdByClasseId(int classeId);
}
