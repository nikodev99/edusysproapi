package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.Guardian;
import com.edusyspro.api.dto.EnrolledStudent;
import com.edusyspro.api.dto.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {

    /**
     * Adding a new student
     * @param enrollment data to add
     * @return Enrollment
     */
    Enrollment enrollStudent(Enrollment enrollment);

    /**
     * TODO Change enrolledStudent to Enrollment
     * Get all the student enrolled in a school
     * @param schoolId The school id.
     * @param pageable The pageable for the pagination
     * @return Page<EnrolledStudent>
     */
    Page<EnrolledStudent> getEnrolledStudents(String schoolId, Pageable pageable);

    /**
     * //TODO Change enrolledStudent to Enrollment
     * Get the searched student in a school
     * @param schoolId The school id.
     * @param lastname The last name letter of the searched student
     * @return List<EnrolledStudent>
     */
    List<EnrolledStudent> getEnrolledStudents(String schoolId, String lastname);

    /**
     * Get an enrolled student with its address, guardian, its health status,
     * 5 marks, 5 history, 5 attendances and 5 classe schedule
     * @param schoolId The school id.
     * @param studentId The student id
     * @return Enrollment
     */
    Enrollment getEnrolledStudent(String schoolId, String studentId);

    /**
     * Get a student current classe classmates
     * @param schoolId The school id
     * @param studentId The student id
     * @param classeId The classe id
     * @param classmateNumber The number of classmates to retrieve
     * @return List<Enrollment>
     */
    List<Enrollment> getStudentClassmates(String schoolId, String studentId, int classeId, int classmateNumber);

    /***
     * Get a student classmates from current and previous years
     * @param schoolId The school id
     * @param studentId The student id
     * @param classeId The classe id
     * @param academicYear The academic year id
     * @param pageable The pageable for the pagination
     * @return List<Enrollment>
     */
    Page<Enrollment> getAllStudentClassmates(String schoolId, String studentId, int classeId, String academicYear, Pageable pageable);

    /***
     * Get the all guardians of the enrolled students
     * @param schoolId school id
     * @param pageable The pageable for the pagination
     * @return List<Guardian>
     */
    Page<Guardian> getEnrolledStudentGuardians(String schoolId, Pageable pageable);

    /**
     * Get the searched Guardian in a school
     * @param schoolId The school id.
     * @param lastname The last name letter of the searched Guardian
     * @return List<Guardian>
     */
    List<Guardian> getEnrolledStudentGuardians(String schoolId, String lastname);

}
