package com.edusyspro.api.student.services;

import com.edusyspro.api.student.models.Guardian;
import com.edusyspro.api.student.models.dtos.EnrolledStudent;
import com.edusyspro.api.student.models.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {

    Enrollment enrollStudent(Enrollment enrollment);

    Page<List<EnrolledStudent>> getEnrolledStudents(UUID schoolId, boolean isArchived, Pageable pageable);

    List<Guardian> getEnrolledStudentGuardians(UUID schoolId, boolean isArchived);

}
