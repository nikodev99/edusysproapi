package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.AttendanceDTO;
import com.edusyspro.api.dto.custom.AttendanceEssential;
import com.edusyspro.api.dto.custom.AttendanceStatusCount;
import com.edusyspro.api.model.Attendance;
import com.edusyspro.api.model.enums.AttendanceStatus;
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
    public Page<AttendanceDTO> getLastStudentAttendances(long studentId, Pageable pageable) {
        return attendanceRepository.findAttendanceByStudentId(studentId, pageable)
                .map(AttendanceEssential::populate);
    }

    @Override
    public Page<AttendanceDTO> getStudentAttendancesByAcademicYear(long studentId, String academicYearId, Pageable pageable) {
        return attendanceRepository.findAttendanceByStudentAndAcademicYear(
                studentId,
                UUID.fromString(academicYearId),
                pageable
        ).map(AttendanceEssential::populate);
    }

    @Override
    public List<AttendanceDTO> getStudentAttendances(long studentId, String academicYearId) {
        return attendanceRepository.findAllByStudentAndAcademicYear(studentId, UUID.fromString(academicYearId))
                .stream()
                .map(AttendanceEssential::populate)
                .toList();
    }

    @Override
    public List<AttendanceStatusCount> getStudentAttendanceCount(long studentId, String academicYearId) {
        return attendanceRepository.findAttendanceStatusCountByStudent(studentId, UUID.fromString(academicYearId))
                .stream()
                .map(o -> new AttendanceStatusCount((AttendanceStatus) o[0], (long) o[1]))
                .toList();
    }

    @Override
    public List<AttendanceStatusCount> getClasseAttendanceCount(int classeId, String academicYearId) {
        return attendanceRepository.findAttendanceStatusCountByClasse(classeId, UUID.fromString(academicYearId))
                .stream()
                .map(o -> new AttendanceStatusCount((AttendanceStatus) o[0], (long) o[1]))
                .toList();
    }

    @Override
    public List<AttendanceStatusCount> getSchoolAttendanceCount(String schoolId, String academicYearId) {
        return attendanceRepository.findAttendanceStatusCountBySchool(UUID.fromString(schoolId), UUID.fromString(academicYearId))
                .stream()
                .map(o -> new AttendanceStatusCount((AttendanceStatus) o[0], (long) o[1]))
                .toList();
    }
}
