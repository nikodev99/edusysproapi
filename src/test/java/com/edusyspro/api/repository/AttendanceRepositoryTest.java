package com.edusyspro.api.repository;

import com.edusyspro.api.model.Attendance;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.AttendanceStatus;
import com.edusyspro.api.utils.MockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class AttendanceRepositoryTest {

    @Autowired
    private AttendanceRepository attendanceRepository;

    private static final Random random = new Random();

    @Test
    public void insertIntoAttendance () {
        List<LocalDate> dates = getDates();
        List<Attendance> attendances = new ArrayList<>();

        long[] students = new long[]{84, 86, 206, 82, 216, 100, 88, 208, 90, 204, 98, 92, 412, 220, 218, 96, 212, 202, 94, 210};

        for (long student : students) {
            for (LocalDate date : dates) {
                attendances.add(Attendance.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK)
                        .individual(Individual.builder()
                                .id(student)
                                .build())
                        .attendanceDate(date)
                        .status(getStatus())
                        .classeEntity(MockUtils.THREE)
                        .build());
            }
        }

        attendanceRepository.saveAll(attendances);
    }

    private List<LocalDate> getDates () {
        LocalDate today = LocalDate.now();
        LocalDate lastMonday = today.with(DayOfWeek.MONDAY).minusWeeks(1);
        List<LocalDate> dates = new ArrayList<>();

        for (int i = 0; i < 5; i++) { // Loop from Monday to Friday
            dates.add(lastMonday.plusDays(i));
        }

        return List.of(dates.toArray(new LocalDate[0]));
    }

    private AttendanceStatus getStatus () {
        int roll = random.nextInt(100);
        if (roll < 40) {
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
