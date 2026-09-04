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
        select distinct new com.edusyspro.api.dto.custom.ExamEssential(e.id, e.examType, e.startDate, e.endDate, e.academicYear.id,
        e.academicYear.years, e.academicYear.current) from Exam e where e.academicYear.school.id = ?1 and e.academicYear.id = ?2
        order by e.startDate
    """)
    List<ExamEssential> findAllSchoolExams(UUID schoolId, UUID academicYearId);

    @Query("""
        select distinct new com.edusyspro.api.dto.custom.ExamEssential(e.id, e.examType, e.startDate, e.endDate, e.academicYear.id,
        e.academicYear.years, e.academicYear.current) from Exam e where e.academicYear.school.id = ?1
    """)
    List<ExamEssential> findAllSchoolExams(UUID schoolId);

    /**
     * Retrieves a list of exam essentials for a specific class and academic year, based on their assignments.
     *
     * @param classeId the ID of the class entity for which exams are being retrieved
     * @param academicYear the UUID of the academic year during which the exams are scheduled
     * @return a list of {@code ExamEssential} objects representing the exams associated with the given class
     *         and academic year, ordered by exam ID
     */
    @Query("""
        select distinct new com.edusyspro.api.dto.custom.ExamEssential(e.id, e.examType, e.startDate, e.endDate, e.academicYear.id,
        e.academicYear.years, e.academicYear.current) from Exam e join e.assignments a where a.classeEntity.id = ?1 and e.academicYear.id = ?2
        order by e.id
    """)
    List<ExamEssential> findClasseExamsAssignments(Integer classeId, UUID academicYear);

    @Query("""
        select new com.edusyspro.api.dto.custom.ExamEssential(e.id, e.examType, e.startDate, e.endDate, e.academicYear.id,
        e.academicYear.years, e.academicYear.current) from Exam e where e.id = ?1 order by e.id
    """)
    Optional<ExamEssential> findExamById(int examId);

    @Query("""
        select new com.edusyspro.api.dto.custom.ExamEssential(e.id, e.examType, e.startDate, e.endDate, e.academicYear.id,
        e.academicYear.years, e.academicYear.current) from Exam e where e.id = ?1
    """)
    Optional<ExamEssential> findClasseExamsAssignments(Integer examId, Integer classeId, UUID academicYearId);

    @Query("select count(a.id) from Assignment a where a.exam.id = ?1")
    long countAssignmentByExam(Integer examId);

    @Query("select e.id from Exam e where e.examType.id = ?1 and e.academicYear.id = ?2")
    Optional<Integer> findExistedExam(int examTypeId, UUID academicYearId);

    @Query("select e.id from Exam e where e.examType.id = ?1")
    List<Integer> findExistedExam(int examTypeId);

    boolean existsByExamTypeIdAndAcademicYearIdAndIdNot(int examTypeId, UUID academicYearId, int id);
}
