package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.ScoreBasicValue;
import com.edusyspro.api.dto.custom.ScoreEssential;
import com.edusyspro.api.model.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("select new com.edusyspro.api.dto.custom.ScoreEssential(s.id, s.assignment.id, s.assignment.examName, s.assignment.examDate, " +
            "s.assignment.startTime, s.assignment.endTime, s.assignment.classeEntity.name, s.assignment.classeEntity.grade.section, " +
            "s.assignment.subject.id, s.assignment.subject.course, s.obtainedMark) " +
            "from Score s where s.assignment.semester.semestre.academicYear.current = true and s.studentEntity.id = ?1 order by s.assignment.examDate desc")
    Page<ScoreEssential> findLastFiveScoresByStudent(UUID studentId, Pageable pageable);

    @Query("select new com.edusyspro.api.dto.custom.ScoreEssential(s.id, s.assignment.id, s.assignment.examName, s.assignment.examDate, " +
            "s.assignment.startTime, s.assignment.endTime, s.assignment.classeEntity.name, s.assignment.classeEntity.grade.section, " +
            "s.assignment.subject.id, s.assignment.subject.course, s.obtainedMark) " +
            "from Score s where s.assignment.semester.semestre.academicYear.id = ?1 and s.studentEntity.id = ?2 order by s.assignment.examDate desc")
    Page<ScoreEssential> findAllByStudentIdAndAcademicYear(UUID academicYearId, UUID studentId, Pageable pageable);

    @Query("select new com.edusyspro.api.dto.custom.ScoreEssential(s.id, s.assignment.id, s.assignment.examName, s.assignment.examDate, " +
            "s.assignment.startTime, s.assignment.endTime, s.assignment.classeEntity.name, s.assignment.classeEntity.grade.section, " +
            "s.assignment.subject.id, s.assignment.subject.course, s.obtainedMark) " +
            "from Score s where s.studentEntity.id = ?2 and s.assignment.semester.semestre.academicYear.id = ?1 and s.assignment.subject.id = ?3 order by s.assignment.examDate desc")
    List<ScoreEssential> findAllByStudentIdAcademicYearAndSubjectId(UUID academicYearId, UUID studentId, int subjectId);

    @Query("select new com.edusyspro.api.dto.custom.ScoreBasicValue(s.studentEntity.id, s.studentEntity.personalInfo, s.obtainedMark) from Score s " +
            "where s.assignment.preparedBy.id = ?1 and s.assignment.semester.semestre.academicYear.current = true")
    List<ScoreBasicValue> finsAllTeacherMarks(long teacherId);

    @Query("select new com.edusyspro.api.dto.custom.ScoreBasicValue(s.studentEntity.id, s.studentEntity.personalInfo, s.obtainedMark) from Score s " +
            "where s.assignment.id = ?1 and s.assignment.semester.semestre.academicYear.current = true order by s.obtainedMark desc")
    Page<ScoreBasicValue> findScoresByAssignment(long assignmentId, Pageable pageable);

    @Query("select new com.edusyspro.api.dto.custom.ScoreBasicValue(s.studentEntity.id, s.studentEntity.personalInfo, sum(s.obtainedMark)) from Score s " +
            "where s.assignment.preparedBy.id = ?1 and s.assignment.subject.id = ?2 and s.assignment.semester.semestre.academicYear.current = true " +
            "group by s.studentEntity order by s.obtainedMark desc")
    Page<ScoreBasicValue> findBestStudentByTeacherScores(long teacherId, int subjectId, Pageable pageable);

    @Query("select new com.edusyspro.api.dto.custom.ScoreBasicValue(s.studentEntity.id, s.studentEntity.personalInfo, sum(s.obtainedMark)) from Score s " +
            "where s.assignment.preparedBy.id = ?1 and s.assignment.semester.semestre.academicYear.current = true " +
            "group by s.studentEntity order by s.obtainedMark desc")
    Page<ScoreBasicValue> findBestStudentByTeacherScores(long teacherId, Pageable pageable);

    @Query("select new com.edusyspro.api.dto.custom.ScoreBasicValue(s.studentEntity.id, s.studentEntity.personalInfo, sum(s.obtainedMark)) from Score s " +
            "where s.assignment.classeEntity.id = ?1 and s.assignment.semester.semestre.academicYear.id = ?2 " +
            "group by s.studentEntity order by s.obtainedMark desc")
    Page<ScoreBasicValue> findBestStudentByClasseScores(int classeId, UUID academicYear, Pageable pageable);
}
