package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.AssignmentEssential;
import com.edusyspro.api.dto.custom.AssignmentExhaustif;
import com.edusyspro.api.dto.custom.AssignmentToExam;
import com.edusyspro.api.model.Assignment;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    @Query("""
        select new com.edusyspro.api.dto.custom.AssignmentEssential(a.id, a.semester,a.exam.examType,
        a.preparedBy.id, a.preparedBy.firstName, a.preparedBy.lastName, a.preparedBy.image,
        a.classeEntity.id, a.classeEntity.name, a.classeEntity.grade.section, a.subject.id, a.subject.course,
        a.subject.abbr, a.examName, a.examDate, a.startTime, a.endTime, a.type, a.passed, a.addedDate, a.updatedDate)
        from Assignment a where a.passed = false and a.semester.academicYear.id = ?1
    """)
    List<AssignmentEssential> findAllNotCompleteAssignments(UUID academicYearId);

    @Query("""
        select new com.edusyspro.api.dto.custom.AssignmentEssential(a.id, a.semester,a.exam.examType,
        a.preparedBy.id, a.preparedBy.firstName, a.preparedBy.lastName, a.preparedBy.image,
        a.classeEntity.id, a.classeEntity.name, a.classeEntity.grade.section, a.subject.id, a.subject.course,
        a.subject.abbr, a.examName, a.examDate, a.startTime, a.endTime, a.type, a.passed, a.addedDate, a.updatedDate)
        from Assignment a where a.passed = false and a.semester.academicYear.id = ?1 and a.preparedBy.id = ?2
    """)
    List<AssignmentEssential> findAllTeacherNotCompleteAssignments(UUID academicYearId, long teacherId);

    @Query("""
        select new com.edusyspro.api.dto.custom.AssignmentEssential(a.id, a.semester,a.exam.examType,
        a.preparedBy.id, a.preparedBy.firstName, a.preparedBy.lastName, a.preparedBy.image,
        a.classeEntity.id, a.classeEntity.name, a.classeEntity.grade.section, a.subject.id, a.subject.course,
        a.subject.abbr, a.examName, a.examDate, a.startTime, a.endTime, a.type, a.passed, a.addedDate, a.updatedDate)
        from Assignment a where a.classeEntity.id = ?1 and a.semester.academicYear.id = ?2
    """)
    List<AssignmentEssential> findAllClasseAssignments(Integer classeId, UUID academicYear);

    @Query("""
        select new com.edusyspro.api.dto.custom.AssignmentToExam(a.id, a.semester, a.semester.academicYear.id,
        a.exam.id, a.preparedBy.id, a.preparedBy.firstName, a.preparedBy.lastName,
        a.preparedBy.image, a.classeEntity.id, a.classeEntity.name, a.classeEntity.grade.section, a.subject.id, a.subject.course,
        a.subject.abbr, a.examName, a.examDate, a.startTime, a.endTime, a.type, a.passed, a.coefficient)
        from Assignment a where a.classeEntity.id = ?1 and a.semester.academicYear.id = ?2 and a.exam.id = ?3
        and a.passed = true order by a.addedDate asc
    """)
    List<AssignmentToExam> findAllClasseAssignmentsByExam(Integer classeId, UUID academicYear, Long ExamId);

    @Query("""
        select new com.edusyspro.api.dto.custom.AssignmentEssential(a.id, a.semester,a.exam.examType,
        a.preparedBy.id, a.preparedBy.firstName, a.preparedBy.lastName, a.preparedBy.image,
        a.classeEntity.id, a.classeEntity.name, a.classeEntity.grade.section, a.subject.id, a.subject.course,
        a.subject.abbr, a.examName, a.examDate, a.startTime, a.endTime, a.type, a.passed, a.addedDate, a.updatedDate)
        from Assignment a where a.classeEntity.id = ?1 and a.semester.academicYear.id = ?2 and a.subject.id = ?3
    """)
    List<AssignmentEssential> findAllClasseAssignmentsBySubject(Integer classeId, UUID academicYear, Integer subjectId);

    @Query("""
        select new com.edusyspro.api.dto.custom.AssignmentEssential(a.id, a.semester,a.exam.examType,
        a.preparedBy.id, a.preparedBy.firstName, a.preparedBy.lastName, a.preparedBy.image,
        a.classeEntity.id, a.classeEntity.name, a.classeEntity.grade.section, a.subject.id, a.subject.course,
        a.subject.abbr, a.examName, a.examDate, a.startTime, a.endTime, a.type, a.passed, a.addedDate, a.updatedDate)
        from Assignment a where a.subject.id = ?1 and a.semester.academicYear.id = ?2
    """)
    List<AssignmentEssential> findAllSubjectAssignments(int courseId, UUID academicYear);

    @Query("""
        select new com.edusyspro.api.dto.custom.AssignmentEssential(a.id, a.semester,a.exam.examType,
        a.preparedBy.id, a.preparedBy.firstName, a.preparedBy.lastName, a.preparedBy.image,
        a.classeEntity.id, a.classeEntity.name, a.classeEntity.grade.section, a.subject.id, a.subject.course,
        a.subject.abbr, a.examName, a.examDate, a.startTime, a.endTime, a.type, a.passed, a.addedDate, a.updatedDate)
        from Assignment a where a.preparedBy.id = ?1 and a.semester.academicYear.school.id = ?2 and a.passed = false and
        a.semester.academicYear.current = true order by a.examDate desc
    """)
    Page<AssignmentEssential> findAssignmentsByTeacher(Long teacherId, UUID schoolId, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.AssignmentEssential(a.id, a.semester,a.exam.examType,
        a.preparedBy.id, a.preparedBy.firstName, a.preparedBy.lastName, a.preparedBy.image,
        a.classeEntity.id, a.classeEntity.name, a.classeEntity.grade.section, a.subject.id, a.subject.course,
        a.subject.abbr, a.examName, a.examDate, a.startTime, a.endTime, a.type, a.passed, a.addedDate, a.updatedDate)
        from Assignment a where a.preparedBy.id = ?1 and a.semester.academicYear.id = ?2 order by a.examDate desc
    """)
    List<AssignmentEssential> findAssignmentsByTeacher(Long teacherId, UUID academicYear);

    @Query("""
        select new com.edusyspro.api.dto.custom.AssignmentEssential(a.id, a.semester,a.exam.examType,
        a.preparedBy.id, a.preparedBy.firstName, a.preparedBy.lastName, a.preparedBy.image,
        a.classeEntity.id, a.classeEntity.name, a.classeEntity.grade.section, a.subject.id, a.subject.course,
        a.subject.abbr, a.examName, a.examDate, a.startTime, a.endTime, a.type, a.passed, a.addedDate, a.updatedDate)
        from Assignment a where a.preparedBy.id = ?1 and a.semester.academicYear.id = ?2 and a.classeEntity.id = ?3 and a.subject.id = ?4
        order by a.examDate desc
    """)
    List<AssignmentEssential> findAllAssignmentsByTeacherByCourseByClasse(Long teacherId, UUID academicYear, int classId, int courseId);

    @Query("""
        select new com.edusyspro.api.dto.custom.AssignmentEssential(a.id, a.semester,a.exam.examType,
        a.preparedBy.id, a.preparedBy.firstName, a.preparedBy.lastName, a.preparedBy.image,
        a.classeEntity.id, a.classeEntity.name, a.classeEntity.grade.section, a.subject.id, a.subject.course,
        a.subject.abbr, a.examName, a.examDate, a.startTime, a.endTime, a.type, a.passed, a.addedDate, a.updatedDate)
        from Assignment a where a.preparedBy.id = ?1 and a.semester.academicYear.id = ?2 and a.classeEntity.id = ?3
        order by a.examDate desc
    """)
    List<AssignmentEssential> findAllAssignmentsByTeacherByClasse(Long teacherId, UUID schoolId, int classId);

    @Query("""
        select new com.edusyspro.api.dto.custom.AssignmentExhaustif(a.id, a.semester.semesterId, a.exam.id, a.exam.examType, a.exam.startDate,
        a.exam.endDate, a.preparedBy.id, c.id, s.id, s.course, s.abbr, d.code, a.examName, a.examDate, a.startTime, a.endTime,
        a.type, a.passed, a.coefficient, a.addedDate, a.updatedDate) from Assignment a left join a.classeEntity c left join a.subject s
        left join s.department d where a.id = ?1
    """)
    Optional<AssignmentExhaustif> findAssignmentById(Long assignmentId);

    @Modifying
    @Transactional
    @Query("""
        update Assignment a set a.examDate = ?1, a.startTime = ?2, a.endTime = ?3 where a.id = ?4
    """)
    Integer changeAssignmentDateById(LocalDate examDate, LocalTime startTitle, LocalTime endTime, Long assignmentId);

    @Query("select count(a.id) from Assignment a where a.examName = ?1 and a.semester.academicYear.id = ?2 and a.classeEntity.id = ?3")
    Optional<Long> assignmentExists(String examName, UUID academicYear, int classId);

    @Query("select count(a.id) from Assignment a where a.examName = ?1  and a.semester.academicYear.id = ?2 and a.classeEntity.id = ?3 and a.subject.id = ?4")
    Optional<Long> courseAssignmentExists(String examName, UUID academicYear, int classId, int courseId);

}
