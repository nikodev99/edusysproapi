package com.edusyspro.api.repository;

import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.EnrollmentEntity;
import com.edusyspro.api.model.School;
import com.edusyspro.api.dto.custom.EnrolledStudent;
import com.edusyspro.api.utils.Datetime;
import com.edusyspro.api.utils.Fake;
import com.edusyspro.api.utils.MockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class EnrollmentDTOEntityRepositoryTest {

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private ClasseRepository classeRepository;

    @Test
    public void saveEnrolledStudents() {
        List<EnrollmentEntity> enrollmentEntities = Fake.studentToEnroll(
                10,
                MockUtils.ACADEMIC_YEAR_MOCK.toEntity(),
                MockUtils.SCHOOL_MOCK, getClasse(1)
        );
        enrollmentRepository.saveAll(enrollmentEntities);
    }

    @Test
    public void saveEnrolledStudents2() {
        final School SCHOOL = MockUtils.SCHOOL_MOCK;
        final AcademicYear ACADEMIC_YEAR = MockUtils.ACADEMIC_YEAR_MOCK.toEntity();
        List<EnrollmentEntity> enrollmentEntities = Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(2));
        enrollmentRepository.saveAll(enrollmentEntities);
    }

    @Test
    public void saveEnrolledStudents3() {
        final School SCHOOL = MockUtils.SCHOOL_MOCK;
        final AcademicYear ACADEMIC_YEAR = MockUtils.ACADEMIC_YEAR_MOCK.toEntity();
        List<EnrollmentEntity> enrollmentEntities = Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(3));
        enrollmentRepository.saveAll(enrollmentEntities);
    }

    @Test
    public void saveEnrolledStudents4() {
        final School SCHOOL = MockUtils.SCHOOL_MOCK;
        final AcademicYear ACADEMIC_YEAR = MockUtils.ACADEMIC_YEAR_MOCK.toEntity();
        List<EnrollmentEntity> enrollmentEntities = Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(4));
        enrollmentRepository.saveAll(enrollmentEntities);
    }

    @Test
    public void saveEnrolledStudents5() {
        final School SCHOOL = MockUtils.SCHOOL_MOCK;
        final AcademicYear ACADEMIC_YEAR = MockUtils.ACADEMIC_YEAR_MOCK.toEntity();
        List<EnrollmentEntity> enrollmentEntities = new ArrayList<>(Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(10)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(5)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(6)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(7)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(8)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(9)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(10)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(11)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(12)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(13)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(14)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(15)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(16)));
        enrollmentEntities.addAll(Fake.studentToEnroll(10, ACADEMIC_YEAR, SCHOOL, getClasse(17)));
        enrollmentRepository.saveAll(enrollmentEntities);
    }

    @Test
    public void persistStudent() {
        final School SCHOOL = MockUtils.SCHOOL_MOCK;
        EnrollmentEntity enrollmentEntity = EnrollmentEntity.builder()
                .student(Fake.getStudent(SCHOOL))
                .classe(getClasse(10))
                .enrollmentDate(Datetime.brazzavilleDatetime())
                .isArchived(false)
                .build();
        enrollmentRepository.save(enrollmentEntity);
    }

    @Test
    public void saveEnrollAgain() {
        final AcademicYear ACADEMIC_YEAR = MockUtils.ACADEMIC_YEAR_MOCK.toEntity();
        EnrollmentEntity e = enrollmentRepository.findById(105L).orElseThrow();
        int updated = enrollmentRepository.updateEnrollmentByStudentId(true, e.getStudent().getId());
        EnrollmentEntity enrollmentEntity = EnrollmentEntity.builder()
                .academicYear(ACADEMIC_YEAR)
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
        Page<EnrolledStudent> enrolledStudents = enrollmentRepository.findEnrolledStudent(UUID.fromString("e4525e5a-2c64-44c4-b40b-82aeeebef2ce"), pageable);
        System.out.println("Enrolled StudentDTO: " + enrolledStudents);
    }
    
    private ClasseEntity getClasse(int id) {
        return classeRepository.getClasseById(id);
    }

}