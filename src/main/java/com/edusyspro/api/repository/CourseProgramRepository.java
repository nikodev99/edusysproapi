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
        SELECT cp.id as programId, cp.name as programName, cp.purpose as purpose, cp.description as programDescription,
        cp.timing.status as programStatus, cp.semester as semester, cp.timing.id as programTimingId, cp.timing.startDate as programStartDate,
        cp.timing.endDate as programEndDate, cp.timing.academicYear.id as academicYearId, cp.timing.academicYear.years as academicYear,
        cp.timing.updatedAt as programUpdateDate, cp.timing.completedAt as programCompletedDate, cp.course.course as courseName,
        cp.course.abbr as courseAbbr, cp.classe.name as classeName, cp.classe.grade.section as section, cp.teacher.personalInfo as teacher
        FROM CourseProgram cp WHERE cp.teacher.id = ?1 AND cp.course.id = ?2 AND cp.classe.id = ?3 AND cp.timing.academicYear.id = ?4
    """)
    List<CourseProgramEssential> findAllPerTeacherByCourseClasseAndAcademicYear(UUID teacherId, int courseId, int classeId, UUID academicYearId);

    @Query("""
        SELECT cp.id as programId, cp.name as programName, cp.purpose as purpose, cp.description as programDescription,
        cp.timing.status as programStatus, cp.semester as semester, cp.timing.id as programTimingId, cp.timing.startDate as programStartDate,
        cp.timing.endDate as programEndDate, cp.timing.academicYear.id as academicYearId, cp.timing.academicYear.years as academicYear,
        cp.timing.updatedAt as programUpdateDate, cp.timing.completedAt as programCompletedDate, cp.course.course as courseName,
        cp.course.abbr as courseAbbr, cp.classe.name as classeName, cp.classe.grade.section as section, cp.teacher.personalInfo as teacher
        FROM CourseProgram cp WHERE cp.teacher.id = ?1 AND cp.classe.id = ?2 AND cp.timing.academicYear.id = ?3
    """)
    List<CourseProgramEssential> findAllPerTeacherByClasseAndAcademicYear(UUID teacherId, int classeId, UUID academicYearId);


    @Query("""
        SELECT cp.id as id, cp.name as programName, cpt.title as topicTitle, cp.classe.name as classe, cp.timing.status as programStatus
        FROM CourseProgram cp LEFT JOIN cp.topic cpt WHERE cp.teacher.id = ?1 AND cp.course.id = ?2 AND cp.classe.id = ?3 AND cp.timing.academicYear.id = ?4
    """)
    List<CourseProgramBasic> findAllBasicPerTeacherByCourseClasseAndAcademicYear(UUID teacherId, int courseId, int classeId, UUID academicYearId);

    @Query("""
        SELECT cp.id as id, cp.name as programName, cpt.title as topicTitle, cp.classe.name as classe, cp.timing.status as programStatus
        FROM CourseProgram cp JOIN cp.topic cpt WHERE cp.teacher.id = ?1 AND cp.course.id = ?2 AND cp.classe.id = ?3 AND cp.timing.academicYear.current = true
    """)
    List<CourseProgramBasic> findAllBasicPerTeacherByCourseClasseAndCurrentAcademicYear(UUID teacherId, int courseId, int classeId);

    @Query("""
        SELECT cp.id as programId, cpt.id as topicId, cp.name as programName, cpt.title as topicTitle, cp.purpose as purpose,
        cp.description as programDescription, cpt.description as topicDescription, cpt.order as order, cp.timing.status as programStatus,
        cpt.timing.status as topicStatus, cp.semester as semester, cp.timing.id as programTimingId, cpt.timing.id as topicTimingId,
        cp.timing.startDate as programStartDate, cpt.timing.startDate as topicStartDate, cp.timing.endDate as programEndDate,
        cpt.timing.endDate as topicEndDate, cp.timing.academicYear.id as academicYearId, cp.timing.academicYear.years as academicYear,
        cp.timing.updatedAt as programUpdateDate, cp.timing.completedAt as programCompletedDate, cpt.timing.updatedAt as topicUpdateDate,
        cpt.timing.completedAt as topicCompletedDate, cp.course.course as courseName, cp.course.abbr as courseAbbr, cp.classe.name as classeName,
        cp.classe.grade.section as section, cp.teacher.personalInfo as teacher
        FROM CourseProgram cp JOIN cp.topic cpt WHERE cp.teacher.id = ?1 AND cp.course.id = ?2
    """)
    List<CourseProgramEssential> findAllByTeacherAndCourse(UUID teacherId, int courseId);

    @Query("""
        SELECT cp.id as programId, cpt.id as topicId, cp.name as programName, cpt.title as topicTitle, cp.purpose as purpose,
        cp.description as programDescription, cpt.description as topicDescription, cpt.order as order, cp.timing.status as programStatus,
        cpt.timing.status as topicStatus, cp.semester as semester, cp.timing.id as programTimingId, cpt.timing.id as topicTimingId,
        cp.timing.startDate as programStartDate, cpt.timing.startDate as topicStartDate, cp.timing.endDate as programEndDate,
        cpt.timing.endDate as topicEndDate, cp.timing.academicYear.id as academicYearId, cp.timing.academicYear.years as academicYear,
        cp.timing.updatedAt as programUpdateDate, cp.timing.completedAt as programCompletedDate, cpt.timing.updatedAt as topicUpdateDate,
        cpt.timing.completedAt as topicCompletedDate, cp.course.course as courseName, cp.course.abbr as courseAbbr, cp.classe.name as classeName,
        cp.classe.grade.section as section, cp.teacher.personalInfo as teacher
        FROM CourseProgram cp JOIN cp.topic cpt WHERE cp.classe.id = ?1 AND cp.course.id = ?2
    """)
    List<CourseProgramEssential> findAllByClasseAndCourse(int classeId, int courseId);
}
