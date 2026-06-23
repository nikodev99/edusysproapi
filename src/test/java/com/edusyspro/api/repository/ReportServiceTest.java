package com.edusyspro.api.repository;

import com.edusyspro.api.dto.TeachingReportDTO;
import com.edusyspro.api.service.interfaces.TeachingReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class ReportServiceTest {
    @Autowired
    private TeachingReportService teachingReportService;

    @Test
    public void getWeekReport() {
        List<TeachingReportDTO> reports = teachingReportService.getBasicReport(
                "74180dff-9f30-4872-b5c2-25acf66c3006",
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 6, 6)
        );

        System.out.println("--------------------------------------");
        System.out.println(reports);
        System.out.println("--------------------------------------");
    }
}
