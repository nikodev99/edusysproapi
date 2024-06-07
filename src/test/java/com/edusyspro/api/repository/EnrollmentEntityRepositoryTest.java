package com.edusyspro.api.repository;

import com.edusyspro.api.classes.ClassRepository;
import com.edusyspro.api.classes.ClasseEntity;
import com.edusyspro.api.student.entities.EnrollmentEntity;
import com.edusyspro.api.entities.School;
import com.edusyspro.api.student.entities.StudentEntity;
import com.edusyspro.api.service.AcademicYearService;
import com.edusyspro.api.student.models.EnrolledStudent;
import com.edusyspro.api.student.repos.EnrollmentRepository;
import com.edusyspro.api.student.repos.StudentRepository;
import com.edusyspro.api.utils.Fake;
import jakarta.persistence.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class EnrollmentEntityRepositoryTest {

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
        List<EnrollmentEntity> enrollmentEntities = Fake.studentToEnroll(10, year, school, getClasse(5));
        enrollmentRepository.saveAll(enrollmentEntities);
    }

    @Test
    public void saveEnrolledStudents2() {
        School school = getSchool();
        String year = academicYear(school);
        List<EnrollmentEntity> enrollmentEntities = Fake.studentToEnroll(10, year, school, getClasse(6));
        enrollmentRepository.saveAll(enrollmentEntities);
    }

    @Test
    public void saveEnrolledStudents3() {
        School school = getSchool();
        String year = academicYear(school);
        List<EnrollmentEntity> enrollmentEntities = Fake.studentToEnroll(10, year, school, getClasse(7));
        enrollmentRepository.saveAll(enrollmentEntities);
    }

    @Test
    public void saveEnrolledStudents4() {
        School school = getSchool();
        String year = academicYear(school);
        List<EnrollmentEntity> enrollmentEntities = Fake.studentToEnroll(10, year, school, getClasse(9));
        enrollmentRepository.saveAll(enrollmentEntities);
    }

    @Test
    public void saveEnrolledStudents5() {
        School school = getSchool();
        String year = academicYear(school);
        List<EnrollmentEntity> enrollmentEntities = new ArrayList<>(Fake.studentToEnroll(10, year, school, getClasse(10)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(11)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(12)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(13)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(14)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(18)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(19)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(20)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(21)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(22)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(23)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(24)));
        System.out.println(enrollmentEntities);
        //enrollmentRepository.saveAll(enrollments);
    }

    @Test
    public void saveEnrollAgain() {
        School school = getSchool();
        EnrollmentEntity e = enrollmentRepository.findById(88L).orElseThrow();
        int updated = enrollmentRepository.updateEnrollmentByStudentId(true, e.getStudentEntity().getId());
        EnrollmentEntity enrollmentEntity = EnrollmentEntity.builder()
                .academicYear(academicYear(school))
                .studentEntity(e.getStudentEntity())
                .classeEntity(getClasse(14))
                .enrollmentDate(ZonedDateTime.now())
                .isArchived(false)
                .school(school)
                .build();
        if (updated > 0)
            enrollmentRepository.save(enrollmentEntity);
    }

    @Test
    public void getEnrolledStudents() {
        List<EnrolledStudent> enrolledStudents = enrollmentRepository.findEnrolledStudent(UUID.fromString("19e8cf01-5098-453b-9d65-d57cd17fc548"));
        for (EnrolledStudent enrolledStudent : enrolledStudents) {
            System.out.println("Enrolled Student: " + enrolledStudent);
        }
    }
    
    private ClasseEntity getClasse(int id) {
        return classRepository.getClasseById(id);
    }

    private School getSchool() {
        Optional<School> school = schoolRepository.findById(UUID.fromString("19e8cf01-5098-453b-9d65-d57cd17fc548"));
        return school.orElse(School.builder().build());
    }

    private String academicYear(School school) {
        return academicYearService.getAcademicYearForSchool(school);
    }

    private StudentEntity getStudent(String uuid) {
        return studentRepository.getStudentById(UUID.fromString(uuid));
    }


}