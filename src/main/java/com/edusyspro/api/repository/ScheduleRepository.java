package com.edusyspro.api.repository;

import com.edusyspro.api.model.Schedule;
import com.edusyspro.api.model.Teacher;
import com.edusyspro.api.model.enums.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s where s.academicYear.current = true and s.classeEntity.id = ?1 and s.dayOfWeek = ?2")
    List<Schedule> findAllByClasseEntityId(int classeId, Day currentDay);

    @Query("""
        select distinct s.teacher from Schedule s where s.academicYear.current = true and s.classeEntity.id = ?1 and s.course.id = ?2 and s.academicYear.school.id = ?3
    """)
    Teacher findTeacherByClasseEntityIdAndCourseId(int classeEntity_id, int course_id, UUID school_id);

    @Query("""
        select distinct s.teacher from Schedule s where s.academicYear.current = true and s.classeEntity.id = ?1 and s.academicYear.school.id = ?2
    """)
    Teacher findTeacherByClasseEntityId(int classeEntity_id, UUID school_id);

}
