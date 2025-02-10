package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.AttendanceDTO;
import com.edusyspro.api.dto.custom.AttendanceStatusCount;
import com.edusyspro.api.model.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AttendanceService {

    Page<AttendanceDTO> getLastStudentAttendances(long studentId, Pageable pageable);

    Page<AttendanceDTO> getStudentAttendancesByAcademicYear(long studentId, String academicYearId, Pageable pageable);

    List<AttendanceDTO> getStudentAttendances(long studentId, String academicYearId);

    List<AttendanceStatusCount> getStudentAttendanceCount(long studentId, String academicYearId);
    List<AttendanceStatusCount> getClasseAttendanceCount(int classeId, String academicYearId);
    List<AttendanceStatusCount> getSchoolAttendanceCount(String schoolId, String academicYearId);

}
