package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.Schedule;
import com.edusyspro.api.model.Teacher;
import com.edusyspro.api.model.enums.Day;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.repository.ScheduleRepository;
import com.edusyspro.api.service.interfaces.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Override
    public Teacher getOnlyTeacherOfCourseInClasse(int classeId, int courseId, UUID schoolId) {
        return scheduleRepository.findTeacherByClasseEntityIdAndCourseId(classeId, courseId, schoolId);
    }

    @Override
    public Teacher getOnlyTeacherOfClasse(int classeId, UUID schoolId) {
        return scheduleRepository.findTeacherByClasseEntityId(classeId, schoolId);
    }

    @Override
    public Schedule save(Schedule entity) {
        return null;
    }

    @Override
    public List<Schedule> saveAll(List<Schedule> entities) {
        return List.of();
    }

    @Override
    public List<Schedule> fetchAll() {
        return List.of();
    }

    @Override
    public List<Schedule> fetchAll(String schoolId) {
        return List.of();
    }

    @Override
    public Page<Schedule> fetchAll(String schoolId, Pageable pageable) {
        return null;
    }

    @Override
    public List<Schedule> fetchAll(Object... args) {
        return List.of();
    }

    @Override
    public Page<Schedule> fetchAll(Pageable pageable, Object... args) {
        return null;
    }

    @Override
    public List<Schedule> fetchAllById(Long id) {
        return List.of();
    }

    @Override
    public List<Schedule> fetchAllById(Object... arg) {
        return List.of();
    }

    @Override
    public Schedule fetchOneById(Long id) {
        return null;
    }

    @Override
    public Schedule fetchOneById(Long id, String schoolId) {
        return null;
    }

    @Override
    public Schedule fetchOneById(Long id, Object... args) {
        return null;
    }

    @Override
    public int update(Schedule entity) {
        return 0;
    }

    @Override
    public int patch(Long id, UpdateField field) {
        return 0;
    }

    @Override
    public int delete(Schedule entity) {
        return 0;
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
