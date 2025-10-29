package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.EmployeeDTO;
import com.edusyspro.api.dto.custom.EmployeeEssential;
import com.edusyspro.api.dto.custom.EmployeeIndividual;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.model.Employee;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.IndividualType;
import com.edusyspro.api.repository.EmployeeRepository;
import com.edusyspro.api.repository.IndividualRepository;
import com.edusyspro.api.repository.context.UpdateContext;
import com.edusyspro.api.service.interfaces.EmployeeService;
import com.edusyspro.api.service.interfaces.IndividualReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UpdateContext updateContext;

    private final IndividualReferenceService individualReferenceService;

    @Autowired
    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            UpdateContext updateContext,
            IndividualReferenceService individualReferenceService
    ) {
        this.employeeRepository = employeeRepository;
        this.updateContext = updateContext;
        this.individualReferenceService = individualReferenceService;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        if (!employeeExists(
                employee.getPersonalInfo().getEmailId(),
                employee.getSchool().getId()
        )) {
            Individual employeePersonalInfo = employee.getPersonalInfo();
            if (employeePersonalInfo != null) {
                String reference = individualReferenceService.generateReference(
                        IndividualType.EMPLOYEE,
                        employee.getSchool().getId()
                );
                employeePersonalInfo.setReference(reference);
            }
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

    @Override
    public Integer updateEmployeeField(String employeeId, UpdateField fields) {
        return updateContext.updateEmployeeFields(fields.field(), fields.value(), UUID.fromString(employeeId));
    }

    @Override
    public List<EmployeeDTO> getEmployeeIndividuals(String schoolId, String searchInput) {
        return searchInput != null && !searchInput.isEmpty()
            ? employeeRepository.findEmployeeIndividuals(UUID.fromString(schoolId), "%" + searchInput + "%").stream()
                .map(EmployeeIndividual::toEmployee)
                .toList()

            : employeeRepository.findEmployeeIndividuals(UUID.fromString(schoolId)).stream()
                .map(EmployeeIndividual::toEmployee)
                .toList();
    }

    private boolean employeeExists(String email, UUID schoolId) {
        return employeeRepository.existsEmployeeByPersonalInfoEmailIdAndSchoolId(email, schoolId);
    }
}
