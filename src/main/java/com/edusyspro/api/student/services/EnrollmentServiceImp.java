package com.edusyspro.api.student.services;

import com.edusyspro.api.student.entities.EnrollmentEntity;
import com.edusyspro.api.student.entities.GuardianEntity;
import com.edusyspro.api.student.models.Guardian;
import com.edusyspro.api.student.models.dtos.EnrolledStudent;
import com.edusyspro.api.student.models.Enrollment;
import com.edusyspro.api.student.models.dtos.EnrolledStudentGuardian;
import com.edusyspro.api.student.repos.EnrollmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<EnrolledStudent> getEnrolledStudents(UUID schoolId) {
        return  enrollmentRepository.findEnrolledStudent(schoolId);
    }

    @Override
    public List<Guardian> getEnrolledStudentGuardians(boolean isArchived, UUID schoolId) {
        List<EnrolledStudentGuardian> enrolledStudentGuardian = enrollmentRepository.findEnrolledStudentGuardian(isArchived, schoolId);
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
