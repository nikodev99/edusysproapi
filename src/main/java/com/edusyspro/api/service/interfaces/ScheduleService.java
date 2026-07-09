package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.ScheduleDTO;
import com.edusyspro.api.dto.TeacherDTO;
import com.edusyspro.api.dto.custom.ScheduleHoursBy;
import com.edusyspro.api.model.enums.Day;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.service.CustomService;

import java.util.List;
import java.util.UUID;

public interface ScheduleService extends CustomService<ScheduleDTO, Long> {

    List<ScheduleDTO> getAllClasseSchedule(int classeId);

    List<ScheduleDTO> getAllClasseSchedule(int classeId, Section section);

    /**
     * Retrieves the schedules for a specific teacher in a given academic year.
     *
     * @param academicYear the academic year for which the schedule is to be retrieved
     * @param teacherId the unique identifier of the teacher whose schedule is requested
     * @return a list of ScheduleDTO objects representing the teacher's schedules for the specified academic year
     */
    List<ScheduleDTO> getTeacherSchedule(String academicYear, String teacherId);

    List<ScheduleDTO> getTeacherScheduleByDay(String academicYear, String teacherId, boolean allDay);

    List<TeacherDTO> getAllClasseTeachers(int classeId);

    TeacherDTO getOnlyTeacherOfCourseInClasse(int classeId, int courseId, UUID schoolId);

    TeacherDTO getOnlyTeacherOfClasse(int classeId, UUID schoolId);

    List<ScheduleDTO> getCourseSchedules(int courseId, boolean byDay);

    List<ScheduleHoursBy> getTotalCourseHoursByClasses(int courseId);
    List<ScheduleHoursBy> getTotalCourseHoursByTeachers(int courseId);

    List<Day> getTeacherDays(String teacherId);
}
