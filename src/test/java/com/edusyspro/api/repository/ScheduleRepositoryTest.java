package com.edusyspro.api.repository;

import com.edusyspro.api.classes.ClasseRepository;
import com.edusyspro.api.classes.ClasseEntity;
import com.edusyspro.api.entities.*;
import com.edusyspro.api.entities.enums.Day;
import com.edusyspro.api.school.entities.Schedule;
import com.edusyspro.api.school.entities.School;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void SavePrimarySchoolClassScheduleTest() {
        ClasseEntity classeEntity = getClasse(3);
        Teacher teacher = getTeacher(classeEntity.getName());
        List<Schedule> schedules = List.of(
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(teacher)
                        .dayOfWeek(Day.ALL_DAYS)
                        .designation("Première partie des cours")
                        .startTime(time(7, 30))
                        .endTime(time(9, 15))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.ALL_DAYS)
                        .designation("Première récréation")
                        .startTime(time(9, 15))
                        .endTime(time(9, 45))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(teacher)
                        .dayOfWeek(Day.ALL_DAYS)
                        .designation("Deuxième partie des cours")
                        .startTime(time(9, 45))
                        .endTime(time(11, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.ALL_DAYS)
                        .designation("Deuxième récréation")
                        .startTime(time(11, 0))
                        .endTime(time(11, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
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
        ClasseEntity classeEntity = getClasse(7);
        List<Schedule> schedules = List.of(
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacher(classeEntity.getName(), getCourse("FRA").getId()))
                        .course(getCourse("FRA"))
                        .dayOfWeek(Day.MONDAY)
                        .designation("Deux heures de Français")
                        .startTime(time(7, 0))
                        .endTime(time(9, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacher(classeEntity.getName(), getCourse("ECM").getId()))
                        .course(getCourse("ECM"))
                        .dayOfWeek(Day.MONDAY)
                        .designation("Une heure d'ECM")
                        .startTime(time(9, 0))
                        .endTime(time(10, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.MONDAY)
                        .designation("Récréation")
                        .startTime(time(10, 0))
                        .endTime(time(10, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacher(classeEntity.getName(), getCourse("PC").getId()))
                        .course(getCourse("PC"))
                        .dayOfWeek(Day.MONDAY)
                        .designation("Deux heures de Physique Chimie")
                        .startTime(time(10, 30))
                        .endTime(time(12, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacher(classeEntity.getName(), getCourse("Maths").getId()))
                        .course(getCourse("Maths"))
                        .dayOfWeek(Day.TUESDAY)
                        .designation("Trois heures de Maths")
                        .startTime(time(7, 0))
                        .endTime(time(10, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .dayOfWeek(Day.TUESDAY)
                        .designation("Récréation")
                        .startTime(time(10, 0))
                        .endTime(time(10, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacher(classeEntity.getName(), getCourse("SVT").getId()))
                        .course(getCourse("SVT"))
                        .dayOfWeek(Day.TUESDAY)
                        .designation("Deux heures des SVT")
                        .startTime(time(10, 30))
                        .endTime(time(12, 30))
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
                        .teacher(getTeacher(classeEntity.getName(), getCourse("HG").getId()))
                        .course(getCourse("HG"))
                        .dayOfWeek(Day.WEDNESDAY)
                        .designation("Deux heures de Histoire Géographie")
                        .startTime(time(7, 0))
                        .endTime(time(9, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacher(classeEntity.getName(), getCourse("EPS").getId()))
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
                        .teacher(getTeacher(classeEntity.getName(), getCourse("Dessin").getId()))
                        .course(getCourse("Dessin"))
                        .dayOfWeek(Day.WEDNESDAY)
                        .designation("Deux heures de Dessin")
                        .startTime(time(10, 30))
                        .endTime(time(12, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacher(classeEntity.getName(), getCourse("SVT").getId()))
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
                        .teacher(getTeacher(classeEntity.getName(), getCourse("PC").getId()))
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
                        .teacher(getTeacher(classeEntity.getName(), getCourse("FRA").getId()))
                        .course(getCourse("FRA"))
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Deux heures de Histoire Français")
                        .startTime(time(7, 0))
                        .endTime(time(9, 0))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacher(classeEntity.getName(), getCourse("Lat").getId()))
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
                        .teacher(getTeacher(classeEntity.getName(), getCourse("Musc").getId()))
                        .course(getCourse("Musc"))
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Une heure de Musique")
                        .startTime(time(10, 30))
                        .endTime(time(11, 30))
                        .build(),
                Schedule.builder()
                        .classeEntity(classeEntity)
                        .teacher(getTeacher(classeEntity.getName(), getCourse("Math").getId()))
                        .course(getCourse("Math"))
                        .dayOfWeek(Day.FRIDAY)
                        .designation("Une heure de Math")
                        .startTime(time(11, 30))
                        .endTime(time(12, 30))
                        .build()
        );
        scheduleRepository.saveAll(schedules);
    }

    private ClasseEntity getClasse(int id) {
        return classeRepository.getClasseById(id);
    }

    private Course getCourse(String abbr) {
        return courseRepository.getCourseByAbbrContainingIgnoreCase(abbr);
    }

    private Teacher getTeacher(String className) {
        return teacherRepository.getTeacherByClassesName(className);
    }

    private Teacher getTeacher(String className, int courseId) {
        return teacherRepository.getTeacherByClassesNameAndCourseId(className, courseId);
    }

    private LocalTime time(int hour, int minute) {
        return LocalTime.of(hour, minute);
    }

    private School getSchool() {
        Optional<School> school = schoolRepository.findById(UUID.fromString("19e8cf01-5098-453b-9d65-d57cd17fc548"));
        return school.orElseThrow();
    }

}