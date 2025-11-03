package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.EnrolledStudentBasic;
import com.edusyspro.api.dto.custom.GuardianEssential;
import com.edusyspro.api.model.EnrollmentEntity;
import com.edusyspro.api.dto.custom.EnrolledStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {

    /**
     * Updates the enrollment status for a student by setting the archived status.
     *
     * @param isArchived a boolean indicating whether the enrollment should be archived
     * @param uuid the unique identifier of the student whose enrollment is to be updated
     * @return an integer representing the number of records updated in the database
     */
    @Modifying
    @Transactional
    @Query("update EnrollmentEntity e set e.isArchived = ?1 where e.student.id = ?2 and e.academicYear.id = ?3")
    int updateEnrollmentByStudentId(boolean isArchived, UUID uuid, UUID academicYearId);

    /**
     * Retrieves a paginated list of enrolled students for a given school and the current academic year.
     *
     * @param schoolId the unique identifier of the school to fetch enrolled students for
     * @param pageable pagination information including page number and size
     * @return a paginated list of enrolled students matching the specified criteria
     */
    @Query("""
            select new com.edusyspro.api.dto.custom.EnrolledStudent(e.id, e.student.id, e.student.personalInfo, e.academicYear,
            e.enrollmentDate, e.classe.id, e.classe.name, e.classe.category, e.classe.grade.section, e.isArchived, e.classe.monthCost,
            e.student.dadName, e.student.momName) from EnrollmentEntity e
            where e.academicYear.school.id = ?1 and e.academicYear.current = true and e.isArchived = false
    """)
    Page<EnrolledStudent> findEnrolledStudent(UUID schoolId, Pageable pageable);

    /**
     * Retrieves a list of enrolled students based on the provided school ID and partial or full last name match.
     *
     * @param schoolId the unique identifier of the school for which the enrolled students are to be retrieved
     * @param lastname the partial or full last name of the students used for filtering the results
     * @return a list of {@link EnrolledStudent} containing the details of students enrolled in the specified school,
     *         filtered by the provided last name criteria, and ordered by last name in ascending order
     */
    @Query(value = """
            select new com.edusyspro.api.dto.custom.EnrolledStudent(e.id, e.student.id, e.student.personalInfo, e.academicYear,
            e.enrollmentDate, e.classe.id, e.classe.name, e.classe.category, e.classe.grade.section, e.isArchived, e.classe.monthCost,
            e.student.dadName, e.student.momName) from EnrollmentEntity e where e.academicYear.school.id = ?1 and e.academicYear.current = true
            and e.isArchived = false and (lower(e.student.personalInfo.lastName) like lower(?2) or lower(e.student.personalInfo.firstName) like lower(?2)
            or lower(e.student.personalInfo.reference) like lower(?2))
            order by e.student.personalInfo.lastName asc
    """)
    List<EnrolledStudent> findEnrolledStudent(UUID schoolId, String lastname);

    @Query(value = """
            select distinct new com.edusyspro.api.dto.custom.EnrolledStudent(e.id, e.student.id, e.student.personalInfo, e.academicYear,
            e.enrollmentDate, e.classe.id, e.classe.name, e.classe.category, e.classe.grade.section, e.isArchived, e.classe.monthCost,
            e.student.dadName, e.student.momName) from EnrollmentEntity e where e.academicYear.school.id = ?1
            and (lower(e.student.personalInfo.lastName) like lower(?2) or lower(e.student.personalInfo.firstName) like lower(?2)
            or lower(e.student.personalInfo.reference) like lower(?2)) order by e.enrollmentDate desc
    """)
    List<EnrolledStudent> findUnenrolledStudent(UUID schoolId, String lastname);

    /**
     * Finds the enrollment details for a student by their unique identifier.
     *
     * @param studentId the unique identifier of the student whose enrollment information is to be retrieved
     * @return an EnrolledStudent object containing the enrollment details of the specified student
     */
    @Query(value = """
            select new com.edusyspro.api.dto.custom.EnrolledStudent(e.id, e.student.id, e.student.personalInfo, e.academicYear,
            e.enrollmentDate, e.classe.id, e.classe.name, e.classe.category, e.classe.grade.section, e.isArchived, e.classe.monthCost,
            e.student.dadName, e.student.momName) from EnrollmentEntity e where e.student.id = ?1
            and e.academicYear.current = true and e.isArchived = false
    """)
    EnrolledStudent findEnrollmentById(UUID studentId);

    /**
     * Finds a paginated list of enrollments for a specific student in a given school that have been archived.
     *
     * @param studentId the unique identifier of the student whose enrollments are to be retrieved
     * @return a paginated list of enrollments containing details about the student's enrollment history
     */
    @Query("""
        select new com.edusyspro.api.dto.custom.EnrolledStudentBasic(e.id, e.student.id, e.academicYear.id, e.academicYear.years, e.classe.id, e.classe.name, e.classe.grade.section,
        e.classe.monthCost, e.student.personalInfo.id, e.student.personalInfo.firstName, e.student.personalInfo.lastName,
        e.student.personalInfo.image, e.student.personalInfo.reference, e.isArchived) from EnrollmentEntity e where e.student.id = ?1
        order by e.enrollmentDate desc
    """)
    List<EnrolledStudentBasic> findStudentEnrollments(UUID studentId);

    /**
     * Retrieves a paginated list of classmates enrolled in the same class for a specific academic year,
     * excluding the student identified by the given student ID.
     *
     * @param studentId the unique identifier of the student whose classmates are being searched
     *                  (this student will be excluded from the results)
     * @param classeId the unique identifier of the class in which the classmates are enrolled
     * @param academicYear the unique identifier of the academic year during which the classmates are enrolled
     * @param pageable a Pageable object to define the pagination and sorting of the results
     * @return a Page of EnrolledStudent objects containing the classmates' details for the specified class and academic year
     */
    @Query(value = """
            select new com.edusyspro.api.dto.custom.EnrolledStudent(e.id, e.student.id, e.student.personalInfo, e.academicYear,
            e.enrollmentDate, e.classe.id, e.classe.name, e.classe.category, e.classe.grade.section, e.isArchived, e.classe.monthCost,
            e.student.dadName, e.student.momName) from EnrollmentEntity e where e.student.id <> ?1
            and e.classe.id = ?2 and e.academicYear.id = ?3 order by e.student.personalInfo.lastName asc
   """)
    Page<EnrolledStudent> findStudentClassmateByAcademicYear(UUID studentId, int classeId, UUID academicYear, Pageable pageable);

    /**
     * Retrieves a paginated list of distinct guardian details for enrolled students
     * in a specific school and academic year. Only non-archived enrollments are
     * considered, and the academic year must be marked as current.
     *
     * @param schoolId the unique identifier of the school
     * @param pageable the pagination and sorting information
     * @return a Page containing GuardianEssential objects with details of the guardians
     */
    @Query("""
        select distinct new com.edusyspro.api.dto.custom.GuardianEssential(e.student.guardian.id, e.student.guardian.personalInfo,
        e.student.guardian.jobTitle, e.student.guardian.company, e.student.guardian.createdAt, e.student.guardian.modifyAt)
        from EnrollmentEntity e where e.academicYear.school.id = ?1 and e.academicYear.current = true and e.isArchived = false
    """)
    Page<GuardianEssential> findEnrolledStudentGuardian(UUID schoolId, Pageable pageable);

    /**
     * Retrieves a paginated list of enrolled students for a given class and academic year.
     *
     * @param classeId the unique identifier of the class for which to retrieve enrolled students
     * @param academicYear the unique identifier of the academic year for the enrollment records
     * @param pageable the pagination and sorting information for the resulting list of students
     * @return a paginated list of enrolled students matching the specified class and academic year
     */
    @Query(value = """
        select new com.edusyspro.api.dto.custom.EnrolledStudent(e.id, e.student.id, e.student.personalInfo, e.academicYear,
            e.enrollmentDate, e.classe.id, e.classe.name, e.classe.category, e.classe.grade.section, e.isArchived, e.classe.monthCost,
            e.student.dadName, e.student.momName) from EnrollmentEntity e where e.classe.id = ?1
        and e.academicYear.id = ?2
   """)
    Page<EnrolledStudent> getEnrolledStudentsByClassId(int classeId, UUID academicYear, Pageable pageable);

    /**
     * Retrieves a list of enrolled students for a specific class and academic year.
     *
     * @param classeId the ID of the class for which the enrolled students are being retrieved.
     * @param academicYear the UUID representing the academic year for which the enrolled students are being retrieved.
     * @return a list of EnrolledStudentBasic objects containing basic details about the enrolled students.
     */
    @Query(value = """
        select new com.edusyspro.api.dto.custom.EnrolledStudentBasic(e.id, e.student.id, e.academicYear.id, e.academicYear.years, e.classe.id, e.classe.name, e.classe.grade.section,
        e.classe.monthCost, e.student.personalInfo.id, e.student.personalInfo.firstName, e.student.personalInfo.lastName,
        e.student.personalInfo.image, e.student.personalInfo.reference, e.isArchived) from EnrollmentEntity e where e.classe.id = ?1
        and e.academicYear.id = ?2
   """)
    List<EnrolledStudentBasic> getEnrolledStudentsByClassId(int classeId, UUID academicYear);

    /**
     * Retrieves a list of enrolled students based on the provided class ID, academic year, and search criteria.
     *
     * @param classeId the identifier of the class for which enrolled students need to be retrieved
     * @param academicYear the unique identifier of the academic year
     * @param searchName the search keyword applied to filter by student's last name or first name, case-insensitive
     * @return a list of enrolled students matching the specified criteria, ordered by last name in ascending order
     */
    @Query(value = """
        select new com.edusyspro.api.dto.custom.EnrolledStudent(e.id, e.student.id, e.student.personalInfo, e.academicYear,
        e.enrollmentDate, e.classe.id, e.classe.name, e.classe.category, e.classe.grade.section, e.isArchived, e.classe.monthCost,
        e.student.dadName, e.student.momName) from EnrollmentEntity e where e.classe.id = ?1
        and e.academicYear.id = ?2 and (lower(e.student.personalInfo.lastName) like lower(?3) or lower(e.student.personalInfo.firstName) like lower(?3))
      order by e.student.personalInfo.lastName asc
   """)
    List<EnrolledStudent> getEnrolledStudentsByClassIdSearch(int classeId, UUID academicYear, String searchName);

    /**
     * Finds a distinct list of guardians for students who are enrolled in the current academic year
     * of the specified school and are not archived. The search can be filtered by the guardian's last name or first name.
     * The results are ordered by the guardian's last name in ascending order.
     *
     * @param schoolId the unique identifier of the school whose enrolled students' guardians are to be retrieved
     * @param lastName a string used to filter guardians by their last name or first name (case-insensitive)
     * @return a list of {@code GuardianEssential} objects representing the essential details of the guardians
     */
    @Query("""
        select distinct new com.edusyspro.api.dto.custom.GuardianEssential(e.student.guardian.id, e.student.guardian.personalInfo,
        e.student.guardian.jobTitle, e.student.guardian.company, e.student.guardian.createdAt, e.student.guardian.modifyAt)
        from EnrollmentEntity e where e.academicYear.school.id = ?1 and e.academicYear.current = true and e.isArchived = false
        and (lower(e.student.guardian.personalInfo.lastName) like lower(?2) or lower(e.student.guardian.personalInfo.firstName) like lower(?2))
        order by e.student.guardian.personalInfo.lastName asc
    """)
    List<GuardianEssential> findEnrolledStudentGuardian(UUID schoolId, String lastName);

    /**
     * Counts all students by class and academic year.
     *
     * @param classeId the ID of the class for which students are being counted
     * @param academicYearId the ID of the academic year for which students are being counted
     * @return a list of objects containing gender and birthdate information for the students
     */
    @Query("""
        select e.student.personalInfo.gender, e.student.personalInfo.birthDate from EnrollmentEntity e
        where e.classe.id = ?1 and e.academicYear.id = ?2
    """)
    List<Object[]> countAllStudentsByClasseAndAcademicYear(int classeId, UUID academicYearId);

    /**
     * Counts all students in multiple classes for a specific academic year.
     * The query retrieves gender and birthdate information for students
     * enrolled in the specified classes during the provided academic year.
     *
     * @param classeId a list of class IDs to filter the students
     * @param academicYear the unique identifier of the academic year
     * @return a list of objects, where each object array contains the gender and
     *         birthdate of a student
     */
    @Query("select e.student.personalInfo.gender, e.student.personalInfo.birthDate from EnrollmentEntity e where e.classe.id in (?1) and e.academicYear.id = ?2")
    List<Object[]> countAllStudentInMultipleClasses(List<Integer> classeId, UUID academicYear);

    /**
     * Retrieves the count of all students grouped by academic year for a specified class.
     *
     * @param classeId the unique identifier of the class for which the student count is to be retrieved
     * @return a list of objects where each object contains the academic year and the corresponding count of students
     */
    //TODO getting all the classe student count by academic year
    @Query("select e.academicYear.years, count(e.id) from EnrollmentEntity e where e.classe.id = ?1 group by e.academicYear.id")
    List<Object> countAllStudentsByClassByAcademicYear(int classeId);

    /**
     * Retrieves a list of student information, including gender and birthdate,
     * for all students enrolled in the current academic year for a specific school.
     *
     * @param schoolId the unique identifier of the school for which students need to be counted.
     * @return a list of objects where each object contains the gender and birthdate
     *         of a student who is enrolled in the current academic year of the specified school.
     */
    @Query("select e.student.personalInfo.gender, e.student.personalInfo.birthDate from EnrollmentEntity e where e.academicYear.school.id = ?1 and e.academicYear.current = true")
    List<Object[]> countAllStudents(UUID schoolId);

    @Modifying
    @Transactional
    @Query("UPDATE EnrollmentEntity e SET e.isArchived = true WHERE e.isArchived = false and e.academicYear.endDate <= :now")
    int archiveByAcademicYearEnd(@Param("now")LocalDate now);
}
