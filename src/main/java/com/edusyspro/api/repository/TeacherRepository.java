package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.*;
import com.edusyspro.api.model.Teacher;
import com.edusyspro.api.model.enums.Section;
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
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.TeacherEssential(
            t.id, t.personalInfo, t.hireDate, t.salaryByHour, s.id, s.name, t.createdAt, t.modifyAt
        ) FROM Teacher t JOIN t.school s WHERE s.id = ?1
    """)
    Page<TeacherEssential> findAllBySchoolId(UUID schoolId, Pageable pageable);

    @Query("select new com.edusyspro.api.dto.custom.ClassBasicValue(c.id, c.name, c.category, c.grade.section) from Teacher t " +
            "join t.aClasses c join t.school s where t.id = ?1 and s.id = ?2")
    List<ClassBasicValue> findTeacherClasses(UUID teacherId, UUID schoolId);

    @Query("select new com.edusyspro.api.dto.custom.CourseBasicValue(c.id, c.course, c.abbr) from Teacher t join t.courses c join t.school s where t.id = ?1 and s.id = ?2")
    List<CourseBasicValue> findTeacherCourses(UUID teacherId, UUID schoolId);

    @Query("""
        select new com.edusyspro.api.dto.custom.CourseEssential(
            c.id, c.course, c.abbr, c.department.id, c.department.name, c.department.code, c.department.purpose,
            c.department.boss.d_boss.id, c.department.boss.current, c.department.boss.d_boss.firstName,
            c.department.boss.d_boss.lastName, c.department.boss.startPeriod, c.department.boss.endPeriod, c.createdAt
        ) from Teacher t join t.courses c where t.id = ?1
    """)
    List<CourseEssential> findTeacherEssentialCourses(UUID teacherId);

    @Query("select new com.edusyspro.api.dto.custom.TeacherEssential(t.id, t.personalInfo, t.hireDate, t.salaryByHour, " +
        "s.id, s.name, t.createdAt, t.modifyAt) from Teacher t join t.school s where s.id = ?1 " +
        "and (lower(t.personalInfo.lastName) like lower(?2) or lower(t.personalInfo.firstName) like lower(?2)) " +
        "order by t.personalInfo.lastName asc")
    List<TeacherEssential> findAllBySchoolId(UUID schoolId, String lastname);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.TeacherEssential(
            t.id, t.personalInfo, t.hireDate, t.salaryByHour, s.id, s.name, t.createdAt, t.modifyAt
        ) FROM Teacher t JOIN t.school s WHERE t.id = ?1 AND s.id = ?2
    """)
    Optional<TeacherEssential> findTeacherById(UUID id, UUID schoolId);

    @Query("""
        SELECT DISTINCT new com.edusyspro.api.dto.custom.TeacherClasseCourse(
            t.id, t.personalInfo, t.hireDate, c.id, c.name, s.course.id, s.course.course
        ) FROM Teacher t join t.aClasses c LEFT JOIN Schedule s WHERE c.id = ?1
    """)
    List<TeacherEssential> findAllClasseTeachers(int classId);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.TeacherBasic(
            t.id, t.personalInfo.id, t.personalInfo.firstName, t.personalInfo.lastName, t.personalInfo.image, t.personalInfo.gender,
            t.personalInfo.emailId, t.personalInfo.birthDate, t.personalInfo.nationality, t.personalInfo.birthCity, t.personalInfo.telephone, c.id, c.name
        ) FROM Teacher t join t.aClasses c WHERE c.id = ?1 and c.grade.section = ?2
    """)
    List<TeacherBasic> findAllTeacherBasicValue(int classId, Section section);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.TeacherBasic(
            t.id, t.personalInfo.id, t.personalInfo.firstName, t.personalInfo.lastName, t.personalInfo.image, t.personalInfo.gender,
            t.personalInfo.emailId, t.personalInfo.birthDate, t.personalInfo.nationality, t.personalInfo.birthCity, t.personalInfo.telephone, c.id, c.name
        ) FROM Teacher t join t.aClasses c WHERE t.personalInfo.id = :teacherId and c.id = :classeId
    """)
    Optional<TeacherBasic> findTeacherBasicValue(@Param("teacherId") long teacherId, @Param("classeId") int classId);


    @Query("select t.personalInfo.gender, t.personalInfo.birthDate from Teacher t join t.school s where s.id = ?1")
    List<Object[]> countAllTeachers (UUID schoolId);

    boolean existsByPersonalInfoEmailIdAndSchoolId(String emailId, UUID schoolId);

    @Query("select count(s.id) from Teacher t join t.aClasses c join c.students s where t.id = :teacherId and s.academicYear.current = true")
    Long countTeacherStudents(@Param("teacherId") UUID teacherId);

    @Query("select c.name, count(s.id) from Teacher t join t.aClasses c join c.students s where t.id = ?1 group by c.id")
    List<Object[]> countAllTeacherStudentsByClasses(UUID teacherId);

    //TESTING
    @Query("select t from Teacher t join t.aClasses c join t.courses co where c.id = :classId and co.id = :courseId")
    Optional<Teacher> findTeacherByClasseIdAndCourseId(@Param("classId") int classId, @Param("courseId") int courseId);

    @Query("select t from Teacher t join t.aClasses c where c.id = :classId")
    Optional<Teacher> findTeacherByClasseId(@Param("classId") int classId);
}
