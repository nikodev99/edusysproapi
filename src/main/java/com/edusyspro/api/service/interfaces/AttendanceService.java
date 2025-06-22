package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.AttendanceDTO;
import com.edusyspro.api.dto.custom.AttendanceStatusCount;
import com.edusyspro.api.dto.custom.AttendanceStatusStat;
import com.edusyspro.api.dto.custom.IndividualAttendanceSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AttendanceService {
    Boolean saveAllAttendances(List<AttendanceDTO> attendances, int schoolId, LocalDate attendanceDate);
    Boolean updateAllAttendances(List<AttendanceDTO> attendances);

    Page<AttendanceDTO> getLastStudentAttendances(long studentId, Pageable pageable);
    Page<AttendanceDTO> getStudentAttendancesByAcademicYear(long studentId, String academicYearId, Pageable pageable);
    List<AttendanceDTO> getStudentAttendances(long studentId, String academicYearId);
    AttendanceStatusCount getStudentAttendanceCount(long studentId, String academicYearId);

    List<AttendanceDTO> getAllClasseAttendancesPerDate(int classeId, String academicYearId, LocalDate date);
    AttendanceStatusCount getClasseAttendanceCount(int classeId, String academicYearId, LocalDate date);
    AttendanceStatusCount getSchoolAttendanceCount(String academicYearId, LocalDate date);

    List<AttendanceDTO> getAllSchoolAttendances(String schoolId, String academicYearId);
    Page<AttendanceDTO> getAllSchoolPerDateAttendances(String schoolId, String academicYearId, LocalDate date, Pageable pageable);
    Page<AttendanceDTO> getAllSchoolPerDateAttendances(String schoolId, String academicYearId, LocalDate date, String searchable, Pageable pageable);

    List<IndividualAttendanceSummary> getBestStudentAtAttendance(int classeId, String academicYearId);
    List<IndividualAttendanceSummary> getWorstStudentAtAttendance(int classeId, String academicYearId);
    List<IndividualAttendanceSummary> getStudentAtAttendanceRanking(String schoolId, String academicYearId, boolean isWorst);

    Page<IndividualAttendanceSummary> getStudentAttendanceStatus(int classeId, String academicYearId, Pageable pageable);
    List<IndividualAttendanceSummary> getStudentAttendanceStatus(int classeId, String academicYearId, String name);

    List<AttendanceStatusStat> getClasseAttendanceStats(int classeId, String academicYearId, LocalDate startDate, LocalDate endDate);
    List<AttendanceStatusStat> getSchoolAttendanceStats(String schoolId, String academicYearId, LocalDate startDate, LocalDate endDate);

    Integer getNumberOfClasseDays(int classeId, String academicYearId);
}
