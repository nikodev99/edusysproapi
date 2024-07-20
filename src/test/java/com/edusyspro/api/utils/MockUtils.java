package com.edusyspro.api.utils;

import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.repository.SchoolRepository;
import com.edusyspro.api.school.entities.AcademicYear;
import com.edusyspro.api.school.entities.School;
import com.edusyspro.api.school.repos.AcademicYearRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MockUtils {

    public static School SCHOOL_MOCK;
    public static AcademicYear ACADEMIC_YEAR_MOCK;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private AcademicYearRepository academicYearRepository;

    @PostConstruct
    private void init() {
       SCHOOL_MOCK = schoolRepository.getSchoolById(UUID.fromString(ConstantUtils.SCHOOL_ID));
       ACADEMIC_YEAR_MOCK = academicYearRepository.findAcademicYearBySchoolIdAndCurrentIsTrue(UUID.fromString(ConstantUtils.SCHOOL_ID));
   }
}
