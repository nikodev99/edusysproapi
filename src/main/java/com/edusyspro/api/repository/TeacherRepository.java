package com.edusyspro.api.repository;

import com.edusyspro.api.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    @Query("select t from Teacher t join t.classes c where c.name = ?1")
    Teacher getTeacherByClassesName(String className);

    @Query("select t from Teacher t join t.classes c join t.courses o where c.name = ?1 and o.id = ?2")
    Teacher getTeacherByClassesNameAndCourseId(String className, int courseId);
}
