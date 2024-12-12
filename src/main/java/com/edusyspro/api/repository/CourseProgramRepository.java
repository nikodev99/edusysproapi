package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.CourseProgramBasic;
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

}
