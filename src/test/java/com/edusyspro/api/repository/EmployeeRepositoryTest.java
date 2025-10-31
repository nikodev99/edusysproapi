package com.edusyspro.api.repository;

import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.dto.EmployeeDTO;
import com.edusyspro.api.dto.custom.EmployeeIndividual;
import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.Employee;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Status;
import com.edusyspro.api.service.interfaces.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testSaveEmployee() {
        var personalInfo = Individual.builder()
                .firstName("Jane")
                .lastName("doe")
                .gender(Gender.FEMME)
                .birthDate(LocalDate.of(1990, 1, 1))
                .birthCity("Brazzaville")
                .nationality("Congo")
                .telephone("058412563")
                .reference("123456789")
                .status(Status.SINGLE)
                .address(Address.builder()
                        .number(2)
                        .street("Rue du Pool")
                        .borough("Diata")
                        .city("Brazzaville")
                        .zipCode("75008")
                        .country("Congo")
                        .build())
                .build();

        Employee employee = Employee.builder()
                .school(School.builder()
                        .id(UUID.fromString(ConstantUtils.CS2_SCHOOL))
                        .build())
                .jobTitle("Informaticienne")
                .contractType("CDI")
                .hireDate(LocalDate.of(2020, 8, 1))
                .personalInfo(personalInfo)
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        System.out.println("New Employee: " + savedEmployee);
    }

    @Test
    public void testFindAllEmployees() {
        Page<EmployeeDTO> employees = employeeService.findAllEmployees(ConstantUtils.SCHOOL_ID, PageRequest.of(0, 10));
        System.out.println("Employees: " + employees.toList());
    }

    @Test
    public void testFindingEmployeeIndividuals() {
        List<EmployeeIndividual> employeeIndividuals = employeeRepository.findEmployeeIndividuals(
                UUID.fromString("81148a1b-bdb9-4be1-9efd-fdf4106341d6")
        );

        System.out.println("Employee Individuals: " + employeeIndividuals.size());
    }

    public void testFindEmployeeById() {

    }

    public void testFindEmployeeByEmail() {}

}
