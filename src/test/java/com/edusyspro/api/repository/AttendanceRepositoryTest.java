package com.edusyspro.api.repository;

import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.dto.EnrollmentDTO;
import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.Attendance;
import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.AttendanceStatus;
import com.edusyspro.api.service.interfaces.EnrollmentService;
import com.edusyspro.api.utils.MockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
public class AttendanceRepositoryTest {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EnrollmentService enrollmentService;

    private static final Random random = new Random();

    @Test
    public void insertTodayAttendance () {
        String academicYear = ConstantUtils.ACADEMIC_YEAR;
        ClasseEntity classe = MockUtils.TERC;
        List<Attendance> attendances = new ArrayList<>();
        List<EnrollmentDTO> students = enrollmentService.getClasseEnrolledStudents(
                classe.getId(), academicYear, PageRequest.of(0, 21)
        ).toList();

        students.forEach(student -> attendances.add(Attendance.builder()
                .academicYear(AcademicYear.builder()
                        .id(UUID.fromString(academicYear))
                        .build())
                .individual(Individual.builder()
                        .id(student.getStudent().getPersonalInfo().getId())
                        .build())
                .attendanceDate(LocalDate.now())
                .status(getStatus())
                .classeEntity(classe)
                .build()));

        attendanceRepository.saveAll(attendances);
    }

    @Test
    public void insertIntoAttendance () {
        List<LocalDate> dates = getDates();
        List<Attendance> attendances = new ArrayList<>();
        List<EnrollmentDTO> dtoList = enrollmentService.getClasseEnrolledStudents(
                17,
                String.valueOf(MockUtils.ACADEMIC_YEAR_MOCK.getId()),
                PageRequest.of(0, 21)).toList();

        List<Long> students = dtoList.stream()
                .map(s -> s.getStudent().getPersonalInfo().getId())
                .toList();

        for (long student : students) {
            for (LocalDate date : dates) {
                attendances.add(Attendance.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK)
                        .individual(Individual.builder()
                                .id(student)
                                .build())
                        .attendanceDate(date)
                        .status(getStatus())
                        .classeEntity(MockUtils.TERC)
                        .build());
            }
        }
        attendanceRepository.saveAll(attendances);
    }

    private List<LocalDate> getDates () {
        LocalDate today = LocalDate.of(2025, 4, 1);
        LocalDate lastMonday = today.with(DayOfWeek.MONDAY).plusWeeks(4);
        List<LocalDate> dates = new ArrayList<>();

        for (int i = 0; i < 5; i++) { // Loop from Monday to Friday
            dates.add(lastMonday.plusDays(i));
        }

        return List.of(dates.toArray(new LocalDate[0]));
    }

    private AttendanceStatus getStatus () {
        int roll = random.nextInt(100);
        if (roll < 70) {
            return AttendanceStatus.PRESENT;
        } else if (roll < 80) {
            return AttendanceStatus.ABSENT;
        } else if (roll < 90) {
            return AttendanceStatus.LATE;
        }else {
            return AttendanceStatus.EXCUSED;
        }
    }

}
