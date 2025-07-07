package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.StudentEssential;
import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.model.HealthCondition;
import com.edusyspro.api.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {

    StudentEntity findStudentEntityById(UUID id);

    @Query("select s.personalInfo.address from StudentEntity s where s.id = ?1")
    Optional<Address> findStudentEntityAddressByStudentId(UUID id);

    @Query("select s.guardian from StudentEntity s where s.id = ?1")
    Optional<GuardianEntity> getStudentEntityGuardianByStudentId(UUID id);

    @Query("select s.healthCondition from StudentEntity s where s.id = ?1")
    Optional<HealthCondition> findStudentEntityHealthConditionByStudentId(UUID id);

    @Query("""
            select new com.edusyspro.api.dto.custom.StudentEssential(s.student.id, s.student.personalInfo,
            s.student.dadName, s.student.momName, s.classe.id, s.classe.name, s.classe.category,
            s.classe.grade.section, s.classe.monthCost, s.academicYear.school.id, s.academicYear.school.name,
            s.academicYear.school.abbr, s.academicYear.school.websiteURL)
            from EnrollmentEntity s where s.student.guardian.id = ?1 and s.academicYear.current = true
    """)
    List<StudentEssential> findStudentByGuardianId(UUID guardianId);
}
