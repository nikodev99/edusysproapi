package com.edusyspro.api.student.services;

import com.edusyspro.api.student.models.EnrolledStudent;
import com.edusyspro.api.student.models.Enrollment;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {

    Enrollment enrollStudent(Enrollment enrollment);

    List<EnrolledStudent> getEnrolledStudents(UUID schoolId);

}
