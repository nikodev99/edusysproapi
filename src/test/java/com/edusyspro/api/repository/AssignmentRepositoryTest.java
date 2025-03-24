package com.edusyspro.api.repository;

import com.edusyspro.api.model.*;
import com.edusyspro.api.model.enums.AssignmentType;
import com.edusyspro.api.utils.MockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
public class AssignmentRepositoryTest {

    @Autowired
    private AssignmentRepository repository;

    @Autowired
    private TeacherRepository  teacherRepository;

    @Autowired
    private PlanningRepository planningRepository;

    @Test
    public void addAssignment() {

        ClasseEntity classe = MockUtils.STC;
        Teacher mathTeacher = getTeacherByClasseAndCourse(classe, MockUtils.MATH);
        Teacher pcTeacher = getTeacherByClasseAndCourse(classe, MockUtils.PC);
        Teacher fraTeacher = getTeacherByClasseAndCourse(classe, MockUtils.FRA);
        Teacher svtTeacher = getTeacherByClasseAndCourse(classe, MockUtils.SVT);
        Teacher angTeacher = getTeacherByClasseAndCourse(classe, MockUtils.ANG);
        Teacher hgTeacher = getTeacherByClasseAndCourse(classe, MockUtils.HG);
        Teacher epsTeacher = getTeacherByClasseAndCourse(classe, MockUtils.EPS);
        Teacher espTeacher = getTeacherByClasseAndCourse(classe, MockUtils.ESP);

        Assignment assignmentMath = Assignment.builder()
                .semester(Planning.builder()
                        .id(14)
                        .build())
                .exam(Exam.builder()
                        .id(3)
                        .build())
                .preparedBy(Individual.builder()
                        .id(mathTeacher.getPersonalInfo().getId())
                        .build())
                .classeEntity(classe)
                .type(AssignmentType.CLASSROOM_ASSIGNMENT)
                .subject(MockUtils.MATH)
                .examName("Premier Devoir de Mathématique du 3e Trimestre")
                .examDate(LocalDate.of(2025, 3, 24))
                .startTime(LocalTime.of(12, 0))
                .endTime(LocalTime.of(15, 0))
                .passed(false)
                .build();

        /*Assignment assignmentPC = Assignment.builder()
                .semester(Planning.builder()
                        .id(12)
                        .build())
                .exam(Exam.builder()
                        .id(2)
                        .build())
                .preparedBy(Individual.builder()
                        .id(pcTeacher.getPersonalInfo().getId())
                        .build())
                .classeEntity(classe)
                .type(AssignmentType.EXAMINATION_ASSIGNMENT)
                .subject(MockUtils.PC)
                .examName("Composition de Physique-Chimie du 2e Trimestre")
                .examDate(LocalDate.of(2025, 3, 25))
                .startTime(LocalTime.of(12, 0))
                .endTime(LocalTime.of(15, 0))
                .passed(true)
                .build();

        Assignment assignmentFra = Assignment.builder()
                .semester(Planning.builder()
                        .id(12)
                        .build())
                .exam(Exam.builder()
                        .id(2)
                        .build())
                .preparedBy(Individual.builder()
                        .id(fraTeacher.getPersonalInfo().getId())
                        .build())
                .classeEntity(classe)
                .type(AssignmentType.EXAMINATION_ASSIGNMENT)
                .subject(MockUtils.FRA)
                .examName("Composition de Français du 2e Trimestre")
                .examDate(LocalDate.of(2025, 3, 26))
                .startTime(LocalTime.of(12, 0))
                .endTime(LocalTime.of(14, 0))
                .passed(true)
                .build();

        Assignment assignmentSvt = Assignment.builder()
                .semester(Planning.builder()
                        .id(12)
                        .build())
                .exam(Exam.builder()
                        .id(2)
                        .build())
                .preparedBy(Individual.builder()
                        .id(svtTeacher.getPersonalInfo().getId())
                        .build())
                .classeEntity(classe)
                .type(AssignmentType.EXAMINATION_ASSIGNMENT)
                .subject(MockUtils.SVT)
                .examName("Composition de SVT du 2e Trimestre")
                .examDate(LocalDate.of(2025, 2, 26))
                .startTime(LocalTime.of(14, 30))
                .endTime(LocalTime.of(17, 30))
                .passed(true)
                .build();

        Assignment assignmentAng = Assignment.builder()
                .semester(Planning.builder()
                        .id(12)
                        .build())
                .exam(Exam.builder()
                        .id(2)
                        .build())
                .preparedBy(Individual.builder()
                        .id(angTeacher.getPersonalInfo().getId())
                        .build())
                .classeEntity(classe)
                .type(AssignmentType.EXAMINATION_ASSIGNMENT)
                .subject(MockUtils.ANG)
                .examName("Composition d'Anglais du 2e Trimestre")
                .examDate(LocalDate.of(2025, 3, 27))
                .startTime(LocalTime.of(12, 0))
                .endTime(LocalTime.of(14, 0))
                .passed(true)
                .build();

        Assignment assignmentHg = Assignment.builder()
                .semester(Planning.builder()
                        .id(12)
                        .build())
                .exam(Exam.builder()
                        .id(2)
                        .build())
                .preparedBy(Individual.builder()
                        .id(hgTeacher.getPersonalInfo().getId())
                        .build())
                .classeEntity(classe)
                .type(AssignmentType.EXAMINATION_ASSIGNMENT)
                .subject(MockUtils.HG)
                .examName("Composition Histoire-Géo du 2e Trimestre")
                .examDate(LocalDate.of(2025, 2, 27))
                .startTime(LocalTime.of(15, 0))
                .endTime(LocalTime.of(17, 0))
                .passed(true)
                .build();

        Assignment assignmentEps = Assignment.builder()
                .semester(Planning.builder()
                        .id(12)
                        .build())
                .exam(Exam.builder()
                        .id(2)
                        .build())
                .preparedBy(Individual.builder()
                        .id(epsTeacher.getPersonalInfo().getId())
                        .build())
                .classeEntity(classe)
                .type(AssignmentType.EXAMINATION_ASSIGNMENT)
                .subject(MockUtils.EPS)
                .examName("Composition d'EPS du 2e Trimestre")
                .examDate(LocalDate.of(2025, 2, 28))
                .startTime(LocalTime.of(15, 0))
                .endTime(LocalTime.of(17, 0))
                .passed(true)
                .build();

        Assignment assignmentEsp = Assignment.builder()
                .semester(Planning.builder()
                        .id(12)
                        .build())
                .exam(Exam.builder()
                        .id(2)
                        .build())
                .preparedBy(Individual.builder()
                        .id(espTeacher.getPersonalInfo().getId())
                        .build())
                .classeEntity(classe)
                .type(AssignmentType.EXAMINATION_ASSIGNMENT)
                .subject(MockUtils.ESP)
                .examName("Composition d'Espagnol du 2e Trimestre")
                .examDate(LocalDate.of(2025, 3, 28))
                .startTime(LocalTime.of(12, 0))
                .endTime(LocalTime.of(14, 0))
                .passed(true)
                .build();*/

        repository.saveAll(List.of(
                assignmentMath//, assignmentPC, assignmentFra, assignmentSvt,
                //assignmentAng, assignmentHg, assignmentEps, assignmentEsp
        ));
    }

    private Teacher getTeacherByClasseAndCourse(ClasseEntity classe, Course course) {
        return teacherRepository.findTeacherByClasseIdAndCourseId(classe.getId(), course.getId()).orElse(null);
    }

}
