package com.edusyspro.api.service.impl;

import com.edusyspro.api.helper.log.L;
import com.edusyspro.api.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class EnrollmentArchiver {

    private final EnrollmentRepository enrollmentRepository;

    @Scheduled(cron = "0 0 2 * * MON")
    public void archiveEndedAcademicYears() {
        int updated = enrollmentRepository.archiveByAcademicYearEnd(LocalDate.now());
        L.info("Archivage automatique : {} enrollments marqués comme archived.", updated);
    }

}
