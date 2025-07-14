package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.EmployeeDTO;
import com.edusyspro.api.dto.custom.EmployeeEssential;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.model.Employee;
import com.edusyspro.api.repository.EmployeeRepository;
import com.edusyspro.api.service.interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        if (!employeeExists(
                employee.getPersonalInfo().getEmailId(),
                employee.getSchool().getId()
        )) {
            return employeeRepository.save(employee);
        }
        return null;
    }

    @Override
    public Page<EmployeeDTO> findAllEmployees(String schoolId, Pageable pageable) {
        return employeeRepository.findAllEmployees(UUID.fromString(schoolId), pageable)
                .map(EmployeeEssential::toDto);
    }

    @Override
    public List<EmployeeDTO> findAllSearchedEmployees(String schoolId, String searchInput) {
        return employeeRepository.findSearchedEmployees(UUID.fromString(schoolId), "%" + searchInput + "%").stream()
                .map(EmployeeEssential::toDto)
                .toList();
    }

    @Override
    public EmployeeDTO findEmployee(String employeeId) {
        return employeeRepository.findOneEmployee(UUID.fromString(employeeId))
                .map(EmployeeEssential::toDto)
                .orElseThrow(() -> new NotFountException("Employee not found for ID: " + employeeId));
    }

    private boolean employeeExists(String email, UUID schoolId) {
        return employeeRepository.existsEmployeeByPersonalInfoEmailIdAndSchoolId(email, schoolId);
    }
}
