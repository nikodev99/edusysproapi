package com.edusyspro.api.repository;

import com.edusyspro.api.school.entities.Department;
import com.edusyspro.api.school.entities.School;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Test
    public void saveDepartment() {
        School school = getSchool();
        Department department = Department.builder()
                .code("DDL")
                .name("Département de Lettre")
                .school(school)
                .build();

        Department department2 = Department.builder()
                .code("DDS")
                .name("Département de Science")
                .school(school)
                .build();

        Department department3 = Department.builder()
                .code("DAC")
                .name("Département Art et Culture")
                .school(school)
                .build();

        Department department4 = Department.builder()
                .code("DEP")
                .name("Département Education Physique")
                .boss(null)
                .school(school)
                .build();

        List<Department> departments = List.of(department, department2, department3, department4);
        departmentRepository.saveAll(departments);
    }

    @Test
    public void printDepartments() {
        List<Department> departments = departmentRepository.findAll();
        departments.forEach(d -> System.out.println("Department=" + d));
    }

    @Test
    public void printDepartmentByCode() {
        Department department = departmentRepository.getDepartmentByCode("DDL");
        System.out.println(department);
    }

    private School getSchool() {
        Optional<School> school = schoolRepository.findById(UUID.fromString("e4525e5a-2c64-44c4-b40b-82aeeebef2ce"));
        return school.orElse(School.builder().build());
    }

}