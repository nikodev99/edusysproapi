package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.custom.AttendanceEssential;
import com.edusyspro.api.model.Attendance;
import com.edusyspro.api.repository.AttendanceRepository;
import com.edusyspro.api.service.interfaces.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public Page<Attendance> getLastStudentAttendances(String studentId, Pageable pageable) {
        return attendanceRepository.findAttendanceByStudentId(UUID.fromString(studentId), pageable);
    }

    @Override
    public Page<Attendance> getStudentAttendancesByAcademicYear(String studentId, String academicYearId, Pageable pageable) {
        return attendanceRepository.findAttendanceByStudentEntityIdAndAcademicYearIdOrderByAttendanceDateDesc(
                UUID.fromString(studentId),
                UUID.fromString(academicYearId),
                pageable
        );
    }

    @Override
    public List<Attendance> getStudentAttendances(String studentId, String academicYearId) {
        List<AttendanceEssential> attendanceList = attendanceRepository.findAllByStudentEntityIdAndAcademicYearId(
                UUID.fromString(studentId), UUID.fromString(academicYearId)
        );
        return attendanceList.stream()
                .map(AttendanceEssential::populate)
                .toList();
    }

}
