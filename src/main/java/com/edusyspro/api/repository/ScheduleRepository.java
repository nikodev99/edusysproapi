package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.ScheduleEssential;
import com.edusyspro.api.dto.custom.TeacherClasseCourse;
import com.edusyspro.api.dto.custom.TeacherEssential;
import com.edusyspro.api.model.Schedule;
import com.edusyspro.api.model.enums.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("""
        select new com.edusyspro.api.dto.custom.ScheduleEssential(
            s.id, s.academicYear.years, s.teacher.id, s.teacher.personalInfo.firstName, s.teacher.personalInfo.lastName, s.course.id, s.course.course,
            s.course.abbr, s.classeEntity.id, s.classeEntity.name, s.classeEntity.grade.section, s.designation, s.dayOfWeek, s.startTime, s.endTime
        ) from Schedule s left join s.teacher t left join t.personalInfo left join s.course
        where s.academicYear.current = true and s.classeEntity.id = ?1 and s.dayOfWeek = ?2
    """)
    List<ScheduleEssential> findAllByClasseEntityId(int classeId, Day currentDay);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScheduleEssential(
            s.id, s.academicYear.years, s.teacher.id, s.teacher.personalInfo.firstName, s.teacher.personalInfo.lastName, s.course.id, s.course.course,
            s.course.abbr, s.classeEntity.id, s.classeEntity.name, s.classeEntity.grade.section, s.designation, s.dayOfWeek, s.startTime, s.endTime
        ) from Schedule s left join s.teacher t left join t.personalInfo left join s.course
        where s.academicYear.current = true and s.classeEntity.id = :id
    """)
    List<ScheduleEssential> findAllDayClasseSchedules(@Param("id") int classeId);

    @Query("""
        select distinct new com.edusyspro.api.dto.custom.TeacherEssential(t.id, t.personalInfo, t.hireDate, t.salaryByHour,
        t.school.id, t.school.name, t.createdAt, t.modifyAt) from Schedule s join s.teacher t where s.academicYear.current = true
        and s.classeEntity.id = ?1 and s.course.id = ?2 and s.academicYear.school.id = ?3
    """)
    TeacherEssential findTeacherByClasseEntityIdAndCourseId(int classeEntity_id, int course_id, UUID school_id);

    @Query("""
        select distinct new com.edusyspro.api.dto.custom.TeacherEssential(t.id, t.personalInfo, t.hireDate, t.salaryByHour,
        t.school.id, t.school.name, t.createdAt, t.modifyAt) from Schedule s join s.teacher t where s.academicYear.current = true and s.classeEntity.id = ?1 and s.academicYear.school.id = ?2
    """)
    TeacherEssential findTeacherByClasseEntityId(int classeEntity_id, UUID school_id);

    @Query("""
        SELECT DISTINCT new com.edusyspro.api.dto.custom.TeacherClasseCourse(
            t.id, t.personalInfo, t.hireDate, c.id, c.name, co.id, co.course
        ) FROM Schedule s left join s.teacher t left join s.classeEntity c left join s.course co WHERE c.id = ?1
    """)
    List<TeacherClasseCourse> findAllClasseTeachers(int classId);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScheduleEssential(
            s.id, s.academicYear.years, s.teacher.id, s.teacher.personalInfo.firstName, s.teacher.personalInfo.lastName, s.course.id, s.course.course,
            s.course.abbr, s.classeEntity.id, s.classeEntity.name, s.classeEntity.grade.section, s.designation, s.dayOfWeek, s.startTime, s.endTime
        ) from Schedule s left join s.course where s.academicYear.current = true and s.teacher.id = ?1
    """)
    List<ScheduleEssential> findAllByTeacherId(UUID teacher_id);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScheduleEssential(
            s.id, s.academicYear.years, s.teacher.id, s.teacher.personalInfo.firstName, s.teacher.personalInfo.lastName, s.course.id, s.course.course,
            s.course.abbr, s.classeEntity.id, s.classeEntity.name, s.classeEntity.grade.section, s.designation, s.dayOfWeek, s.startTime, s.endTime
        ) from Schedule s left join s.course c where s.academicYear.current = true and s.teacher.id = ?1 and s.dayOfWeek = ?2
    """)
    List<ScheduleEssential> findAllByTeacherIdByDay(UUID teacher_id, Day dayOfWeek);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScheduleEssential(
            s.id, s.academicYear.years, s.teacher.id, s.teacher.personalInfo.firstName, s.teacher.personalInfo.lastName, s.course.id, s.course.course,
            s.course.abbr, s.classeEntity.id, s.classeEntity.name, s.classeEntity.grade.section, s.designation, s.dayOfWeek, s.startTime, s.endTime
        ) from Schedule s where s.academicYear.current = true and s.course.id = ?1 and s.dayOfWeek = ?2
    """)
    List<ScheduleEssential> findCourseSchedulesByDay(int courseId, Day dayOfWeek);

    @Query("""
        select new com.edusyspro.api.dto.custom.ScheduleEssential(
            s.id, s.academicYear.years, s.teacher.id, s.teacher.personalInfo.firstName, s.teacher.personalInfo.lastName, s.course.id, s.course.course,
            s.course.abbr, s.classeEntity.id, s.classeEntity.name, s.classeEntity.grade.section, s.designation, s.dayOfWeek, s.startTime, s.endTime
        ) from Schedule s where s.academicYear.current = true and s.course.id = ?1
    """)
    List<ScheduleEssential> findCourseSchedules(int courseId);

    @Query("select s.classeEntity.name, s.startTime, s.endTime from Schedule s where s.course.id = ?1 group by s.classeEntity.id, s.startTime, s.endTime")
    List<Object[]> findCourseHourByClasse(int courseId);

    @Query("select concat(s.teacher.personalInfo.lastName, ' ', s.teacher.personalInfo.firstName) , s.startTime, s.endTime " +
            "from Schedule s where s.course.id = ?1 group by s.teacher.id, s.classeEntity.id, s.startTime, s.endTime")
    List<Object[]> findCourseHourByTeacher(int courseId);
}
