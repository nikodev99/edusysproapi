package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.model.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AttendanceService {

    Page<Attendance> getLastStudentAttendances(String studentId, Pageable pageable);

    Page<Attendance> getStudentAttendancesByAcademicYear(String studentId, String academicYearId, Pageable pageable);

    List<Attendance> getStudentAttendances(String studentId, String academicYearId);

}
