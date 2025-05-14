package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.ExamEssential;
import com.edusyspro.api.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    @Query("""
        select distinct new com.edusyspro.api.dto.custom.ExamEssential(e.id, e.examType, e.startDate, e.endDate)
        from Exam e where e.academicYear.school.id = ?1 and e.academicYear.id = ?2
        order by e.startDate
    """)
    List<ExamEssential> findAllSchoolExams(UUID schoolId, UUID academicYearId);

    @Query("""
        select distinct new com.edusyspro.api.dto.custom.ExamEssential(e.id, e.examType, e.startDate, e.endDate)
        from Exam e join e.assignments a where a.classeEntity.id = ?1 and e.academicYear.id = ?2
        order by e.id
    """)
    List<ExamEssential> findClasseExamsAssignments(Integer classeId, UUID academicYear);

    @Query("""
        select new com.edusyspro.api.dto.custom.ExamEssential(e.id, e.examType, e.startDate, e.endDate) from Exam e
        where e.id = ?1 order by e.id
    """)
    Optional<ExamEssential> findExamById(Long examId);

    @Query("""
        select new com.edusyspro.api.dto.custom.ExamEssential(e.id, e.examType, e.startDate, e.endDate)
        from Exam e where e.id = ?1
    """)
    Optional<ExamEssential> findClasseExamsAssignments(Long examId, Integer classeId, UUID academicYearId);

}
