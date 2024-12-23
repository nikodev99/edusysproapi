package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.AssignmentEssential;
import com.edusyspro.api.model.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    @Query("""
        select new com.edusyspro.api.dto.custom.AssignmentEssential(a.id, a.semester.semestre.semesterName, a.exam.examType,
        a.preparedBy, a.classeEntity.name, a.classeEntity.grade.section, a.subject.course, a.subject.abbr, a.examName, a.examDate,
        a.startTime, a.endTime, a.passed) from Assignment a where a.preparedBy.id = ?1 and a.passed = false and
        a.semester.semestre.academicYear.current = true order by a.examDate
    """)
    Page<AssignmentEssential> findAssignmentsByTeacher(Long teacherId, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.AssignmentEssential(a.id, a.semester.semestre.semesterName, a.exam.examType,
        a.preparedBy, a.classeEntity.name, a.classeEntity.grade.section, a.subject.course, a.subject.abbr, a.examName, a.examDate,
        a.startTime, a.endTime, a.passed) from Assignment a where a.preparedBy.id = ?1 and a.classeEntity.id = ?2 and a.subject.id = ?3
        and a.semester.semestre.academicYear.current = true order by a.examDate
    """)
    Page<AssignmentEssential> findAllAssignmentsByTeacher(Long teacherId, int classId, int courseId, Pageable pageable);

}
