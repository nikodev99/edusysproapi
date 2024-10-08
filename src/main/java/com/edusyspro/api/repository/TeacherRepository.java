package com.edusyspro.api.repository;

import com.edusyspro.api.dto.TeacherEssential;
import com.edusyspro.api.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    @Query("""
        SELECT com.edusyspro.api.dto.TeacherEssential() FROM Teacher t
    """)
    List<TeacherEssential> findAllTeachers();

    @Query("select t from Teacher t join t.aClasses c where c.name = ?1")
    Teacher getTeacherByClassesName(String className);

    @Query("select t from Teacher t join t.aClasses c join t.courses o where c.name = ?1 and o.id = ?2")
    Teacher getTeacherByClassesNameAndCourseId(String className, int courseId);
}
