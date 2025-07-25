package com.edusyspro.api.repository;

import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.enums.Day;
import com.edusyspro.api.model.Course;
import com.edusyspro.api.model.Teacher;
import com.edusyspro.api.model.Schedule;
import com.edusyspro.api.utils.MockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.List;

@SpringBootTest
class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void SavePrimarySchoolClassScheduleTest() {
        ClasseEntity classe = MockUtils.CM2;
        Teacher teacher = getTeacherByClasse(classe);
        List<Schedule> schedules = List.of(
                Schedule.builder()
                        .classeEntity(classe)
                        .teacher(teacher)
                        .dayOfWeek(Day.ALL_DAYS)
                        .designation("Première partie des cours")
                        .startTime(time(7, 30))
                        .endTime(time(9, 15))
                        .build(),
                Schedule.builder()
                        .classeEntity(classe)
                        .teacher(teacher)
                        .dayOfWeek(Day.ALL_DAYS)
                        .designation("Première récréation")
                        .startTime(time(9, 15))
                        .endTime(time(9, 45))
                        .build(),
                Schedule.builder()
                        .classeEntity(classe)
                        .teacher(teacher)
                        .dayOfWeek(Day.ALL_DAYS)
                        .designation("Deuxième partie des cours")
                        .startTime(time(9, 45))
                        .endTime(time(11, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classe)
                        .teacher(teacher)
                        .dayOfWeek(Day.ALL_DAYS)
                        .designation("Deuxième récréation")
                        .startTime(time(11, 0))
                        .endTime(time(11, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classe)
                        .teacher(teacher)
                        .dayOfWeek(Day.ALL_DAYS)
                        .designation("Troisième partie des cours")
                        .startTime(time(11, 30))
                        .endTime(time(12, 30))
                        .build()
        );
        scheduleRepository.saveAll(schedules);
    }

    @Test
    public void SaveSecondarySchoolClassScheduleTest() {
        ClasseEntity classeEntity = MockUtils.STC;
        List<Schedule> schedules = List.of(
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.MATH))
                        .course(MockUtils.MATH)
                        .dayOfWeek(Day.MONDAY)
                        .designation("Deux heures de math")
                        .startTime(time(12, 0))
                        .endTime(time(14, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.MONDAY)
                        .designation("Récréation")
                        .startTime(time(14, 0))
                        .endTime(time(14, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.PC))
                        .course(MockUtils.PC)
                        .dayOfWeek(Day.MONDAY)
                        .designation("Deux heure de Physique Chimie")
                        .startTime(time(14, 30))
                        .endTime(time(16, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.FRA))
                        .course(MockUtils.FRA)
                        .dayOfWeek(Day.MONDAY)
                        .designation("Une heure de Français")
                        .startTime(time(16, 30))
                        .endTime(time(17, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.SVT))
                        .course(MockUtils.SVT)
                        .dayOfWeek(Day.TUESDAY)
                        .designation("Deux heures de SVT")
                        .startTime(time(12, 0))
                        .endTime(time(14, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.TUESDAY)
                        .designation("Récréation")
                        .startTime(time(14, 0))
                        .endTime(time(14, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.ANG))
                        .course(MockUtils.ANG)
                        .dayOfWeek(Day.TUESDAY)
                        .designation("Deux heures d'Anglais")
                        .startTime(time(14, 30))
                        .endTime(time(16, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.HG))
                        .course(MockUtils.HG)
                        .dayOfWeek(Day.TUESDAY)
                        .designation("Une heures d'Histoire Géo")
                        .startTime(time(16, 30))
                        .endTime(time(17, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.MATH))
                        .course(MockUtils.MATH)
                        .dayOfWeek(Day.WEDNESDAY)
                        .designation("Trois heure de Maths")
                        .startTime(time(12, 0))
                        .endTime(time(15, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.WEDNESDAY)
                        .designation("Récréation")
                        .startTime(time(15, 0))
                        .endTime(time(15, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.EPS))
                        .course(MockUtils.EPS)
                        .dayOfWeek(Day.WEDNESDAY)
                        .designation("Deux heures de d'EPS")
                        .startTime(time(15, 30))
                        .endTime(time(17, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.SVT))
                        .course(MockUtils.SVT)
                        .dayOfWeek(Day.THURSDAY)
                        .designation("Deux heures de SVT")
                        .startTime(time(12, 0))
                        .endTime(time(14, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.FRA))
                        .course(MockUtils.FRA)
                        .dayOfWeek(Day.THURSDAY)
                        .designation("Une heure de de français")
                        .startTime(time(14, 0))
                        .endTime(time(15, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.THURSDAY)
                        .designation("Récréation")
                        .startTime(time(15, 0))
                        .endTime(time(15, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.ESP))
                        .course(MockUtils.ESP)
                        .dayOfWeek(Day.THURSDAY)
                        .designation("Deux heures d'Espagnol")
                        .startTime(time(15, 30))
                        .endTime(time(17, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.MATH))
                        .course(MockUtils.MATH)
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Une heure de Math")
                        .startTime(time(12, 0))
                        .endTime(time(13, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.ANG))
                        .course(MockUtils.ANG)
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Une heure d'anglais")
                        .startTime(time(13, 0))
                        .endTime(time(14, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.HG))
                        .course(MockUtils.HG)
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Une heures d'Histoire-Géo")
                        .startTime(time(14, 0))
                        .endTime(time(15, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Récréation")
                        .startTime(time(15, 0))
                        .endTime(time(15, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.PC))
                        .course(MockUtils.PC)
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Deux heures de physique-chimie")
                        .startTime(time(15, 30))
                        .endTime(time(17, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.PC))
                        .course(MockUtils.PC)
                        .dayOfWeek(Day.SATURDAY)
                        .designation("Une heure de physique-chimie")
                        .startTime(time(8, 0))
                        .endTime(time(9, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.FRA))
                        .course(MockUtils.FRA)
                        .dayOfWeek(Day.SATURDAY)
                        .designation("Deux heure de Français")
                        .startTime(time(9, 0))
                        .endTime(time(11, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.SATURDAY)
                        .designation("Récréation")
                        .startTime(time(11, 0))
                        .endTime(time(11, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, MockUtils.HG))
                        .course(MockUtils.HG)
                        .dayOfWeek(Day.SATURDAY)
                        .designation("Deux heure d'EPS")
                        .startTime(time(11, 30))
                        .endTime(time(13, 30))
                        .build()
        );
        scheduleRepository.saveAll(schedules);
    }

    @Test
    public void SaveThirdSecondarySchoolClassScheduleTest() {
        ClasseEntity classeEntity = MockUtils.THREE;
        Course[] courses = new Course[]{
                MockUtils.FRA, MockUtils.MATH, MockUtils.PC, MockUtils.ANG, MockUtils.HG,
                MockUtils.SVT, MockUtils.EPS,
        };
        List<Schedule> schedules = List.of(
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, courses[4]))
                        .course(courses[4])
                        .dayOfWeek(Day.MONDAY)
                        .designation("Trois d'Histoire Géographie")
                        .startTime(time(8, 0))
                        .endTime(time(11, 0))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.MONDAY)
                        .designation("Récréation")
                        .startTime(time(11, 0))
                        .endTime(time(11, 30))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, courses[6]))
                        .course(courses[6])
                        .dayOfWeek(Day.MONDAY)
                        .designation("Deux heures d'EPS")
                        .startTime(time(11, 30))
                        .endTime(time(13, 30))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, courses[0]))
                        .course(courses[0])
                        .dayOfWeek(Day.TUESDAY)
                        .designation("Trois heures de Français")
                        .startTime(time(8, 0))
                        .endTime(time(11, 0))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.TUESDAY)
                        .designation("Récréation")
                        .startTime(time(11, 0))
                        .endTime(time(11, 30))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, courses[5]))
                        .course(courses[5])
                        .dayOfWeek(Day.TUESDAY)
                        .designation("Deux heures de SVT")
                        .startTime(time(11, 30))
                        .endTime(time(13, 30))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, courses[2]))
                        .course(courses[2])
                        .dayOfWeek(Day.WEDNESDAY)
                        .designation("Trois heures de Science Physique")
                        .startTime(time(8, 0))
                        .endTime(time(11, 0))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.WEDNESDAY)
                        .designation("Récréation")
                        .startTime(time(11, 0))
                        .endTime(time(11, 30))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, courses[4]))
                        .course(courses[4])
                        .dayOfWeek(Day.WEDNESDAY)
                        .designation("Deux heures de d'histoire géographie")
                        .startTime(time(11, 30))
                        .endTime(time(13, 30))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, courses[1]))
                        .course(courses[1])
                        .dayOfWeek(Day.THURSDAY)
                        .designation("Trois heures de Math")
                        .startTime(time(8, 0))
                        .endTime(time(11, 0))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.THURSDAY)
                        .designation("Récréation")
                        .startTime(time(11, 0))
                        .endTime(time(11, 30))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, courses[0]))
                        .course(courses[0])
                        .dayOfWeek(Day.THURSDAY)
                        .designation("Deux heures de Français")
                        .startTime(time(11, 30))
                        .endTime(time(13, 30))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, courses[5]))
                        .course(courses[5])
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Trois heures d'SVT")
                        .startTime(time(8, 0))
                        .endTime(time(11, 0))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Récréation")
                        .startTime(time(11, 0))
                        .endTime(time(11, 30))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, courses[2]))
                        .course(courses[2])
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Deux heures de Science Physique")
                        .startTime(time(11, 30))
                        .endTime(time(13, 30))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, courses[3]))
                        .course(courses[3])
                        .dayOfWeek(Day.SATURDAY)
                        .designation("Trois heures d'Anglais")
                        .startTime(time(8, 0))
                        .endTime(time(10, 0))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.SATURDAY)
                        .designation("Récréation")
                        .startTime(time(11, 0))
                        .endTime(time(11, 30))
                        .build(),
                Schedule.builder()
                        .academicYear(MockUtils.ACADEMIC_YEAR_MOCK.toEntity())
                        .classeEntity(classeEntity)
                        .teacher(getTeacherByClasseAndCourse(classeEntity, courses[1]))
                        .course(courses[1])
                        .dayOfWeek(Day.SATURDAY)
                        .designation("Deux heure de Mathématique")
                        .startTime(time(11, 30))
                        .endTime(time(13, 30))
                        .build()
        );
        scheduleRepository.saveAll(schedules);
    }

    @Test
    public void saveSchedules() {
        ClasseEntity classeEntity = getClasse(7);
        List<Schedule> schedules = List.of(
                Schedule.builder()
                        .classeEntity(classeEntity)
                        //.teacher(getTeacher(classeEntity.getName(), getCourse("HG").getId()))
                        .course(getCourse("HG"))
                        .dayOfWeek(Day.WEDNESDAY)
                        .designation("Deux heures de Histoire Géographie")
                        .startTime(time(7, 0))
                        .endTime(time(9, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        //.teacher(getTeacher(classeEntity.getName(), getCourse("EPS").getId()))
                        .course(getCourse("EPS"))
                        .dayOfWeek(Day.WEDNESDAY)
                        .designation("Une heure d'EPS")
                        .startTime(time(9, 0))
                        .endTime(time(10, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.WEDNESDAY)
                        .designation("Récréation")
                        .startTime(time(10, 0))
                        .endTime(time(10, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        //.teacher(getTeacher(classeEntity.getName(), getCourse("Dessin").getId()))
                        .course(getCourse("Dessin"))
                        .dayOfWeek(Day.WEDNESDAY)
                        .designation("Deux heures de Dessin")
                        .startTime(time(10, 30))
                        .endTime(time(12, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        //.teacher(getTeacher(classeEntity.getName(), getCourse("SVT").getId()))
                        .course(getCourse("SVT"))
                        .dayOfWeek(Day.THURSDAY)
                        .designation("Trois heures de biologie")
                        .startTime(time(7, 0))
                        .endTime(time(10, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.THURSDAY)
                        .designation("Récréation")
                        .startTime(time(10, 0))
                        .endTime(time(10, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        //.teacher(getTeacher(classeEntity.getName(), getCourse("PC").getId()))
                        .course(getCourse("PC"))
                        .dayOfWeek(Day.THURSDAY)
                        .designation("Deux heures des Physique Chimie")
                        .startTime(time(10, 30))
                        .endTime(time(12, 30))
                        .build()
        );
        scheduleRepository.saveAll(schedules);
    }

    @Test
    public void saveSchedulesExt() {
        ClasseEntity classeEntity = getClasse(11);
        List<Schedule> schedules = List.of(
                Schedule.builder()
                        .classeEntity(classeEntity)
                        //.teacher(getTeacher(classeEntity.getName(), getCourse("FRA").getId()))
                        .course(getCourse("FRA"))
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Deux heures de Histoire Français")
                        .startTime(time(7, 0))
                        .endTime(time(9, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        //.teacher(getTeacher(classeEntity.getName(), getCourse("Lat").getId()))
                        .course(getCourse("Lat"))
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Une heure de Latin")
                        .startTime(time(9, 0))
                        .endTime(time(10, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Récréation")
                        .startTime(time(10, 0))
                        .endTime(time(10, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        //.teacher(getTeacher(classeEntity.getName(), getCourse("Musc").getId()))
                        .course(getCourse("Musc"))
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Une heure de Musique")
                        .startTime(time(10, 30))
                        .endTime(time(11, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        //.teacher(getTeacher(classeEntity.getName(), getCourse("Math").getId()))
                        .course(getCourse("Math"))
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Une heure de Math")
                        .startTime(time(11, 30))
                        .endTime(time(12, 30))
                        .build()
        );
        scheduleRepository.saveAll(schedules);
    }

    private Teacher getTeacherByClasse(ClasseEntity classe) {
        return teacherRepository.findTeacherByClasseId(classe.getId()).orElse(null);
    }

    private Teacher getTeacherByClasseAndCourse(ClasseEntity classe, Course course) {
        return teacherRepository.findTeacherByClasseIdAndCourseId(classe.getId(), course.getId()).orElse(null);
    }

    private ClasseEntity getClasse(int id) {
        return classeRepository.getClasseById(id);
    }

    private Course getCourse(String abbr) {
        return courseRepository.getCourseByAbbrContainingIgnoreCase(abbr);
    }

    private LocalTime time(int hour, int minute) {
        return LocalTime.of(hour, minute);
    }

}