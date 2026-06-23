package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.TeachingReportDTO;

import java.time.LocalDate;
import java.util.List;

public interface TeachingReportService {
    void saveReport(TeachingReportDTO report);
    List<TeachingReportDTO> getBasicReport(String teacherId, LocalDate startDate, LocalDate endDate);
    TeachingReportDTO getDetailedReport(Long reportId);
}
