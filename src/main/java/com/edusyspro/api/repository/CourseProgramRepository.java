package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.CourseProgramBasic;
import com.edusyspro.api.dto.custom.CourseProgramEssential;
import com.edusyspro.api.model.CourseProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseProgramRepository extends JpaRepository<CourseProgram, Long> {
    @Query("""
        select new com.edusyspro.api.dto.custom.CourseProgramBasic(cp.id, cp.topic, cp.updateDate, cp.classe.name, cp.active)
        from CourseProgram cp where cp.teacher.id = ?1 and cp.passed = false order by cp.createAt asc limit 10
    """)
    List<CourseProgramBasic> findBasicTeacherCoursePrograms(UUID teacherId);

    @Query("""
        select new com.edusyspro.api.dto.custom.CourseProgramEssential(cp.id, cp.topic, cp.purpose, cp.description, cp.active,
        cp.passed, cp.updateDate, cp.semester.semesterName, cp.semester.academicYear.years, cp.course.course, cp.course.abbr,
        cp.classe.name, cp.classe.grade.section, cp.teacher.personalInfo) from CourseProgram cp where cp.teacher.id = ?1
        and cp.classe.id = ?2 and (?3 is null or cp.course.id = ?3)
    """)
    List<CourseProgramEssential> findAllByTeacherId(UUID teacherId, int classeId, int courseId);

    @Query("""
        select new com.edusyspro.api.dto.custom.CourseProgramEssential(cp.id, cp.topic, cp.purpose, cp.description, cp.active,
        cp.passed, cp.updateDate, cp.semester.semesterName, cp.semester.academicYear.years, cp.course.course, cp.course.abbr,
        cp.classe.name, cp.classe.grade.section, cp.teacher.personalInfo) from CourseProgram cp where cp.teacher.id = ?1
        and cp.classe.id = ?2
    """)
    List<CourseProgramEssential> findAllByTeacherId(UUID teacherId, int classeId);

}
