package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.*;
import com.edusyspro.api.model.Teacher;
import com.edusyspro.api.model.enums.AffiliationStatus;
import com.edusyspro.api.model.enums.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.TeacherEssential(
            t.id, t.personalInfo, s.contract.id, s.contract.role, s.contract.jobTitle, s.contract.startDate, s.contract.salaryByHour,
            s.status, s.school.id, s.school.name, t.createdAt, t.modifyAt
        ) FROM Teacher t JOIN t.schoolAffiliations s WHERE s.school.id = ?1 AND s.status = ?2
    """)
    Page<TeacherEssential> findAllBySchoolId(UUID schoolId, AffiliationStatus status, Pageable pageable);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.TeacherEssential(
            t.id, t.personalInfo, s.contract.id, s.contract.role, s.contract.jobTitle, s.contract.startDate, s.contract.salaryByHour,
            s.status, s.school.id, s.school.name, t.createdAt, t.modifyAt
        ) FROM Teacher t JOIN t.schoolAffiliations s WHERE s.school.id = ?1 AND t.id = ?2 AND s.status = ?3
    """)
    Page<TeacherEssential> findAllBySchoolId(UUID schoolId, UUID teacherId, AffiliationStatus status, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.ClassBasicValue(c.id, c.name, c.category, g.section, d.name, d.code) from Teacher t
        join t.schoolAffiliations af join af.aClasses ac join ac.classe c left join c.grade g left join c.department d
        where t.id = ?1 and af.status = ?2 and af.school.id = ?3
    """)
    List<ClassBasicValue> findTeacherClasses(UUID teacherId, AffiliationStatus status, UUID schoolId);

    @Query("""
        select new com.edusyspro.api.dto.custom.CourseBasicValue(c.id, c.course, c.courseType, c.abbr, c.discipline)
        from Teacher t join t.schoolAffiliations ac join ac.courses co join co.course c where t.id = ?1 AND ac.status = ?2 and ac.school.id = ?3
    """)
    List<CourseBasicValue> findTeacherCourses(UUID teacherId, AffiliationStatus status, UUID schoolId);

    @Query("""
        select new com.edusyspro.api.dto.custom.CourseEssential(
            c.id, c.course, c.courseType, c.abbr, c.discipline, d.id, d.name, d.code, d.purpose, b.d_boss.id, b.current, i.firstName,
            i.lastName, b.startPeriod, b.endPeriod, c.createdAt
        ) from Teacher t join t.schoolAffiliations cs join cs.courses co join co.course c left join c.department d left join d.boss b left join b.d_boss i
        where t.id = ?1 AND cs.status = ?2 and cs.school.id = ?3
    """)
    List<CourseEssential> findTeacherEssentialCourses(UUID teacherId, AffiliationStatus status, UUID schoolId);

    @Query("""
        select new com.edusyspro.api.dto.custom.TeacherEssential(t.id, t.personalInfo, s.contract.id, s.contract.role,
        s.contract.jobTitle, s.contract.startDate, s.contract.salaryByHour, s.status, s.school.id, s.school.name, t.createdAt, t.modifyAt)
        from Teacher t join t.schoolAffiliations s where s.school.id = ?1 and (lower(t.personalInfo.lastName)
        like lower(?2) or lower(t.personalInfo.firstName) like lower(?2)) and s.status = ?3 order by t.personalInfo.lastName asc
    """)
    List<TeacherEssential> findAllBySchoolId(UUID schoolId, String lastname, AffiliationStatus status);

    @Query("""
        select new com.edusyspro.api.dto.custom.TeacherEssential(t.id, t.personalInfo, s.contract.id, s.contract.role,
        s.contract.jobTitle, s.contract.startDate, s.contract.salaryByHour, s.status, s.school.id, s.school.name, t.createdAt, t.modifyAt)
        from Teacher t join t.schoolAffiliations s where s.school.id <> ?1 and (lower(concat(t.personalInfo.lastName, ' ', t.personalInfo.firstName))
        like lower(?2)) or (lower(t.personalInfo.reference) like lower(?2))
    """)
    Page<TeacherEssential> findSearchedTeacher(UUID schoolId, String searchInput, Pageable pageable);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.TeacherEssential(
            t.id, t.personalInfo, s.contract.id, s.contract.role, s.contract.jobTitle, s.contract.startDate, s.contract.salaryByHour,
            s.status, s.school.id, s.school.name, t.createdAt, t.modifyAt
        ) FROM Teacher t JOIN t.schoolAffiliations s WHERE t.id = ?1 AND s.school.id = ?2
    """)
    Optional<TeacherEssential> findTeacherById(UUID id, UUID schoolId);

    @Query("""
        SELECT DISTINCT new com.edusyspro.api.dto.custom.TeacherClasseCourse(
            t.id, t.personalInfo, a.contract.id, a.contract.startDate, c.id, c.name, s.course.id, s.course.course
        ) FROM Teacher t join t.schoolAffiliations a join a.aClasses ac join ac.classe c left join c.schedule s WHERE a.status = ?1 and c.id = ?2
    """)
    List<TeacherClasseCourse> findAllClasseTeachers(AffiliationStatus status, int classId);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.TeacherBasic(
            t.id, t.personalInfo.id, t.personalInfo.firstName, t.personalInfo.lastName, t.personalInfo.image, t.personalInfo.gender,
            t.personalInfo.emailId, t.personalInfo.birthDate, t.personalInfo.nationality, t.personalInfo.birthCity, t.personalInfo.telephone, c.id, c.name
        ) FROM Teacher t join t.schoolAffiliations tc join tc.aClasses ac join ac.classe c left join c.grade g WHERE c.id = ?1 and g.section = ?2 and tc.status = ?3
    """)
    List<TeacherBasic> findAllTeacherBySection(int classId, Section section, AffiliationStatus status);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.TeacherBasic(
            t.id, t.personalInfo.id, t.personalInfo.firstName, t.personalInfo.lastName, t.personalInfo.image, t.personalInfo.gender,
            t.personalInfo.emailId, t.personalInfo.birthDate, t.personalInfo.nationality, t.personalInfo.birthCity, t.personalInfo.telephone, c.id, c.name
        ) FROM Teacher t join t.schoolAffiliations tc join tc.aClasses ca join ca.classe c WHERE t.personalInfo.id = :teacherId and c.id = :classeId
    """)
    Optional<TeacherBasic> findTeacherByClasse(@Param("teacherId") long teacherId, @Param("classeId") int classId);

    @Query("select new com.edusyspro.api.dto.custom.IndividualBasic(p.id, p.firstName, p.lastName, p.image, p.reference) from Teacher t join t.personalInfo p where t.id = ?1")
    Optional<IndividualBasic> findTeacherPersonalInfo(UUID teacherId);

    @Query("select t.personalInfo.gender, t.personalInfo.birthDate from Teacher t join t.schoolAffiliations s where s.school.id = ?1")
    List<Object[]> countAllTeachers (UUID schoolId);

    @Query("select t.id from Teacher t join t.schoolAffiliations s where t.personalInfo.emailId = ?1 and s.school.id = ?2")
    Optional<Object> findByPersonalInfoEmailIdAndSchoolId(String emailId, UUID schoolId);

    @Query("select count(s.id) from Teacher t join t.schoolAffiliations ts join ts.aClasses ac join ac.classe c join c.students s where t.id = :teacherId and s.academicYear.id = :academicYearId")
    Long countTeacherStudents(@Param("teacherId") UUID teacherId, @Param("academicYearId") UUID academicYearId);

    @Query("select c.name, count(s.id) from Teacher t join t.schoolAffiliations ts join ts.aClasses ac join ac.classe c join ac.affiliation sc join c.students s where t.id = ?1 and sc.school.id = ?2 and s.isArchived = false group by c.id")
    List<Object[]> countAllTeacherStudentsByClasses(UUID teacherId, UUID schoolId);

    @Query("select distinct tc.id from Teacher t join t.schoolAffiliations tc where t.id = :teacherId and tc.school.id = :schoolId")
    Optional<Long> findSchoolToAffiliate(@Param("teacherId") UUID teacherId, @Param("schoolId") UUID schoolId);

    //UPDATE CLASSES:
    @Query(value = "select class_id from teacher_classes where teacher_id = :teacherId", nativeQuery = true)
    List<Integer> findAssignedClassIds(@Param("teacherId") UUID teacherId);

    @Modifying
    @Query(value = "insert into teacher_classes (teacher_id, class_id, affiliation_id) values (:teacherId, :classId, :affiliation)", nativeQuery = true)
    int linkClass(@Param("teacherId") UUID teacherId, @Param("classId") Integer classId, @Param("affiliation") long affiliation);

    @Modifying
    @Query(value = "delete from teacher_classes where teacher_id = :teacherId and class_id in :classIds", nativeQuery = true)
    int unlinkClasses(@Param("teacherId") UUID teacherId, @Param("classIds") List<Integer> classIds);

    //UPDATE COURSES:
    @Query(value = "select course_id from teacher_courses where teacher_id = :teacherId", nativeQuery = true)
    List<Integer> findAssignedCourseIds(@Param("teacherId") UUID teacherId);

    @Modifying
    @Query(value = "insert into teacher_courses (teacher_id, course_id, affiliation_id) values (:teacherId, :courseId, :affiliation)", nativeQuery = true)
    int linkCourse(@Param("teacherId") UUID teacherId, @Param("courseId") Integer courseId, @Param("affiliation") long affiliation);

    @Modifying
    @Query(value = "delete from teacher_courses where teacher_id = :teacherId and course_id in :courseIds", nativeQuery = true)
    int unlinkCourses(@Param("teacherId") UUID teacherId, @Param("courseIds") List<Integer> courseIds);

    //TESTING
    @Query("select t from Teacher t join t.schoolAffiliations ts join ts.aClasses ca join ts.courses tcs join ca.classe c join tcs.course co where c.id = :classId and co.id = :courseId")
    Optional<Teacher> findTeacherByClasseIdAndCourseId(@Param("classId") int classId, @Param("courseId") int courseId);

    @Query("select t from Teacher t join t.schoolAffiliations ts join ts.aClasses c where c.id = :classId")
    Optional<Teacher> findTeacherByClasseId(@Param("classId") int classId);
}
