package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.*;
import com.edusyspro.api.model.Teacher;
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
            t.id, t.personalInfo, t.hireDate, t.salaryByHour, t.school.id, t.school.name, t.createdAt, t.modifyAt
        ) FROM Teacher t WHERE t.school.id = ?1
    """)
    Page<TeacherEssential> findAllBySchoolId(UUID schoolId, Pageable pageable);

    @Query("select new com.edusyspro.api.dto.custom.ClassBasicValue(c.id, c.name, c.category, c.grade.section) from Teacher t " +
            "join t.aClasses c where t.id = ?1 and t.school.id = ?2")
    List<ClassBasicValue> findTeacherClasses(UUID teacherId, UUID schoolId);

    @Query("select new com.edusyspro.api.dto.custom.CourseBasicValue(c.id, c.course, c.abbr) from Teacher t join t.courses c where t.id = ?1 and t.school.id = ?2")
    List<CourseBasicValue> findTeacherCourses(UUID teacherId, UUID schoolId);

    @Query("""
        select new com.edusyspro.api.dto.custom.CourseEssential(
            c.id, c.course, c.abbr, c.department.id, c.department.name, c.department.code, c.department.purpose,
            c.department.boss.d_boss.id, c.department.boss.current, c.department.boss.d_boss.personalInfo.firstName,
            c.department.boss.d_boss.personalInfo.lastName, c.department.boss.startPeriod, c.department.boss.endPeriod, c.createdAt
        ) from Teacher t join t.courses c where t.id = ?1
    """)
    List<CourseEssential> findTeacherEssentialCourses(UUID teacherId);

    @Query("select new com.edusyspro.api.dto.custom.TeacherEssential(t.id, t.personalInfo, t.hireDate, t.salaryByHour, " +
        "t.school.id, t.school.name, t.createdAt, t.modifyAt) from Teacher t where t.school.id = ?1 " +
        "and (lower(t.personalInfo.lastName) like lower(?2) or lower(t.personalInfo.firstName) like lower(?2)) " +
        "order by t.personalInfo.lastName asc")
    List<TeacherEssential> findAllBySchoolId(UUID schoolId, String lastname);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.TeacherEssential(
            t.id, t.personalInfo, t.hireDate, t.salaryByHour, t.school.id, t.school.name, t.createdAt, t.modifyAt
        ) FROM Teacher t WHERE t.id = ?1 AND t.school.id = ?2
    """)
    Optional<TeacherEssential> findTeacherById(UUID id, UUID schoolId);

    @Query("""
        SELECT DISTINCT new com.edusyspro.api.dto.custom.TeacherClasseCourse(
            t.id, t.personalInfo, t.hireDate, c.id, c.name, s.course.id, s.course.course
        ) FROM Teacher t join t.aClasses c LEFT JOIN Schedule s WHERE c.id = ?1
    """)
    List<TeacherEssential> findAllClasseTeachers(int classId);

    @Query("select t.personalInfo.gender, t.personalInfo.birthDate from Teacher t where t.school.id = ?1")
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
