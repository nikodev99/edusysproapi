package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.ScheduleDTO;
import com.edusyspro.api.dto.TeacherDTO;
import com.edusyspro.api.dto.custom.ScheduleEssential;
import com.edusyspro.api.dto.custom.ScheduleHoursBy;
import com.edusyspro.api.dto.custom.TeacherClasseCourse;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.enums.Day;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.repository.ScheduleRepository;
import com.edusyspro.api.service.interfaces.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<ScheduleDTO> getAllClasseSchedule(int classeId) {
        return scheduleRepository.findAllDayClasseSchedules(classeId).stream()
                .map(ScheduleEssential::toScheduleDto)
                .toList();
    }

    @Override
    public List<ScheduleDTO> getAllClasseSchedule(int classeId, Section section) {
        Day scheduleDay = currentDay(section);
        List<ScheduleEssential> scheduleEssentials;
        if (scheduleDay == Day.ALL_DAYS) {
            scheduleEssentials = scheduleRepository.findAllDayClasseSchedules(classeId);
        }else {
            scheduleEssentials =  scheduleRepository.findAllByClasseEntityId(classeId, currentDay(section));
        }
        System.out.println("scheduleEssentials: " + scheduleEssentials);
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
    public List<TeacherDTO> getAllClasseTeachers(int classeId) {
        return scheduleRepository.findAllClasseTeachers(classeId).stream()
                .map(TeacherClasseCourse::toTeacher)
                .toList();
    }

    @Override
    public TeacherDTO getOnlyTeacherOfCourseInClasse(int classeId, int courseId, UUID schoolId) {
        return scheduleRepository.findTeacherByClasseEntityIdAndCourseId(classeId, courseId, schoolId).toTeacher();
    }

    @Override
    public TeacherDTO getOnlyTeacherOfClasse(int classeId, UUID schoolId) {
        return scheduleRepository.findTeacherByClasseEntityId(classeId, schoolId).toTeacher();
    }

    @Override
    public List<ScheduleDTO> getCourseSchedules(int courseId, boolean byDay) {
        if (byDay) {
            return scheduleRepository.findCourseSchedulesByDay(courseId, Day.getCurrentDay()).stream()
                    .map(ScheduleEssential::toScheduleDto)
                    .toList();
        }else {
            return scheduleRepository.findCourseSchedules(courseId).stream()
                    .map(ScheduleEssential::toScheduleDto)
                    .toList();
        }
    }

    @Override
    public List<ScheduleHoursBy> getTotalCourseHoursByClasses(int courseId) {
        List<Object[]> results = scheduleRepository.findCourseHourByClasse(courseId);
        return !results.isEmpty() ? calculateTotalHours(results) : List.of();
    }

    @Override
    public List<ScheduleHoursBy> getTotalCourseHoursByTeachers(int courseId) {
        List<Object[]> results = scheduleRepository.findCourseHourByTeacher(courseId);
        return !results.isEmpty() ? calculateTotalHours(results) : List.of();
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
    public Page<ScheduleDTO> fetchAllByOtherEntityId(String otherEntityId, Pageable pageable) {
        return null;
    }

    @Override
    public List<ScheduleDTO> fetchAllByOtherEntityId(String otherEntityId) {
        return List.of();
    }

    @Override
    public List<ScheduleDTO> fetchAllByOtherEntityId(Object... arg) {
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
    public Map<String, Boolean> update(ScheduleDTO entity, Long id) {
        return Map.of();
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

    private List<ScheduleHoursBy> calculateTotalHours(List<Object[]> schedules) {
        return schedules.stream()
                .collect(Collectors.groupingBy(
                        t -> (String) t[0],
                        Collectors.summingLong(t -> Duration.between((LocalTime)t[1], (LocalTime)t[2]).toHours()))
                )
                .entrySet().stream()
                .map(entry -> new ScheduleHoursBy(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
