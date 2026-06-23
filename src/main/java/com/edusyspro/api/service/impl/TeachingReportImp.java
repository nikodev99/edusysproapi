package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.ScheduleDTO;
import com.edusyspro.api.dto.TeachingReportDTO;
import com.edusyspro.api.model.enums.Day;
import com.edusyspro.api.model.enums.ReportStatus;
import com.edusyspro.api.repository.TeachingReportRepository;
import com.edusyspro.api.service.interfaces.ScheduleService;
import com.edusyspro.api.service.interfaces.TeachingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TeachingReportImp implements TeachingReportService {
    private final TeachingReportRepository teachingReportRepository;
    private final ScheduleService scheduleService;

    @Autowired
    public TeachingReportImp(TeachingReportRepository teachingReportRepository, ScheduleService scheduleService) {
        this.teachingReportRepository = teachingReportRepository;
        this.scheduleService = scheduleService;
    }

    @Override
    public void saveReport(TeachingReportDTO report) {
        DayOfWeek dayOfWeek = report.getSessionDate().getDayOfWeek();
        List<Day> days = scheduleService.getTeacherDays(report.getTeacher().getId().toString());

        if (!isDayAllowed(dayOfWeek, days)) {
            throw new IllegalArgumentException("Le professeur ne peut pas faire de rapport pour ce jour");
        }

        teachingReportRepository.save(report.toEntity());
    }

    @Override
    public List<TeachingReportDTO> getBasicReport(String teacherId, LocalDate startDate, LocalDate endDate) {
        return teachingReportRepository.findAllWeekReport(UUID.fromString(teacherId), startDate, endDate).stream()
                .map(t -> TeachingReportDTO.builder()
                        .id(t.get("id", Long.class))
                        .schedule(ScheduleDTO.builder().id(t.get("schedule_id", Long.class)).build())
                        .isLateSubmission(t.get("late", Boolean.class))
                        .sessionDate(t.get("date", LocalDate.class))
                        .reportStatus(t.get("status", ReportStatus.class))
                        .notes(t.get("notes", String.class)).build()
                )
                .toList();
    }

    @Override
    public TeachingReportDTO getDetailedReport(Long reportId) {
        return teachingReportRepository.findReportById(reportId)
                .map(t -> TeachingReportDTO.builder().build().toReport(t))
                .orElse(TeachingReportDTO.builder().build());
    }

    private boolean isDayAllowed(DayOfWeek dayOfWeek, List<Day> allowedDays) {
        // If the list contains ALL_DAYS, any day is allowed
        if (allowedDays.contains(Day.ALL_DAYS)) {
            return true;
        }
        // Otherwise, check if the dayOfWeek matches any of the allowed days
        return allowedDays.stream()
                .anyMatch(day -> day.toDayOfWeek() == dayOfWeek);
    }
}
