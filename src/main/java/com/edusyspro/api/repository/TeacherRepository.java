package com.edusyspro.api.repository;

import com.edusyspro.api.dto.TeacherEssential;
import com.edusyspro.api.model.Course;
import com.edusyspro.api.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    @Query("""
        select new com.edusyspro.api.dto.TeacherEssential(t.id, t.firstName, t.lastName, t.maidenName, t.status, t.birthDate, t.gender,
        t.address, t.emailId, t.telephone, t.hireDate, t.salaryByHour, t.school.id, t.school.name, t.createdAt, t.modifyAt) \
        from Teacher t where t.school.id = ?1
    """)
    Page<TeacherEssential> findAllTeachers(UUID schoolId, Pageable pageable);

    @Query("select t.courses from Teacher t where t.id = ?1 and t.school.id = ?2")
    List<Course> findTeacherCourses(UUID teacherId, UUID schoolId);

    @Query("select t from Teacher t join t.aClasses c where c.name = ?1")
    Teacher getTeacherByClassesName(String className);

    @Query("select t from Teacher t join t.aClasses c join t.courses o where c.name = ?1 and o.id = ?2")
    Teacher getTeacherByClassesNameAndCourseId(String className, int courseId);
}
