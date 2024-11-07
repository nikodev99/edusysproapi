package com.edusyspro.api.utils;

import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.model.*;
import com.edusyspro.api.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MockUtils {

    public static School SCHOOL_MOCK;
    public static AcademicYear ACADEMIC_YEAR_MOCK;
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
       SCHOOL_MOCK = schoolRepository.getSchoolById(UUID.fromString(ConstantUtils.SCHOOL_ID));
       ACADEMIC_YEAR_MOCK = academicYearRepository.findAcademicYearBySchoolIdAndCurrentIsTrue(UUID.fromString(ConstantUtils.SCHOOL_ID));
       FIRST_SEMESTER = semesterRepository.findById(1).orElseThrow();
       SECOND_SEMESTER = semesterRepository.findById(2).orElseThrow();
       THIRD_SEMESTER = semesterRepository.findById(3).orElseThrow();
       CP1 = classeRepository.findById(1).orElseThrow();
       CP2 = classeRepository.findById(2).orElseThrow();
       CE1 = classeRepository.findById(3).orElseThrow();
       CE2 = classeRepository.findById(4).orElseThrow();
       CM1 = classeRepository.findById(5).orElseThrow();
       CM2 = classeRepository.findById(6).orElseThrow();
       SIX = classeRepository.findById(7).orElseThrow();
       FIVE = classeRepository.findById(8).orElseThrow();
       FOUR = classeRepository.findById(9).orElseThrow();
       THREE = classeRepository.findById(10).orElseThrow();
       SECONDA = classeRepository.findById(11).orElseThrow();
       STC = classeRepository.findById(12).orElseThrow();
       FIRSTA = classeRepository.findById(13).orElseThrow();
       FIRSTS = classeRepository.findById(14).orElseThrow();
       TERA = classeRepository.findById(15).orElseThrow();
       TERD = classeRepository.findById(16).orElseThrow();
       TERC = classeRepository.findById(17).orElseThrow();
       PHILO = courseRepository.findById(1).orElseThrow();
       MATH = courseRepository.findById(2).orElseThrow();
       SVT = courseRepository.findById(3).orElseThrow();
       PC = courseRepository.findById(4).orElseThrow();
       HG = courseRepository.findById(5).orElseThrow();
       ANG = courseRepository.findById(6).orElseThrow();
       EPS = courseRepository.findById(7).orElseThrow();
       TECH = courseRepository.findById(8).orElseThrow();
       DES = courseRepository.findById(9).orElseThrow();
       MUSC = courseRepository.findById(10).orElseThrow();
       ECM = courseRepository.findById(11).orElseThrow();
       LAT = courseRepository.findById(12).orElseThrow();
       RUS = courseRepository.findById(13).orElseThrow();
       ESP = courseRepository.findById(14).orElseThrow();
       INF = courseRepository.findById(15).orElseThrow();
       FRA = courseRepository.findById(16).orElseThrow();
   }
}
