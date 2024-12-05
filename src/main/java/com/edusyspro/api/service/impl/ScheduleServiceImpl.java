package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.ScheduleDTO;
import com.edusyspro.api.dto.custom.ScheduleEssential;
import com.edusyspro.api.dto.custom.UpdateField;
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
import java.util.Map;
import java.util.UUID;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<ScheduleDTO> getAllClasseSchedule(int classeId, Section section) {
        Day scheduleDay = currentDay(section);
        System.out.println("DAY: " + scheduleDay);
        System.out.println("SAME DAY " + (scheduleDay == Day.ALL_DAYS));
        List<ScheduleEssential> scheduleEssentials;
        if (scheduleDay == Day.ALL_DAYS) {
            scheduleEssentials = scheduleRepository.findAllDayClasseSchedules(classeId);
        }else {
            scheduleEssentials =  scheduleRepository.findAllByClasseEntityId(classeId, currentDay(section));
        }
        return scheduleEssentials.stream()
                .map(ScheduleEssential::toScheduleDto)
                .toList();
    }

    @Override
    public List<ScheduleDTO> getTeacherSchedule(String teacherId) {
        return scheduleRepository.findAllByTeacherId(UUID.fromString(teacherId)).stream()
                .map(ScheduleEssential::toScheduleDto)
                .toList();
    }

    @Override
    public List<ScheduleDTO> getTeacherScheduleByDay(String teacherId, boolean allDay) {
        return scheduleRepository.findAllByTeacherIdByDay(UUID.fromString(teacherId), allDay ? Day.ALL_DAYS : Day.getCurrentDay()).stream()
                .map(ScheduleEssential::toScheduleDto)
                .toList();
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
    public ScheduleDTO save(ScheduleDTO entity) {
        return null;
    }

    @Override
    public List<ScheduleDTO> saveAll(List<ScheduleDTO> entities) {
        return List.of();
    }

    @Override
    public List<ScheduleDTO> fetchAll() {
        return List.of();
    }

    @Override
    public List<ScheduleDTO> fetchAll(String schoolId) {
        return List.of();
    }

    @Override
    public Page<ScheduleDTO> fetchAll(String schoolId, Pageable pageable) {
        return null;
    }

    @Override
    public List<ScheduleDTO> fetchAll(Object... args) {
        return List.of();
    }

    @Override
    public Page<ScheduleDTO> fetchAll(Pageable pageable, Object... args) {
        return null;
    }

    @Override
    public List<ScheduleDTO> fetchAllById(Long id) {
        return List.of();
    }

    @Override
    public List<ScheduleDTO> fetchAllById(Object... arg) {
        return List.of();
    }

    @Override
    public ScheduleDTO fetchOneById(Long id) {
        return null;
    }

    @Override
    public ScheduleDTO fetchOneById(Long id, String schoolId) {
        return null;
    }

    @Override
    public ScheduleDTO fetchOneById(Long id, Object... args) {
        return null;
    }

    @Override
    public ScheduleDTO fetchOneByCustomColumn(String columnValue) {
        return null;
    }

    @Override
    public ScheduleDTO fetchOneByCustomColumn(String columnValue, Object... args) {
        return null;
    }

    @Override
    public ScheduleDTO fetchOneById(Object... arg) {
        return null;
    }

    @Override
    public int update(ScheduleDTO entity) {
        return 0;
    }

    @Override
    public int patch(Long id, UpdateField field) {
        return 0;
    }

    @Override
    public int delete(ScheduleDTO entity) {
        return 0;
    }

    @Override
    public Map<String, Long> count(Long id) {
        return Map.of();
    }

    @Override
    public Map<String, Long> count(String schoolId) {
        return Map.of();
    }

    @Override
    public Map<String, Long> count(Object... args) {
        return Map.of();
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
