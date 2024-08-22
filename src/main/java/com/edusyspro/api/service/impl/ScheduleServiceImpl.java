package com.edusyspro.api.service.impl;

import com.edusyspro.api.model.Schedule;
import com.edusyspro.api.model.enums.Day;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.repository.ScheduleRepository;
import com.edusyspro.api.service.interfaces.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<Schedule> getAllClasseSchedule(int classeId, Section section) {
        return scheduleRepository.findAllByClasseEntityId(classeId, currentDay(section));
    }

    private Day currentDay(Section section) {
        Day currentDay;
        switch (section) {
            case MATERNELLE, PRIMAIRE -> currentDay = Day.ALL_DAYS;
            default -> currentDay = Day.getCurrentDay();
        }
        return currentDay;
    }
}
