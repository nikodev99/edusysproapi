package com.edusyspro.api.student.services;

import com.edusyspro.api.student.entities.EnrollmentEntity;
import com.edusyspro.api.student.entities.GuardianEntity;
import com.edusyspro.api.student.models.Guardian;
import com.edusyspro.api.student.models.Student;
import com.edusyspro.api.student.models.dtos.EnrolledStudent;
import com.edusyspro.api.student.models.Enrollment;
import com.edusyspro.api.student.models.dtos.EnrolledStudentGuardian;
import com.edusyspro.api.student.repos.EnrollmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EnrollmentServiceImp implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    private final GuardianService guardianService;

    @Autowired
    public EnrollmentServiceImp(EnrollmentRepository enrollmentRepository, GuardianService guardianService) {
        this.enrollmentRepository = enrollmentRepository;
        this.guardianService = guardianService;
    }

    @Override
    @Transactional
    public Enrollment enrollStudent(Enrollment enrollment) {
        EnrollmentEntity enrollmentEntity = EnrollmentEntity.builder().build();
        BeanUtils.copyProperties(enrollment, enrollmentEntity);

        GuardianEntity guardianEntity = enrollmentEntity.getStudent().getGuardian();
        if (guardianEntity != null) {
            GuardianEntity guardian = guardianService.saveOrUpdateGuardian(guardianEntity);
            enrollmentEntity.getStudent().setGuardian(guardian);
        }

        enrollmentRepository.save(enrollmentEntity);
        return enrollment;
    }

    @Override
    public Page<List<EnrolledStudent>> getEnrolledStudents(UUID schoolId, Pageable pageable) {
        return  enrollmentRepository.findEnrolledStudent(schoolId, pageable);
    }

    @Override
    public List<EnrolledStudent> getEnrolledStudents(UUID schoolId, String lastname) {
        return enrollmentRepository.findEnrolledStudent(schoolId, "%" + lastname + "%");
    }

    @Override
    public Enrollment getEnrolledStudent(UUID schoolId, UUID studentId) {
        EnrolledStudent enrolledStudent = enrollmentRepository.findEnrollmentById(schoolId, studentId);
        EnrolledStudent student = EnrolledStudent.builder().build();
        return student.populateStudent(enrolledStudent);
    }

    @Override
    public List<Guardian> getEnrolledStudentGuardians(UUID schoolId, boolean isArchived) {
        List<EnrolledStudentGuardian> enrolledStudentGuardian = enrollmentRepository.findEnrolledStudentGuardian(schoolId, isArchived);
        List<Guardian> guardians = new ArrayList<>();
        if (!enrolledStudentGuardian.isEmpty()) {
            guardians = enrolledStudentGuardian.stream()
                    .map(e -> Guardian.builder()
                            .id(e.getId())
                            .firstName(e.getFirstName())
                            .lastName(e.getLastName())
                            .maidenName(e.getMaidenName())
                            .genre(e.getGenre())
                            .emailId(e.getEmailId())
                            .jobTitle(e.getJobTitle())
                            .company(e.getCompany())
                            .telephone(e.getTelephone())
                            .mobile(e.getMobile())
                            .build())
                    .toList();
        }
        return guardians;
    }
}
