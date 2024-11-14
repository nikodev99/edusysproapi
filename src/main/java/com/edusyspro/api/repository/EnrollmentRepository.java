package com.edusyspro.api.repository;

import com.edusyspro.api.dto.GuardianEssential;
import com.edusyspro.api.model.EnrollmentEntity;
import com.edusyspro.api.dto.EnrolledStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "update EnrollmentEntity set isArchived = ?1 where student.id = ?2")
    int updateEnrollmentByStudentId(boolean isArchived, UUID uuid);

    @Query("""
            select new com.edusyspro.api.dto.EnrolledStudent(e.student.id, e.academicYear, e.student.personalInfo.id, e.student.personalInfo.firstName,
            e.student.personalInfo.lastName, e.student.personalInfo.gender, e.student.personalInfo.emailId, e.student.personalInfo.birthDate,
            e.student.personalInfo.birthCity, e.student.personalInfo.nationality, e.student.reference, e.student.personalInfo.image,
            e.enrollmentDate, e.classe.id, e.classe.name, e.classe.category, e.classe.grade.section, e.classe.monthCost,
            e.student.dadName, e.student.momName) from EnrollmentEntity e where e.academicYear.school.id = ?1 and e.academicYear.current = true and e.isArchived = false
    """)
    Page<EnrolledStudent> findEnrolledStudent(UUID schoolId, Pageable pageable);

    @Query(value = """
            select new com.edusyspro.api.dto.EnrolledStudent(e.student.id, e.academicYear, e.student.personalInfo.id, e.student.personalInfo.firstName,
            e.student.personalInfo.lastName, e.student.personalInfo.gender, e.student.personalInfo.emailId, e.student.personalInfo.birthDate,
            e.student.personalInfo.birthCity, e.student.personalInfo.nationality, e.student.reference, e.student.personalInfo.image,
            e.enrollmentDate, e.classe.id, e.classe.name, e.classe.category, e.classe.grade.section, e.classe.monthCost,
            e.student.dadName, e.student.momName) from EnrollmentEntity e where e.academicYear.school.id = ?1 and e.academicYear.current = true
            and e.isArchived = false and (lower(e.student.personalInfo.lastName) like lower(?2) or lower(e.student.personalInfo.firstName) like lower(?2) )
            order by e.student.personalInfo.lastName asc
    """)
    List<EnrolledStudent> findEnrolledStudent(UUID schoolId, String lastname);

    @Query(value = """
            select new com.edusyspro.api.dto.EnrolledStudent(e.student.id, e.academicYear, e.student.personalInfo.id, e.student.personalInfo.firstName,
            e.student.personalInfo.lastName, e.student.personalInfo.gender, e.student.personalInfo.emailId, e.student.personalInfo.birthDate,
            e.student.personalInfo.birthCity, e.student.personalInfo.nationality, e.student.reference, e.student.personalInfo.image,
            e.enrollmentDate, e.classe.id, e.classe.name, e.classe.category, e.classe.grade.section, e.classe.monthCost,
            e.student.dadName, e.student.momName) from EnrollmentEntity e where e.academicYear.school.id = ?1 and e.student.id = ?2
            and e.academicYear.current = true and e.isArchived = false
    """)
    EnrolledStudent findEnrollmentById(UUID schoolId, UUID studentId);

    @Query("select e from EnrollmentEntity e where e.academicYear.school.id = ?1 and e.student.id = ?2 and e.isArchived = true order by e.enrollmentDate desc")
    Page<EnrollmentEntity> findStudentEnrollments(UUID schoolId, UUID studentId, Pageable pageable);

    @Query(value = """
            select new com.edusyspro.api.dto.EnrolledStudent(e.student.id, e.academicYear, e.student.personalInfo.id, e.student.personalInfo.firstName,
            e.student.personalInfo.lastName, e.student.personalInfo.gender, e.student.personalInfo.emailId, e.student.personalInfo.birthDate,
            e.student.personalInfo.birthCity, e.student.personalInfo.nationality, e.student.reference, e.student.personalInfo.image,
            e.enrollmentDate, e.classe.id, e.classe.name, e.classe.category, e.classe.grade.section, e.classe.monthCost,
            e.student.dadName, e.student.momName) from EnrollmentEntity e where e.academicYear.school.id = ?1 and e.student.id <> ?2
            and e.classe.id = ?3 and e.academicYear.id = ?4 order by e.student.personalInfo.lastName asc
   """)
    Page<EnrolledStudent> findStudentClassmateByAcademicYear(UUID schoolId, UUID studentId, int classeId, UUID academicYear, Pageable pageable);

    @Query("""
            select distinct new com.edusyspro.api.dto.GuardianEssential(e.student.guardian.id, e.student.guardian.personalInfo,
            e.student.guardian.jobTitle, e.student.guardian.company, e.student.guardian.createdAt, e.student.guardian.modifyAt)
            from EnrollmentEntity e where e.academicYear.school.id = ?1 and e.academicYear.current = true and e.isArchived = false
    """)
    Page<GuardianEssential> findEnrolledStudentGuardian(UUID schoolId, Pageable pageable);

    @Query("""
            select distinct new com.edusyspro.api.dto.GuardianEssential(e.student.guardian.id, e.student.guardian.personalInfo,
            e.student.guardian.jobTitle, e.student.guardian.company, e.student.guardian.createdAt, e.student.guardian.modifyAt)
            from EnrollmentEntity e where e.academicYear.school.id = ?1 and e.academicYear.current = true and e.isArchived = false
            and (lower(e.student.guardian.personalInfo.lastName) like lower(?2) or lower(e.student.guardian.personalInfo.firstName) like lower(?2))
            order by e.student.guardian.personalInfo.lastName asc
    """)
    List<GuardianEssential> findEnrolledStudentGuardian(UUID schoolId, String lastName);

    @Query("select count(e.id) from EnrollmentEntity e where e.academicYear.school.id = ?1 and e.academicYear.current = true")
    Long countAllStudents(UUID schoolId);
}
