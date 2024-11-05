package com.edusyspro.api.repository;

import com.edusyspro.api.dto.ClassBasicValue;
import com.edusyspro.api.dto.CourseBasicValue;
import com.edusyspro.api.dto.TeacherEssential;
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
        SELECT new com.edusyspro.api.dto.TeacherEssential(t.id, t.firstName, t.lastName, t.maidenName, t.status, t.birthDate,
        t.cityOfBirth, t.nationality,t.gender, t.address, t.emailId, t.telephone, t.hireDate, t.salaryByHour, t.school.id,
        t.school.name, t.createdAt, t.modifyAt) FROM Teacher t WHERE t.school.id = ?1
    """)
    Page<TeacherEssential> findAllBySchoolId(UUID schoolId, Pageable pageable);

    @Query("select new com.edusyspro.api.dto.ClassBasicValue(c.id, c.name, c.category, c.grade.section) from Teacher t " +
            "join t.aClasses c where t.id = ?1 and t.school.id = ?2")
    List<ClassBasicValue> findTeacherClasses(UUID teacherId, UUID schoolId);

    @Query("select new com.edusyspro.api.dto.CourseBasicValue(c.id, c.course, c.abbr) from Teacher t join t.courses c where t.id = ?1 and t.school.id = ?2")
    List<CourseBasicValue> findTeacherCourses(UUID teacherId, UUID schoolId);

    @Query("select t from Teacher t where t.school.id = ?1 and (lower(t.lastName) like lower(?2) or lower(t.firstName) like lower(?2)) order by t.lastName asc")
    List<Teacher> findAllBySchoolId(UUID schoolId, String lastname);

    Optional<Teacher> findTeacherByIdAndSchoolId(UUID id, UUID schoolId);

    boolean existsByEmailIdAndSchoolId(String emailId, UUID schoolId);

    @Query("select count(s.id) from Teacher t join t.aClasses c join c.students s where t.id = :teacherId and s.academicYear.current = true")
    Long countTeacherStudents(@Param("teacherId") UUID teacherId);
}
