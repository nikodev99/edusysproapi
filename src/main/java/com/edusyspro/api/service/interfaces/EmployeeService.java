package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.EmployeeDTO;
import com.edusyspro.api.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    Page<EmployeeDTO> findAllEmployees(String schoolId, Pageable pageable);
    List<EmployeeDTO> findAllSearchedEmployees(String schoolId, String searchInput);
    EmployeeDTO findEmployee(String employeeId);
}
