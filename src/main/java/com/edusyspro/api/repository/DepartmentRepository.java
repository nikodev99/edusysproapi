package com.edusyspro.api.repository;

import com.edusyspro.api.school.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Department getDepartmentByCode(String code);

}
