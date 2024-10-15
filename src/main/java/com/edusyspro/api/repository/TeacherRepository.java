package com.edusyspro.api.repository;

import com.edusyspro.api.model.Teacher;
import com.edusyspro.api.model.TeacherClassCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    @Query("select t from Teacher t where t.school.id = ?1")
    Page<Teacher> findAllTeachers(UUID schoolId, Pageable pageable);

    @Query("select t from Teacher t where t.school.id = ?1 and (lower(t.lastName) like lower(?2) or lower(t.firstName) like lower(?2)) order by t.lastName asc")
    List<Teacher> findAllTeachers(UUID schoolId, String lastname);

    boolean existsByEmailIdAndSchoolId(String emailId, UUID schoolId);

    @Query("select t from TeacherClassCourse t where t.classe.id = ?1 and t.course.id = ?2 and t.teacher.school.id = ?3")
    List<TeacherClassCourse> findTeacherByCourseAndClasse(int classeId, int courseId, UUID schoolId);

    @Query("select t from TeacherClassCourse t where t.classe.id = ?1 and t.teacher.school.id = ?2")
    List<TeacherClassCourse> findTeacherByClasse(int classeId, UUID schoolId);

    @Query("select t from TeacherClassCourse t where t.teacher.id = ?1")
    List<TeacherClassCourse> findTeacherByClassesAndCourses(UUID schoolId);
}
