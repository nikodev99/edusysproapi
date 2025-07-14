package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.EmployeeEssential;
import com.edusyspro.api.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("""
        SELECT new com.edusyspro.api.dto.custom.EmployeeEssential(e.id, e.personalInfo, e.jobTitle, e.salary, e.contractType,
        e.active, e.hireDate, e.school.id, e.school.name, e.school.abbr, e.createdAt, e.modifyAt)
        FROM Employee e WHERE e.school.id = :schoolId AND e.active = true
    """)
    Page<EmployeeEssential> findAllEmployees(@Param("schoolId") UUID schoolId, Pageable pageable);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.EmployeeEssential(e.id, e.personalInfo, e.jobTitle, e.salary, e.contractType,
        e.active, e.hireDate, e.school.id, e.school.name, e.school.abbr, e.createdAt, e.modifyAt) FROM Employee e WHERE e.school.id = :schoolId
        AND ((LOWER(e.personalInfo.lastName) = LOWER(:searchInput)) OR (LOWER(e.personalInfo.firstName) = LOWER(:searchInput))) AND e.active = true
    """)
    List<EmployeeEssential> findSearchedEmployees(@Param("schoolId") UUID schoolId, String searchInput);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.EmployeeEssential(e.id, e.personalInfo, e.jobTitle, e.salary, e.contractType,
        e.active, e.hireDate, e.school.id, e.school.name, e.school.abbr, e.createdAt, e.modifyAt)
        FROM Employee e WHERE e.id = :employeeId
    """)
    Optional<EmployeeEssential> findOneEmployee(@Param("employeeId") UUID employeeId);

    Boolean existsEmployeeByPersonalInfoEmailIdAndSchoolId(String email, UUID schoolId);
}
