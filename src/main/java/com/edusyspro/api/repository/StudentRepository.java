package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.GuardianEssential;
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

    @Query("""
        select new com.edusyspro.api.dto.custom.GuardianEssential(g.id, p.id, p.firstName, p.lastName, p.gender,
        p.status, p.emailId, p.telephone, p.mobile, p.reference, ad.id, ad.number, ad.street, ad.secondStreet, ad.city,
        ad.country, ad.neighborhood, ad.borough, ad.zipCode, g.jobTitle, g.company, g.createdAt, g.modifyAt)
        from StudentEntity s join s.guardian g join g.personalInfo p join p.address ad where s.id = ?1
    """)
    Optional<GuardianEssential> getStudentEntityGuardianByStudentId(UUID id);

    @Query("select s.healthCondition from StudentEntity s where s.id = ?1")
    Optional<HealthCondition> findStudentEntityHealthConditionByStudentId(UUID id);

    @Query("""
            select new com.edusyspro.api.dto.custom.StudentEssential(s.student.id, s.student.personalInfo,
            s.student.dadName, s.student.momName, s.classe.id, s.classe.name, s.classe.category,
            s.classe.grade.section, s.classe.monthCost, s.academicYear.school.id, s.academicYear.school.name,
            s.academicYear.school.abbr, s.academicYear.school.websiteURL) from EnrollmentEntity s where s.academicYear.school.id = ?1
            and s.student.guardian.id = ?2 and s.academicYear.current = true
    """)
    List<StudentEssential> findStudentByGuardianId(UUID schoolId, UUID guardianId);
}
