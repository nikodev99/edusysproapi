package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.EmployeeIndividual;
import com.edusyspro.api.dto.custom.IndividualBasic;
import com.edusyspro.api.model.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IndividualRepository extends JpaRepository<Individual, Long> {

    /**
     * Retrieves the employee ID associated with the specified personal information ID.
     *
     * @param personalInfoId the ID of the personal information associated with the employee
     * @return an {@code Optional} containing the employee ID if found, otherwise an empty {@code Optional}
     */
    @Query("""
        SELECT e.id, e.personalInfo.firstName, e.personalInfo.lastName, s.id, s.name, s.abbr, s.websiteURL
        FROM Employee e LEFT JOIN e.school s WHERE e.personalInfo.id = ?1
    """)
    List<Object[]> findEmployeeIdByPersonalInfoId(Long personalInfoId);

    /**
     * Retrieves information related to a guardian and their associated school based on the personal information ID.
     *
     * @param personalInfoId the ID of the personal information associated with the guardian
     * @return an {@code Optional} containing an {@code Object[]} with the following elements:
     *         [guardian ID, guardian firstname, guardian lastname,
     *         school ID, school name, school abbreviation, school website URL],
     *         if a guardian and their school are found; otherwise, an empty {@code Optional}
     */
    @Query("""
        SELECT g.id, g.personalInfo.firstName, g.personalInfo.lastName, s.id, s.name, s.abbr, s.websiteURL
        FROM EnrollmentEntity e JOIN e.academicYear a JOIN e.student st JOIN a.school s JOIN st.guardian g
        WHERE g.personalInfo.id = ?1 AND a.current = true
    """)
    List<Object[]> findGuardianIdPersonalInfoId(Long personalInfoId);

    /**
     * Retrieves information related to a teacher and their associated school based on the personal information ID.
     *
     * @param personalInfo the ID of the personal information associated with the teacher
     * @return an {@code Optional} containing an {@code Object[]} with the following elements:
     *         [teacher ID, teacher firstname, teacher lastname, school ID, school name, school abbreviation, school website URL],
     *         if a teacher and their school are found; otherwise, an empty {@code Optional}
     */
    @Query("""
        SELECT t.id, t.personalInfo.firstName, t.personalInfo.lastName, s.id, s.name, s.abbr, s.websiteURL
        FROM Teacher t LEFT JOIN t.school s WHERE t.personalInfo.id = ?1
    """)
    List<Object[]> findTeacherIdByPersonalInfoId(long personalInfo);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.IndividualBasic(i.id, i.firstName, i.lastName, CONCAT("[", i.emailId, ",", i.telephone, ",", i.individualType, "]"), i.reference)
        FROM Individual i WHERE lower(i.firstName) LIKE lower(?1) OR lower(i.lastName) LIKE lower(?1)
    """)
    List<IndividualBasic> findPotentialUserPersonalInfo(String name);
}
