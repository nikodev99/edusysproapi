package com.edusyspro.api.student.services;

import com.edusyspro.api.student.models.Guardian;
import com.edusyspro.api.student.models.dtos.EnrolledStudent;
import com.edusyspro.api.student.models.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EnrollmentService {

    Enrollment enrollStudent(Enrollment enrollment);

    Page<List<EnrolledStudent>> getEnrolledStudents(String schoolId, Pageable pageable);

    List<EnrolledStudent> getEnrolledStudents(String schoolId, String lastname);

    Enrollment getEnrolledStudent(String schoolId, String studentId);

    List<EnrolledStudent> getStudentClassmates(String schoolId, String studentId, int classmateNumber);

    List<Guardian> getEnrolledStudentGuardians(String schoolId, boolean isArchived);

}
