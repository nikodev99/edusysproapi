package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.ScheduleEssential;
import com.edusyspro.api.dto.custom.TeacherClasseCourse;
import com.edusyspro.api.dto.custom.TeacherEssential;
import com.edusyspro.api.model.Schedule;
import com.edusyspro.api.model.enums.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("""
        select new com.edusyspro.api.dto.custom.ScheduleEssential(
            s.id, s.academicYear.years, s.teacher.id, s.teacher.personalInfo.id, s.teacher.personalInfo.firstName, s.teacher.personalInfo.lastName, s.course.id, s.course.course,
            s.course.abbr, s.classeEntity.id, s.classeEntity.name, s.classeEntity.grade.section, s.designation, s.dayOfWeek, s.startTime, s.endTime
        ) from Schedule s left join s.teacher t left join t.personalInfo left join s.course
        where s.classeEntity.id = ?1 and s.dayOfWeek = ?2 and s.academicYear.id = ?3
    """)
    List<ScheduleEssential> findAllByClasseEntityId(int classeId, Day currentDay, UUID academicYear);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScheduleEssential(
            s.id, s.academicYear.years, s.teacher.id, s.teacher.personalInfo.id, s.teacher.personalInfo.firstName, s.teacher.personalInfo.lastName, s.course.id, s.course.course,
            s.course.abbr, s.classeEntity.id, s.classeEntity.name, s.classeEntity.grade.section, s.designation, s.dayOfWeek, s.startTime, s.endTime
        ) from Schedule s left join s.teacher t left join t.personalInfo left join s.course
        where s.academicYear.id = :academicYear and s.classeEntity.id = :id
    """)
    List<ScheduleEssential> findAllDayClasseSchedules(@Param("academicYear") UUID academicYear, @Param("id") int classeId);

    @Query("""
        select distinct new com.edusyspro.api.dto.custom.TeacherEssential(t.id, t.personalInfo, sc.contract.id, sc.contract.role,
        sc.contract.jobTitle, sc.contract.startDate, sc.contract.salaryByHour, sc.status, sc.school.id, sc.school.name,
        t.createdAt, t.modifyAt) from Schedule s join s.teacher t join t.schoolAffiliations sc where s.academicYear.current = true
        and s.classeEntity.id = ?1 and s.course.id = ?2 and s.academicYear.school.id = ?3
    """)
    TeacherEssential findTeacherByClasseEntityIdAndCourseId(int classeEntity_id, int course_id, UUID school_id);

    @Query("""
        select distinct new com.edusyspro.api.dto.custom.TeacherEssential(t.id, t.personalInfo, sc.contract.id, sc.contract.role,
        sc.contract.jobTitle, sc.contract.startDate, sc.contract.salaryByHour, sc.status, sc.school.id, sc.school.name,
        t.createdAt, t.modifyAt) from Schedule s join s.teacher t join t.schoolAffiliations sc where s.academicYear.current = true
        and s.classeEntity.id = ?1 and s.academicYear.school.id = ?2
    """)
    TeacherEssential findTeacherByClasseEntityId(int classeEntity_id, UUID school_id);

    @Query("""
        SELECT DISTINCT new com.edusyspro.api.dto.custom.TeacherClasseCourse(
            t.id, t.personalInfo, tsa.contract.id, tsa.contract.startDate, c.id, c.name, co.id, co.course
        ) FROM Schedule s join s.classeEntity c join s.course co join s.teacher t join t.schoolAffiliations tsa
            WHERE c.id = ?1 and tsa.school.id = ?2 and s.academicYear.id = ?3
    """)
    List<TeacherClasseCourse> findAllClasseTeachers(int classId, UUID schoolId, UUID academicYear);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScheduleEssential(
            s.id, s.academicYear.years, s.teacher.id, s.teacher.personalInfo.id, s.teacher.personalInfo.firstName, s.teacher.personalInfo.lastName, s.course.id, s.course.course,
            s.course.abbr, s.classeEntity.id, s.classeEntity.name, s.classeEntity.grade.section, s.designation, s.dayOfWeek, s.startTime, s.endTime
        ) from Schedule s left join s.course where s.academicYear.id = ?1 and s.academicYear.current = true and s.teacher.id = ?2
    """)
    List<ScheduleEssential> findAllByTeacherId(UUID academicYear, UUID teacher_id);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScheduleEssential(
            s.id, s.academicYear.years, s.teacher.id, s.teacher.personalInfo.id, s.teacher.personalInfo.firstName, s.teacher.personalInfo.lastName, s.course.id, s.course.course,
            s.course.abbr, s.classeEntity.id, s.classeEntity.name, s.classeEntity.grade.section, s.designation, s.dayOfWeek, s.startTime, s.endTime
        ) from Schedule s left join s.course c where s.academicYear.id = ?1 and s.academicYear.current = true and s.teacher.id = ?2 and s.dayOfWeek = ?3
    """)
    List<ScheduleEssential> findAllByTeacherIdByDay(UUID academicYear, UUID teacher_id, Day dayOfWeek);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScheduleEssential(
            s.id, s.academicYear.years, s.teacher.id, s.teacher.personalInfo.id, s.teacher.personalInfo.firstName, s.teacher.personalInfo.lastName, s.course.id, s.course.course,
            s.course.abbr, s.classeEntity.id, s.classeEntity.name, s.classeEntity.grade.section, s.designation, s.dayOfWeek, s.startTime, s.endTime
        ) from Schedule s where s.academicYear.current = true and s.course.id = ?1 and s.dayOfWeek = ?2
    """)
    List<ScheduleEssential> findCourseSchedulesByDay(int courseId, Day dayOfWeek);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScheduleEssential(
            s.id, s.academicYear.years, s.teacher.id, s.teacher.personalInfo.id, s.teacher.personalInfo.firstName, s.teacher.personalInfo.lastName, s.course.id, s.course.course,
            s.course.abbr, s.classeEntity.id, s.classeEntity.name, s.classeEntity.grade.section, s.designation, s.dayOfWeek, s.startTime, s.endTime
        ) from Schedule s where s.academicYear.current = true and s.course.id = ?1
    """)
    List<ScheduleEssential> findCourseSchedules(int courseId);

    @Query("select s.classeEntity.name, s.startTime, s.endTime from Schedule s where s.course.id = ?1 group by s.classeEntity.id, s.startTime, s.endTime")
    List<Object[]> findCourseHourByClasse(int courseId);

    @Query("select concat(s.teacher.personalInfo.lastName, ' ', s.teacher.personalInfo.firstName) , s.startTime, s.endTime " +
            "from Schedule s where s.course.id = ?1 group by s.teacher.id, s.classeEntity.id, s.startTime, s.endTime")
    List<Object[]> findCourseHourByTeacher(int courseId);

    @Query("SELECT s.dayOfWeek FROM Schedule s WHERE s.teacher.id = ?1")
    List<Day> getDayBySessionDate(UUID teacherId);

    @Modifying
    @Transactional
    @Query("UPDATE Schedule s SET s.teacher.id = ?1, s.course.id = ?2, s.classeEntity.id = ?3, s.designation = ?4, s.dayOfWeek = ?5, s.startTime = ?6, s.endTime = ?7 WHERE s.id = ?8")
    int updateSchedule(
            UUID teacherId,
            Integer courseId,
            int classeId,
            String designation,
            Day dayOfWeek,
            LocalTime start,
            LocalTime end,
            long scheduleId
    );

    @Modifying
    @Transactional
    @Query("UPDATE Schedule s SET s.dayOfWeek = ?1, s.startTime = ?2, s.endTime = ?3 WHERE s.id = ?4")
    int updateScheduleTime(Day dayOfWeek, LocalTime startTime, LocalTime endTime, Long id);
}
