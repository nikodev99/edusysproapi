package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.ScoreBasic;
import com.edusyspro.api.dto.custom.ScoreBasicValue;
import com.edusyspro.api.dto.custom.ScoreEssential;
import com.edusyspro.api.model.Score;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("select new com.edusyspro.api.dto.custom.ScoreEssential(s.id, s.assignment.id, s.assignment.examName, s.assignment.examDate, " +
            "s.assignment.startTime, s.assignment.endTime, s.assignment.classeEntity.name, s.assignment.classeEntity.grade.section, " +
            "s.assignment.subject.id, s.assignment.subject.course, s.obtainedMark, s.assignment.coefficient) " +
            "from Score s where s.assignment.semester.semestre.academicYear.current = true and s.studentEntity.id = ?1 order by s.assignment.examDate desc")
    Page<ScoreEssential> findLastFiveScoresByStudent(UUID studentId, Pageable pageable);

    @Query("select new com.edusyspro.api.dto.custom.ScoreEssential(s.id, s.assignment.id, s.assignment.examName, s.assignment.examDate, " +
            "s.assignment.startTime, s.assignment.endTime, s.assignment.classeEntity.name, s.assignment.classeEntity.grade.section, " +
            "s.assignment.subject.id, s.assignment.subject.course, s.obtainedMark, s.assignment.coefficient) " +
            "from Score s where s.assignment.semester.semestre.academicYear.id = ?1 and s.studentEntity.id = ?2 order by s.assignment.examDate desc")
    Page<ScoreEssential> findAllByStudentIdAndAcademicYear(UUID academicYearId, UUID studentId, Pageable pageable);

    @Query("select new com.edusyspro.api.dto.custom.ScoreEssential(s.id, s.assignment.id, s.assignment.examName, s.assignment.examDate, " +
            "s.assignment.startTime, s.assignment.endTime, s.assignment.classeEntity.name, s.assignment.classeEntity.grade.section, " +
            "s.assignment.subject.id, s.assignment.subject.course, s.obtainedMark, s.assignment.coefficient) " +
            "from Score s where s.studentEntity.id = ?1 and s.assignment.subject.id = ?2 and s.assignment.semester.semestre.academicYear.id = ?3 order by s.assignment.examDate desc")
    List<ScoreEssential> findAllByStudentIdAcademicYearAndSubjectId(UUID studentId,  int subjectId, UUID academicYearId);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScoreBasicValue(s.id, st.id, i.firstName, i.lastName, i.image, st.personalInfo.reference,
        s.assignment.classeEntity.name, s.obtainedMark, s.isPresent)
        from Score s join s.studentEntity st join st.personalInfo i where s.assignment.preparedBy.id = ?1 and s.assignment.semester.semestre.academicYear.current = true
    """)
    List<ScoreBasicValue> findAllTeacherMarks(long teacherId);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScoreBasicValue(s.id, st.id, i.firstName, i.lastName, i.image, st.personalInfo.reference,
        s.assignment.classeEntity.name, s.obtainedMark, s.isPresent)
        from Score s join s.studentEntity st join st.personalInfo i where s.assignment.preparedBy.id in ?1 and s.assignment.semester.semestre.academicYear.current = true
    """)
    List<ScoreBasicValue> findAllTeacherMarks(List<Long> teacherId);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScoreBasicValue(s.id, st.id, i.firstName, i.lastName, i.image, st.personalInfo.reference,
        s.assignment.classeEntity.name, s.obtainedMark, s.isPresent)
        from Score s join s.studentEntity st join st.personalInfo i where s.assignment.id = ?1 order by s.obtainedMark desc
    """)
    Page<ScoreBasicValue> findScoresByAssignment(long assignmentId, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScoreBasicValue(s.id, st.id, i.firstName, i.lastName, i.image, st.personalInfo.reference,
        s.assignment.classeEntity.name, s.obtainedMark, s.isPresent)
        from Score s join s.studentEntity st join st.personalInfo i where s.assignment.id = ?1 order by s.obtainedMark desc
    """)
    List<ScoreBasicValue> findScoresByAssignment(Long assignmentId);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScoreBasic(s.id, st.id, s.assignment.id, s.obtainedMark) from Score s join s.studentEntity st
        where s.assignment.id = ?1
    """)
    List<ScoreBasic> findBasicScoresByAssignmentId(Long assignmentId);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScoreBasic(s.id, st.id, s.assignment.id, s.obtainedMark) from Score s join s.studentEntity st
        where s.assignment.id in (?1)
    """)
    List<ScoreBasic> findScoresByAssignmentIds(List<Long> assignmentId);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScoreBasicValue(s.id, st.id, i.firstName, i.lastName, i.image, st.personalInfo.reference,
        s.assignment.classeEntity.name, sum(s.obtainedMark), s.isPresent)
        from Score s join s.studentEntity st join st.personalInfo i where s.assignment.preparedBy.id = ?1 and s.assignment.subject.id = ?2
        and s.assignment.semester.semestre.academicYear.current = true group by s.studentEntity order by sum(s.obtainedMark) desc
    """)
    Page<ScoreBasicValue> findBestStudentByTeacherScores(long teacherId, int subjectId, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScoreBasicValue(s.id, st.id, i.firstName, i.lastName, i.image, st.personalInfo.reference,
        s.assignment.classeEntity.name, sum(s.obtainedMark), s.isPresent)
        from Score s join s.studentEntity st join st.personalInfo i where s.assignment.preparedBy.id = ?1 and s.assignment.semester.semestre.academicYear.current = true
        group by s.studentEntity.id order by sum(s.obtainedMark) desc
    """)
    Page<ScoreBasicValue> findBestStudentByTeacherScores(long teacherId, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScoreBasicValue(s.id, st.id, i.firstName, i.lastName, i.image, st.personalInfo.reference,
        s.assignment.classeEntity.name, sum(s.obtainedMark), s.isPresent)
        from Score s join s.studentEntity st join st.personalInfo i where s.assignment.classeEntity.id = ?1 and s.assignment.semester.semestre.academicYear.id = ?2
        group by s.studentEntity.id
    """)
    Page<ScoreBasicValue> findBestStudentByClasseScores(int classeId, UUID academicYear, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScoreBasicValue(s.id, st.id, i.firstName, i.lastName, i.image, st.personalInfo.reference,
        s.assignment.classeEntity.name, sum(s.obtainedMark), s.isPresent)
        from Score s join s.studentEntity st join st.personalInfo i where s.assignment.classeEntity.id = ?1 and s.assignment.semester.semestre.academicYear.id = ?2
        and s.assignment.subject.id = ?3 group by s.studentEntity.id
    """)
    Page<ScoreBasicValue> findBestStudentByClasseBySubjectScores(int classeId, UUID academicYear, int courseId, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScoreBasicValue(s.id, st.id, i.firstName, i.lastName, i.image, st.personalInfo.reference,
        s.assignment.classeEntity.name, sum(s.obtainedMark), s.isPresent)
        from Score s join s.studentEntity st join st.personalInfo i where s.assignment.subject.id = ?1 and s.assignment.semester.semestre.academicYear.id = ?2
        group by s.studentEntity.id
    """)
    Page<ScoreBasicValue> findBestStudentByCourseScores(int courseId, UUID academicYear, Pageable pageable);

    @Transactional
    @Modifying
    @Query("""
        update Score s set s.obtainedMark = ?1, s.isPresent = ?2 where s.id = ?3 and s.assignment.id = ?4
    """)
    int updateScoresByAssignmentId(Double obtainedMarks, boolean presents, Long scoreId, Long assignmentId);

    @Query("""
        select count(s.id) from Score s where s.assignment.id = ?1
    """)
    Optional<Long> countAssignmentInScores(long assignmentId);

    @Query("select s.assignment.subject.department.code, avg(s.obtainedMark) from Score s where s.assignment.classeEntity.id = ?1 " +
            "and s.assignment.semester.semestre.academicYear.id = ?2 group by s.assignment.subject.department.code")
    List<Object[]> findClasseScoresAvgByClasse(int classeId, UUID academicYear);
}
