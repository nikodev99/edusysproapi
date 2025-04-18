package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.ScheduleDTO;
import com.edusyspro.api.dto.TeacherDTO;
import com.edusyspro.api.dto.custom.ScheduleHoursBy;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.service.CustomService;

import java.util.List;
import java.util.UUID;

public interface ScheduleService extends CustomService<ScheduleDTO, Long> {

    List<ScheduleDTO> getAllClasseSchedule(int classeId);

    List<ScheduleDTO> getAllClasseSchedule(int classeId, Section section);

    List<ScheduleDTO> getTeacherSchedule(String teacherId);

    List<ScheduleDTO> getTeacherScheduleByDay(String teacherId, boolean allDay);

    List<TeacherDTO> getAllClasseTeachers(int classeId);

    TeacherDTO getOnlyTeacherOfCourseInClasse(int classeId, int courseId, UUID schoolId);

    TeacherDTO getOnlyTeacherOfClasse(int classeId, UUID schoolId);

    List<ScheduleDTO> getCourseSchedules(int courseId, boolean byDay);

    List<ScheduleHoursBy> getTotalCourseHoursByClasses(int courseId);
    List<ScheduleHoursBy> getTotalCourseHoursByTeachers(int courseId);
}
