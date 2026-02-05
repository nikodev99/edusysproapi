package com.edusyspro.api.utils;

import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.dto.AcademicYearDTO;
import com.edusyspro.api.model.*;
import com.edusyspro.api.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MockUtils {

    public static School SCHOOL_MOCK;
    public static AcademicYearDTO ACADEMIC_YEAR_MOCK;
    public static Semester FIRST_SEMESTER;
    public static Semester SECOND_SEMESTER;
    public static Semester THIRD_SEMESTER;
    public static ClasseEntity CP1;
    public static ClasseEntity CP2;
    public static ClasseEntity CE1;
    public static ClasseEntity CE2;
    public static ClasseEntity CM1;
    public static ClasseEntity CM2;
    public static ClasseEntity SIX;
    public static ClasseEntity FIVE;
    public static ClasseEntity FOUR;
    public static ClasseEntity THREE;
    public static ClasseEntity SECONDA;
    public static ClasseEntity STC;
    public static ClasseEntity FIRSTA;
    public static ClasseEntity FIRSTS;
    public static ClasseEntity TERA;
    public static ClasseEntity TERC;
    public static ClasseEntity TERD;
    public static Course PHILO;
    public static Course MATH;
    public static Course SVT;
    public static Course PC;
    public static Course HG;
    public static Course ANG;
    public static Course EPS;
    public static Course TECH;
    public static Course DES;
    public static Course MUSC;
    public static Course ECM;
    public static Course LAT;
    public static Course RUS;
    public static Course ESP;
    public static Course INF;
    public static Course FRA;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private AcademicYearRepository academicYearRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PostConstruct
    private void init() {
       SCHOOL_MOCK = schoolRepository.findById(UUID.fromString(ConstantUtils.SCHOOL_ID)).orElse(null);
       ACADEMIC_YEAR_MOCK = academicYearRepository.findAcademicYearBySchoolIdAndCurrentIsTrue(UUID.fromString(ConstantUtils.SCHOOL_ID));
       FIRST_SEMESTER = semesterRepository.findById(1).orElse(null);
       SECOND_SEMESTER = semesterRepository.findById(2).orElse(null);
       THIRD_SEMESTER = semesterRepository.findById(3).orElse(null);
       CP1 = classeRepository.findById(1).orElse(null);
       CP2 = classeRepository.findById(2).orElse(null);
       CE1 = classeRepository.findById(3).orElse(null);
       CE2 = classeRepository.findById(4).orElse(null);
       CM1 = classeRepository.findById(5).orElse(null);
       CM2 = classeRepository.findById(6).orElse(null);
       SIX = classeRepository.findById(7).orElse(null);
       FIVE = classeRepository.findById(8).orElse(null);
       FOUR = classeRepository.findById(9).orElse(null);
       THREE = classeRepository.findById(10).orElse(null);
       SECONDA = classeRepository.findById(11).orElse(null);
       STC = classeRepository.findById(12).orElse(null);
       FIRSTA = classeRepository.findById(13).orElse(null);
       FIRSTS = classeRepository.findById(14).orElse(null);
       TERA = classeRepository.findById(15).orElse(null);
       TERD = classeRepository.findById(16).orElse(null);
       TERC = classeRepository.findById(17).orElse(null);
       PHILO = courseRepository.findById(1).orElse(null);
       MATH = courseRepository.findById(2).orElse(null);
       SVT = courseRepository.findById(3).orElse(null);
       PC = courseRepository.findById(4).orElse(null);
       HG = courseRepository.findById(5).orElse(null);
       ANG = courseRepository.findById(6).orElse(null);
       EPS = courseRepository.findById(7).orElse(null);
       TECH = courseRepository.findById(8).orElse(null);
       DES = courseRepository.findById(9).orElse(null);
       MUSC = courseRepository.findById(10).orElse(null);
       ECM = courseRepository.findById(11).orElse(null);
       LAT = courseRepository.findById(12).orElse(null);
       RUS = courseRepository.findById(13).orElse(null);
       ESP = courseRepository.findById(14).orElse(null);
       INF = courseRepository.findById(15).orElse(null);
       FRA = courseRepository.findById(16).orElse(null);
   }
}
