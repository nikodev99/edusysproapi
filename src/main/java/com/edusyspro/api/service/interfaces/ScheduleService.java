package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.model.Schedule;
import com.edusyspro.api.model.enums.Section;

import java.util.List;

public interface ScheduleService {

    List<Schedule> getAllClasseSchedule(int classeId, Section section);

}
