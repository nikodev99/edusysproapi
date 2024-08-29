package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.model.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttendanceService {

    Page<Attendance> getLastStudentAttendances(String schoolID, Pageable pageable);

    Page<Attendance> getStudentAttendancesByAcademicYear(String schoolID, String academicYearId, Pageable pageable);

}
