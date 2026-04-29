package com.edusyspro.api.repository;

import com.edusyspro.api.model.*;
import com.edusyspro.api.model.enums.ProgramStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class CourseProgramRepositoryTest {

    @Autowired
    private CourseProgramRepository courseProgramRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void saveCourseProgram() {
        Teacher teacher = Teacher.builder().id(UUID.fromString("74180dff-9f30-4872-b5c2-25acf66c3006")).build();
        Semester firstSemester = getSemester(1);
        Semester secondSemester = getSemester(2);
        Semester thirdSemester = getSemester(3);
        Course angCourse = Course.builder().id(6).build();
        ClasseEntity classe = ClasseEntity.builder().id(9).build();
        AcademicYear academicYear = AcademicYear.builder().id(UUID.fromString("4f1b86f4-8d33-4a4d-abf2-bc9178761d41")).build();

        List<CourseProgram> coursePrograms = List.of(
                CourseProgram.builder()
                        .name("Unit 1: Reading & Comprehension")
                        .purpose("Développer la capacité à lire et comprendre différents types de textes en anglais.")
                        .description("Les élèves apprennent à repérer les idées principales, les détails importants et le sens global d’un texte")
                        .semester(firstSemester)
                        .course(angCourse)
                        .classe(classe)
                        .teacher(teacher)
                        .topic(List.of(
                                CourseProgramTopic.builder()
                                        .title("Lesson 1.1: Fiction Texts")
                                        .description("Étude de textes imaginaires comme les nouvelles et extraits de romans.")
                                        .order((short) 1)
                                        .timing(CourseProgramTiming.builder()
                                                .startDate(LocalDate.of(2025, 10, 2))
                                                .endDate(LocalDate.of(2025, 10, 16))
                                                .status(ProgramStatus.PROGRAMMED)
                                                .academicYear(academicYear)
                                                .build())
                                        .build(),
                                CourseProgramTopic.builder()
                                        .title("Lesson 1.2: Non-Fiction Texts")
                                        .description("Lecture de textes réels comme les articles de presse et les biographies")
                                        .order((short) 2)
                                        .timing(CourseProgramTiming.builder()
                                                .startDate(LocalDate.of(2025, 10, 20))
                                                .endDate(LocalDate.of(2025, 11, 14))
                                                .status(ProgramStatus.PROGRAMMED)
                                                .academicYear(academicYear)
                                                .build())
                                        .build()
                        ))
                        .timing(CourseProgramTiming.builder()
                                .startDate(LocalDate.of(2025, 10, 1))
                                .endDate(LocalDate.of(2025, 11, 15))
                                .status(ProgramStatus.PROGRAMMED)
                                .academicYear(academicYear)
                                .build())
                        .build(),
                CourseProgram.builder()
                        .name("Unit 2: Writing Skills")
                        .purpose("Améliorer l’expression écrite en anglais.")
                        .description("Les élèves apprennent à organiser leurs idées et à écrire de façon claire, correcte et créative.")
                        .semester(firstSemester)
                        .course(angCourse)
                        .classe(classe)
                        .teacher(teacher)
                        .topic(List.of(
                                CourseProgramTopic.builder()
                                        .title("Lesson 2.1: Essay Writing")
                                        .description("Rédaction de textes argumentatifs et descriptifs")
                                        .order((short) 3)
                                        .timing(CourseProgramTiming.builder()
                                                .startDate(LocalDate.of(2025, 11, 17))
                                                .endDate(LocalDate.of(2025, 12, 4))
                                                .status(ProgramStatus.PROGRAMMED)
                                                .academicYear(academicYear)
                                                .build())
                                        .build(),
                                CourseProgramTopic.builder()
                                        .title("Lesson 2.2: Creative Writing")
                                        .description("Écriture imaginative à travers la poésie et les histoires courtes.")
                                        .order((short) 4)
                                        .timing(CourseProgramTiming.builder()
                                                .startDate(LocalDate.of(2025, 12, 5))
                                                .endDate(LocalDate.of(2025, 12, 12))
                                                .status(ProgramStatus.PROGRAMMED)
                                                .academicYear(academicYear)
                                                .build())
                                        .build()
                        ))
                        .timing(CourseProgramTiming.builder()
                                .startDate(LocalDate.of(2025, 11, 17))
                                .endDate(LocalDate.of(2025, 12, 12))
                                .status(ProgramStatus.PROGRAMMED)
                                .academicYear(academicYear)
                                .build())
                        .build(),
                CourseProgram.builder()
                        .name("Unit 3: Grammar & Vocabulary")
                        .purpose("Renforcer la maîtrise de la grammaire et enrichir le vocabulaire.")
                        .description("Cette unité aide les élèves à mieux construire leurs phrases et à utiliser des mots variés.")
                        .semester(secondSemester)
                        .course(angCourse)
                        .classe(classe)
                        .teacher(teacher)
                        .topic(List.of(
                                CourseProgramTopic.builder()
                                        .title("Lesson 3.1: Grammar Rules")
                                        .description("Révision des temps verbaux et de la structure des phrases.")
                                        .order((short) 5)
                                        .timing(CourseProgramTiming.builder()
                                                .startDate(LocalDate.of(2026, 1, 5))
                                                .endDate(LocalDate.of(2026, 1, 16))
                                                .status(ProgramStatus.PROGRAMMED)
                                                .academicYear(academicYear)
                                                .build())
                                        .build(),
                                CourseProgramTopic.builder()
                                        .title("Lesson 3.2: Vocabulary Building")
                                        .description("Développement du vocabulaire par les racines des mots et le contexte")
                                        .order((short) 6)
                                        .timing(CourseProgramTiming.builder()
                                                .startDate(LocalDate.of(2026, 1, 19))
                                                .endDate(LocalDate.of(2026, 2, 9))
                                                .status(ProgramStatus.PROGRAMMED)
                                                .academicYear(academicYear)
                                                .build())
                                        .build()
                        ))
                        .timing(CourseProgramTiming.builder()
                                .startDate(LocalDate.of(2026, 1, 5))
                                .endDate(LocalDate.of(2026, 2, 13))
                                .status(ProgramStatus.PROGRAMMED)
                                .academicYear(academicYear)
                                .build())
                        .build(),
                CourseProgram.builder()
                        .name("Unit 4: Speaking & Listening")
                        .purpose("Améliorer la communication orale et la compréhension à l’oral.")
                        .description("Les élèves apprennent à parler avec plus d’aisance et à écouter activement.")
                        .semester(secondSemester)
                        .course(angCourse)
                        .classe(classe)
                        .teacher(teacher)
                        .topic(List.of(
                                CourseProgramTopic.builder()
                                        .title("Lesson 4.1: Public Speaking")
                                        .description("Prise de parole devant un groupe avec confiance.")
                                        .order((short) 7)
                                        .timing(CourseProgramTiming.builder()
                                                .startDate(LocalDate.of(2026, 2, 16))
                                                .endDate(LocalDate.of(2026, 2, 27))
                                                .status(ProgramStatus.PROGRAMMED)
                                                .academicYear(academicYear)
                                                .build())
                                        .build(),
                                CourseProgramTopic.builder()
                                        .title("Lesson 4.2: Active Listening")
                                        .description("Écoute attentive et compréhension des messages oraux.")
                                        .order((short) 8)
                                        .timing(CourseProgramTiming.builder()
                                                .startDate(LocalDate.of(2026, 3, 2))
                                                .endDate(LocalDate.of(2026, 3, 13))
                                                .status(ProgramStatus.PROGRAMMED)
                                                .academicYear(academicYear)
                                                .build())
                                        .build()
                        ))
                        .timing(CourseProgramTiming.builder()
                                .startDate(LocalDate.of(2026, 2, 16))
                                .endDate(LocalDate.of(2026, 3, 13))
                                .status(ProgramStatus.PROGRAMMED)
                                .academicYear(academicYear)
                                .build())
                        .build(),
                CourseProgram.builder()
                        .name("Unit 5: Literature & Analysis")
                        .purpose("Initier les élèves à l’analyse littéraire.")
                        .description("Les élèves découvrent comment les auteurs utilisent des procédés d’écriture pour transmettre des idées et des émotions.")
                        .semester(thirdSemester)
                        .course(angCourse)
                        .classe(classe)
                        .teacher(teacher)
                        .topic(List.of(
                                CourseProgramTopic.builder()
                                        .title("Lesson 5.1: Literary Devices")
                                        .description("Étude des figures de style et techniques d’écriture.")
                                        .order((short) 9)
                                        .timing(CourseProgramTiming.builder()
                                                .startDate(LocalDate.of(2026, 4, 6))
                                                .endDate(LocalDate.of(2026, 4, 24))
                                                .status(ProgramStatus.PROGRAMMED)
                                                .academicYear(academicYear)
                                                .build())
                                        .build(),
                                CourseProgramTopic.builder()
                                        .title("Lesson 5.2: Text Analysis")
                                        .description("Analyse du sens, du style et des intentions d’un texte.")
                                        .order((short) 10)
                                        .timing(CourseProgramTiming.builder()
                                                .startDate(LocalDate.of(2026, 4, 27))
                                                .endDate(LocalDate.of(2026, 5, 15))
                                                .status(ProgramStatus.PROGRAMMED)
                                                .academicYear(academicYear)
                                                .build())
                                        .build()
                        ))
                        .timing(CourseProgramTiming.builder()
                                .startDate(LocalDate.of(2026, 4, 6))
                                .endDate(LocalDate.of(2026, 5, 15))
                                .status(ProgramStatus.PROGRAMMED)
                                .academicYear(academicYear)
                                .build())
                        .build(),
                CourseProgram.builder()
                        .name("Unit 6: Research & Media Literacy")
                        .purpose("Apprendre à rechercher l’information et à analyser les médias.")
                        .description("Les élèves développent un esprit critique face aux sources d’information et aux contenus médiatiques.")
                        .semester(thirdSemester)
                        .course(angCourse)
                        .classe(classe)
                        .teacher(teacher)
                        .topic(List.of(
                                CourseProgramTopic.builder()
                                        .title("Lesson 6.1: Research Skills")
                                        .description("Recherche d’informations fiables et organisation des données.")
                                        .order((short) 11)
                                        .timing(CourseProgramTiming.builder()
                                                .startDate(LocalDate.of(2026, 5, 18))
                                                .endDate(LocalDate.of(2026, 6, 5))
                                                .status(ProgramStatus.PROGRAMMED)
                                                .academicYear(academicYear)
                                                .build())
                                        .build(),
                                CourseProgramTopic.builder()
                                        .title("Lesson 6.2: Media Literacy")
                                        .description("Compréhension et critique des médias, messages et informations")
                                        .order((short) 12)
                                        .timing(CourseProgramTiming.builder()
                                                .startDate(LocalDate.of(2026, 6, 8))
                                                .endDate(LocalDate.of(2026, 7, 3))
                                                .status(ProgramStatus.PROGRAMMED)
                                                .academicYear(academicYear)
                                                .build())
                                        .build()
                        ))
                        .timing(CourseProgramTiming.builder()
                                .startDate(LocalDate.of(2026, 5, 18))
                                .endDate(LocalDate.of(2026, 7, 3))
                                .status(ProgramStatus.PROGRAMMED)
                                .academicYear(academicYear)
                                .build())
                        .build()
        );

        coursePrograms.forEach(cp ->
                cp.getTopic().forEach(tp -> tp.setCourseProgram(cp))
        );

        courseProgramRepository.saveAll(coursePrograms);
    }

    @Test
    public void testGettingTeachers() {
        Teacher teacher = getTeacher();
        System.out.printf("Teacher: %s%n", teacher);
    }

    private Semester getSemester(Integer semesterId) {
        return Semester.builder()
                .semesterId(semesterId)
                .build();
    }

    private Teacher getTeacher() {
        return teacherRepository.findById(UUID.fromString("4180dff-9f30-4872-b5c2-25acf66c3006"))
                .orElseThrow();
    }

}
