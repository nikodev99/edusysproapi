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
     * Adding a new student
     * @param enrollmentDTO data to add
     * @return EnrollmentDTO
     */
    EnrollmentDTO enrollStudent(EnrollmentDTO enrollmentDTO);

    /**
     * TODO Change enrolledStudent to EnrollmentDTO
     * Get all the student enrolled in a school
     * @param schoolId The school id.
     * @param pageable The pageable for the pagination
     * @return Page<EnrolledStudent>
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
     * Get an enrolled student with its address, guardian, its health status,
     * 5 marks, 5 history, 5 attendances and 5 classe schedule
     * @param schoolId The school id.
     * @param studentId The student id
     * @return EnrollmentDTO
     */
    EnrollmentDTO getEnrolledStudent(String schoolId, String studentId);

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
     * Get pagination of the student of a single class.
     * @param classeId the classe ID
     * @param numberOfStudents the number of student to fetch
     * @return List<EnrollmentDTO>
     */
    List<EnrollmentDTO> getClasseEnrolledStudents(int classeId, int numberOfStudents);

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
     * @param schoolId The school id
     * @param studentId The student id
     * @param classeId The classe id
     * @param academicYear The academic year id
     * @param pageable The pageable for the pagination
     * @return List<EnrollmentDTO>
     */
    Page<EnrollmentDTO> getAllStudentClassmates(String schoolId, String studentId, int classeId, String academicYear, Pageable pageable);

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
    List<GenderCount> countClasseStudents(int classeId, String academicYear);

    /**
     * Get the number of all student by schoolID
     * @param schoolId The school id.
     * @return Map<GuardianDTO>
     */
    Map<String, Long> countStudents(String schoolId);

}
