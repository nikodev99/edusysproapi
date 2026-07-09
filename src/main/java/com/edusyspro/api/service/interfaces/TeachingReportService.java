package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.AcademicYearDTO;
import com.edusyspro.api.dto.DateRange;
import com.edusyspro.api.dto.ScheduleDTO;
import com.edusyspro.api.dto.TeachingReportDTO;
import com.edusyspro.api.model.AcademicYear;

import java.time.LocalDate;
import java.util.List;

public interface TeachingReportService {
    void saveReport(TeachingReportDTO report);
    List<TeachingReportDTO> getBasicReport(String teacherId, LocalDate startDate, LocalDate endDate);
    TeachingReportDTO getDetailedReport(Long reportId);
    long getReportCountByTeacher(String teacherId, String academicYear);

    /**
     * Calculates the expected number of teaching reports by a specific teacher within a given academic year,
     * considering the provided holidays that fall within the academic year.
     *
     * @param teacherId the unique identifier of the teacher for whom the reports are being calculated
     * @param academicYear an object representing the academic year, including start and end dates,
     *                     as well as associated details like semesters and school
     * @param holidays a list of date ranges representing holidays that should be excluded
     *                 from the report calculation
     * @return the expected number of teaching reports for the specified teacher in the given academic year,
     *         adjusted for the holidays provided
     */
    int calculateExpectedReportsByTeacher(String teacherId, AcademicYearDTO academicYear, List<DateRange> holidays);
}
