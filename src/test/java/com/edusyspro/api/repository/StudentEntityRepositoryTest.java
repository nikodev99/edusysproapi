package com.edusyspro.api.repository;

import com.edusyspro.api.classes.ClasseRepository;
import com.edusyspro.api.classes.ClasseEntity;
import com.edusyspro.api.entities.School;
import com.edusyspro.api.student.entities.StudentEntity;
import com.edusyspro.api.student.models.dtos.StudentEssential;
import com.edusyspro.api.student.repos.StudentRepository;
import com.edusyspro.api.utils.Fake;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class StudentEntityRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Test
    public void saveStudent() {
        School school = getSchool();
        StudentEntity studentEntity = Fake.getStudent(school);
        studentRepository.save(studentEntity);
    }

    private School getSchool() {
        return schoolRepository.getSchoolById(UUID.fromString("19e8cf01-5098-453b-9d65-d57cd17fc548"));
    }

    private ClasseEntity getClasse(int id) {
        return classeRepository.getClasseById(id);
    }

}