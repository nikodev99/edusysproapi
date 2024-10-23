package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.model.Schedule;
import com.edusyspro.api.model.Teacher;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.service.CustomService;

import java.util.List;
import java.util.UUID;

public interface ScheduleService extends CustomService<Schedule, Long> {

    List<Schedule> getAllClasseSchedule(int classeId, Section section);

    Teacher getOnlyTeacherOfCourseInClasse(int classeId, int courseId, UUID schoolId);

    Teacher getOnlyTeacherOfClasse(int classeId, UUID schoolId);
}
