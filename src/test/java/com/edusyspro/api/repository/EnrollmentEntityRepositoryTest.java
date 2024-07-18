package com.edusyspro.api.repository;

import com.edusyspro.api.classes.ClasseRepository;
import com.edusyspro.api.classes.ClasseEntity;
import com.edusyspro.api.entities.AcademicYear;
import com.edusyspro.api.student.entities.EnrollmentEntity;
import com.edusyspro.api.entities.School;
import com.edusyspro.api.service.AcademicYearService;
import com.edusyspro.api.student.models.dtos.EnrolledStudent;
import com.edusyspro.api.student.models.dtos.StudentEssential;
import com.edusyspro.api.student.repos.EnrollmentRepository;
import com.edusyspro.api.student.repos.StudentRepository;
import com.edusyspro.api.utils.Datetime;
import com.edusyspro.api.utils.Fake;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    private ClasseRepository classeRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private AcademicYearService academicYearService;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AcademicYearRepository academicYearRepository;

    @Test
    public void saveEnrolledStudents() {
        School school = getSchool();
        AcademicYear year = academicYear(school);
        List<EnrollmentEntity> enrollmentEntities = Fake.studentToEnroll(10, year, school, getClasse(1));
        enrollmentRepository.saveAll(enrollmentEntities);
    }

    @Test
    public void saveEnrolledStudents2() {
        School school = getSchool();
        AcademicYear year = academicYear(school);
        List<EnrollmentEntity> enrollmentEntities = Fake.studentToEnroll(10, year, school, getClasse(2));
        enrollmentRepository.saveAll(enrollmentEntities);
    }

    @Test
    public void saveEnrolledStudents3() {
        School school = getSchool();
        AcademicYear year = academicYear(school);
        List<EnrollmentEntity> enrollmentEntities = Fake.studentToEnroll(10, year, school, getClasse(3));
        enrollmentRepository.saveAll(enrollmentEntities);
    }

    @Test
    public void saveEnrolledStudents4() {
        School school = getSchool();
        AcademicYear year = academicYear(school);
        List<EnrollmentEntity> enrollmentEntities = Fake.studentToEnroll(10, year, school, getClasse(4));
        enrollmentRepository.saveAll(enrollmentEntities);
    }

    @Test
    public void saveEnrolledStudents5() {
        School school = getSchool();
        AcademicYear year = academicYear(school);
        List<EnrollmentEntity> enrollmentEntities = new ArrayList<>(Fake.studentToEnroll(10, year, school, getClasse(10)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(5)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(6)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(7)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(8)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(9)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(10)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(11)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(12)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(13)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(14)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(15)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(16)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, year, school, getClasse(17)));
        enrollmentRepository.saveAll(enrollmentEntities);
    }

    @Test
    public void persistStudent() {
        EnrollmentEntity enrollmentEntity = EnrollmentEntity.builder()
                .student(Fake.getStudent(getSchool()))
                .classe(getClasse(10))
                .enrollmentDate(Datetime.brazzavilleDatetime())
                .isArchived(false)
                .build();
        enrollmentRepository.save(enrollmentEntity);
    }

    @Test
    public void saveEnrollAgain() {
        School school = getSchool();
        EnrollmentEntity e = enrollmentRepository.findById(105L).orElseThrow();
        int updated = enrollmentRepository.updateEnrollmentByStudentId(true, e.getStudent().getId());
        EnrollmentEntity enrollmentEntity = EnrollmentEntity.builder()
                .academicYear(academicYear(school))
                .student(e.getStudent())
                .classe(getClasse(9))
                .enrollmentDate(ZonedDateTime.now())
                .isArchived(false)
                .build();
        if (updated > 0)
            enrollmentRepository.save(enrollmentEntity);
    }

    @Test
    public void getEnrolledStudents() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<List<EnrolledStudent>> enrolledStudents = enrollmentRepository.findEnrolledStudent(UUID.fromString("e4525e5a-2c64-44c4-b40b-82aeeebef2ce"), pageable);
        System.out.println("Enrolled Student: " + enrolledStudents);
    }
    
    private ClasseEntity getClasse(int id) {
        return classeRepository.getClasseById(id);
    }

    private School getSchool() {
        Optional<School> school = schoolRepository.findById(UUID.fromString("e4525e5a-2c64-44c4-b40b-82aeeebef2ce"));
        return school.orElse(School.builder().build());
    }

    private AcademicYear academicYear(School school) {
        return academicYearRepository.findAcademicYearBySchoolId(school.getId());
    }


}