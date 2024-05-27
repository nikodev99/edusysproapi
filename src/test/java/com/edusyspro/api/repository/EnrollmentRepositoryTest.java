package com.edusyspro.api.repository;

import com.edusyspro.api.entities.Classe;
import com.edusyspro.api.entities.Enrollment;
import com.edusyspro.api.entities.School;
import com.edusyspro.api.entities.Student;
import com.edusyspro.api.service.AcademicYearService;
import com.edusyspro.api.utils.Fake;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class EnrollmentRepositoryTest {

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private AcademicYearService academicYearService;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void saveEnrolledStudents() {
        School school = getSchool();
        String year = academicYear(school);
        List<Enrollment> enrollments = Fake.studentToEnroll(10, year, school, getClasse(5));
        enrollmentRepository.saveAll(enrollments);
    }

    @Test
    public void saveEnrolledStudents2() {
        School school = getSchool();
        String year = academicYear(school);
        List<Enrollment> enrollments = Fake.studentToEnroll(10, year, school, getClasse(6));
        enrollmentRepository.saveAll(enrollments);
    }

    @Test
    public void saveEnrolledStudents3() {
        School school = getSchool();
        String year = academicYear(school);
        List<Enrollment> enrollments = Fake.studentToEnroll(10, year, school, getClasse(7));
        enrollmentRepository.saveAll(enrollments);
    }

    @Test
    public void saveEnrolledStudents4() {
        School school = getSchool();
        String year = academicYear(school);
        List<Enrollment> enrollments = Fake.studentToEnroll(10, year, school, getClasse(9));
        enrollmentRepository.saveAll(enrollments);
    }

    @Test
    public void saveEnrolledStudents5() {
        School school = getSchool();
        String year = academicYear(school);
        List<Enrollment> enrollments = new ArrayList<>(Fake.studentToEnroll(10, year, school, getClasse(10)));
        enrollments.addAll(Fake.studentToEnroll(10, year, school, getClasse(11)));
        enrollments.addAll(Fake.studentToEnroll(10, year, school, getClasse(12)));
        enrollments.addAll(Fake.studentToEnroll(10, year, school, getClasse(13)));
        enrollments.addAll(Fake.studentToEnroll(10, year, school, getClasse(14)));
        enrollments.addAll(Fake.studentToEnroll(10, year, school, getClasse(18)));
        enrollments.addAll(Fake.studentToEnroll(10, year, school, getClasse(19)));
        enrollments.addAll(Fake.studentToEnroll(10, year, school, getClasse(20)));
        enrollments.addAll(Fake.studentToEnroll(10, year, school, getClasse(21)));
        enrollments.addAll(Fake.studentToEnroll(10, year, school, getClasse(22)));
        enrollments.addAll(Fake.studentToEnroll(10, year, school, getClasse(23)));
        enrollments.addAll(Fake.studentToEnroll(10, year, school, getClasse(24)));

        enrollmentRepository.saveAll(enrollments);
    }

    @Test
    public void saveEnrollAgain() {
        School school = getSchool();
        Enrollment e = enrollmentRepository.findById(88L).orElseThrow();
        int updated = enrollmentRepository.updateEnrollmentByStudentId(true, e.getStudent().getId());
        Enrollment enrollment = Enrollment.builder()
                .academicYear(academicYear(school))
                .student(e.getStudent())
                .classe(getClasse(14))
                .enrollmentDate(LocalDateTime.now())
                .isArchived(false)
                .school(school)
                .build();
        if (updated > 0)
            enrollmentRepository.save(enrollment);
    }
    
    private Classe getClasse(int id) {
        return classRepository.getClasseById(id);
    }

    private School getSchool() {
        Optional<School> school = schoolRepository.findById(UUID.fromString("19e8cf01-5098-453b-9d65-d57cd17fc548"));
        return school.orElse(School.builder().build());
    }

    private String academicYear(School school) {
        return academicYearService.getAcademicYearForSchool(school);
    }

    private Student getStudent(String uuid) {
        return studentRepository.getStudentById(UUID.fromString(uuid));
    }


}