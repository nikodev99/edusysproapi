package com.edusyspro.api.student.services;

import com.edusyspro.api.student.entities.EnrollmentEntity;
import com.edusyspro.api.student.models.dtos.EnrolledStudent;
import com.edusyspro.api.student.models.Enrollment;
import com.edusyspro.api.student.repos.EnrollmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EnrollmentServiceImp implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentServiceImp(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Enrollment enrollStudent(Enrollment enrollment) {
        EnrollmentEntity enrollmentEntity = EnrollmentEntity.builder().build();
        BeanUtils.copyProperties(enrollment, enrollmentEntity);
        enrollmentRepository.save(enrollmentEntity);
        return enrollment;
    }

    @Override
    public List<EnrolledStudent> getEnrolledStudents(UUID schoolId) {
        return  enrollmentRepository.findEnrolledStudent(schoolId);
    }
}
