package com.edusyspro.api.student.services;

import com.edusyspro.api.student.models.dtos.EnrolledStudent;
import com.edusyspro.api.student.models.Enrollment;
import com.edusyspro.api.student.models.dtos.EnrolledStudentGuardian;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {

    Enrollment enrollStudent(Enrollment enrollment);

    List<EnrolledStudent> getEnrolledStudents(UUID schoolId);

    List<EnrolledStudentGuardian> getEnrolledStudentGuardians(boolean isArchived, UUID schoolId);

}
