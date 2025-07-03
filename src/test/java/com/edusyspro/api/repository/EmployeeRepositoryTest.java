package com.edusyspro.api.repository;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.Employee;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testSaveEmployee() {
        var personalInfo = Individual.builder()
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.HOMME)
                .birthDate(LocalDate.of(1990, 1, 1))
                .birthCity("Paris")
                .nationality("France")
                .telephone("058412563")
                .address(Address.builder()
                        .number(2)
                        .street("Rue du Rivolie")
                        .neighborhood("Paris")
                        .city("Paris")
                        .zipCode("75008")
                        .country("France")
                        .build())
                .build();

        Employee employee = Employee.builder()
                .personalInfo(personalInfo)
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        System.out.println("New Employee: " + savedEmployee);
    }

    public void testFindEmployeeById() {

    }

    public void testFindEmployeeByEmail() {}

}
