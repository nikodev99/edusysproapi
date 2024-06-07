package com.edusyspro.api.repository;

import com.edusyspro.api.entities.Address;
import com.edusyspro.api.entities.School;
import com.edusyspro.api.utils.Generator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class SchoolRepositoryTest {

    @Autowired
    private SchoolRepository schoolRepository;

    @Test
    public void saveSchool() {
        Address address = Address.builder()
                .borough("Madibou")
                .city("Brazzaville")
                .country("Congo")
                .neighborhood("Songolo")
                .secondStreet(null)
                .street("Rue Madia")
                .number(12)
                .zipCode(null)
                .build();

        School school = School.builder()
                .name("Complexe Scolaire Les Bambins")
                .accreditationNumber(Generator.generateDigitsAndLetters())
                .abbr("CSB")
                .accreditationCode("AMD")
                .contactEmail("contact@bambins.cg")
                .foundedDate(LocalDate.of(1997, 3, 15))
                .phoneNumber("+242063216598")
                .address(address)
                .websiteURL(null)
                .build();

        schoolRepository.save(school);
    }

    @Test
    public void saveSchool2() {
        Address address = Address.builder()
                .borough("M'Filou")
                .city("Brazzaville")
                .country("Congo")
                .neighborhood("Makazou")
                .secondStreet("Avenue de la paroisse")
                .street("Rue Bingola")
                .number(5)
                .zipCode(null)
                .build();

        School school = School.builder()
                .name("Complexe Scolaire Les Deux Etrangers")
                .accreditationNumber(Generator.generateDigitsAndLetters())
                .abbr("CSDE")
                .accreditationCode("KLM")
                .contactEmail("contact@csde.cg")
                .foundedDate(LocalDate.of(2007, 9, 28))
                .phoneNumber("+242065442559")
                .address(address)
                .websiteURL(null)
                .build();

        schoolRepository.save(school);
    }

    @Test
    public void saveSchool3() {
        Address address = Address.builder()
                .borough("Moungali")
                .city("Brazzaville")
                .country("Congo")
                .neighborhood("La Poudrière")
                .secondStreet("Avenue des 3 Martyrs")
                .street("Rue du pool")
                .number(1)
                .zipCode(null)
                .build();

        School school = School.builder()
                .name("Complexe Scolaire La Poudrière")
                .accreditationNumber(Generator.generateDigitsAndLetters())
                .abbr("CSP")
                .accreditationCode("ABM")
                .contactEmail("contact@csp.cg")
                .foundedDate(LocalDate.of(1991, 1, 25))
                .phoneNumber("+242065876959")
                .address(address)
                .websiteURL(null)
                .build();

        schoolRepository.save(school);
    }

    @Test
    public void printASchool() {
        Optional<School> school = schoolRepository.findById(UUID.fromString("27a58e8a-a588-45dd-917e-6b690acd4b22"));
        System.out.println("School retrieved=" + school.orElseThrow());
    }

    @Test
    public void updateASchool() {
        int updatedRow = schoolRepository.updateSchoolNameById("Complexe Scolaire les Doux Echos", UUID.fromString("27a58e8a-a588-45dd-917e-6b690acd4b22"));
        System.out.println("Row updated=" + updatedRow);
    }

    @Test
    public void printSchools() {
        List<School> schools = schoolRepository.findAll();
        for(School school: schools)
        {
            System.out.println("School info=" + school.getId());
        }
    }

}