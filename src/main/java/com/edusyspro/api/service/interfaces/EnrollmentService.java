package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.EnrollmentDTO;
import com.edusyspro.api.dto.GuardianDTO;
import com.edusyspro.api.dto.custom.EnrolledStudent;
import com.edusyspro.api.dto.custom.GenderCount;
import com.edusyspro.api.model.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface EnrollmentService {

    /**
     * Enrolls a student based on the provided enrollment details.
     *
     * @param enrollmentDTO the enrollment data transfer object (DTO) containing
     *                       necessary details for student enrollment
     * @return the enrolled student's details encapsulated in an EnrollmentDTO
     */
    EnrollmentDTO enrollStudent(EnrollmentDTO enrollmentDTO);

    /**
     * Retrieves a paginated list of students enrolled in a specific school.
     *
     * @param schoolId the unique identifier of the school
     * @param pageable an object containing pagination and sorting information
     * @return a paginated collection of EnrollmentDTO objects representing the enrolled students
     */
    Page<EnrollmentDTO> getEnrolledStudents(String schoolId, Pageable pageable);

    /**
     * //TODO Change enrolledStudent to EnrollmentDTO
     * Get the searched student in a school
     * @param schoolId The school id.
     * @param lastname The last name letter of the searched student
     * @return List<EnrolledStudent>
     */
    List<EnrollmentDTO> getEnrolledStudents(String schoolId, String lastname);

    /**
     * Get the searched student of the previous academic year in a school
     * @param schoolId The school id.
     * @param lastname The last name letter of the searched student
     * @return List<EnrolledStudent>
     */
    List<EnrollmentDTO> getUnenrolledStudents(String schoolId, String lastname);

    /**
     * Get an enrolled student with its address, guardian, its health status,
     * 5 marks, 5 histories, 5 attendances and 5 classe schedules
     * @param studentId The student id
     * @return EnrollmentDTO
     */
    EnrollmentDTO getEnrolledStudent(String studentId);

    /**
     * Get pagination of the student of a single class.
     * @param classeId the classe ID
     * @param academicYear the academicYear ID
     * @param pageable the pagination
     * @return Page<EnrollmentDTO>
     */
    Page<EnrollmentDTO> getClasseEnrolledStudents(int classeId, String academicYear, Pageable pageable);

    /**
     * Get pagination of the student of a single class.
     * @param classeId the classe ID
     * @param academicYear the academicYear ID
     * @return List<EnrollmentDTO>
     */
    List<EnrollmentDTO> getClasseEnrolledStudentsSearch(int classeId, String academicYear, String searchName);

    /**
     * Get a certain number of students of a single class.
     * @param classeId the classe ID
     * @param numberOfStudents the number of student to fetch
     * @return List<EnrollmentDTO>
     */
    List<EnrollmentDTO> getClasseEnrolledStudents(int classeId, int numberOfStudents);

    /**
     * Get all students of a single class per academic year.
     * @param classeId the classe ID
     * @param academicYear the academic year
     * @return List<EnrollmentDTO>
     */
    List<EnrollmentDTO> getClasseEnrolledStudents(int classeId, String academicYear);

    /**
     * Get a student current classe classmates
     * @param schoolId The school id
     * @param studentId The student id
     * @param classeId The classe id
     * @param classmateNumber The number of classmates to retrieve
     * @return List<EnrollmentDTO>
     */
    List<EnrollmentDTO> getStudentClassmates(String schoolId, String studentId, int classeId, int classmateNumber);

    /***
     * Get a student classmates from current and previous years
     * @param studentId The student id
     * @param classeId The classe id
     * @param academicYear The academic year id
     * @param pageable The pageable for the pagination
     * @return List<EnrollmentDTO>
     */
    Page<EnrollmentDTO> getAllStudentClassmates(String studentId, int classeId, String academicYear, Pageable pageable);

    /***
     * Get the all guardians of the enrolled studentDTOS
     * @param schoolId school id
     * @param pageable The pageable for the pagination
     * @return Page<GuardianDTO>
     */
    Page<GuardianDTO> getEnrolledStudentGuardians(String schoolId, Pageable pageable);

    /**
     * Get the searched GuardianDTO in a school
     * @param schoolId The school id.
     * @param lastname The last name letter of the searched GuardianDTO
     * @return List<GuardianDTO>
     */
    List<GuardianDTO> getEnrolledStudentGuardians(String schoolId, String lastname);

    /**
     * This counts the gender of students by classe and specific academic year
     * @param classeId The classe id.
     * @param academicYear the id or the academic year
     * @return List<GenderCount>
     */
    GenderCount countClasseStudents(int classeId, String academicYear);

    /**
     * Get the number of all student in multiple classes
     * @param classeIds multiple classe ids.
     * @return List<GenderCount>
     */
    GenderCount countClasseStudents(List<Integer> classeIds, String academicYear);

    /**
     * Get the number of all student by schoolID
     * @param schoolId The school id.
     * @return @return GenderCount
     */
    GenderCount countStudents(String schoolId);

}
