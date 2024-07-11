package com.edusyspro.api.student.services;

import com.edusyspro.api.entities.School;
import com.edusyspro.api.student.entities.GuardianEntity;
import com.edusyspro.api.student.models.Student;
import com.edusyspro.api.student.models.dtos.StudentEssential;
import com.edusyspro.api.student.repos.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student findStudentById(String id) {
        try {
            StudentEssential e = studentRepository.getStudentById(UUID.fromString(id));
            if (e != null) {
                return Student.builder()
                        .id(e.getId())
                        .firstName(e.getFirstName())
                        .lastName(e.getLastName())
                        .gender(e.getGender())
                        .emailId(e.getEmailId())
                        .birthDate(e.getBirthDate())
                        .birthCity(e.getBirthCity())
                        .nationality(e.getNationality())
                        .dadName(e.getDadName())
                        .momName(e.getMomName())
                        .reference(e.getReference())
                        .telephone(e.getTelephone())
                        .guardian(
                                GuardianEntity.builder()
                                        .firstName(e.getGuardianFirstName())
                                        .lastName(e.getGuardianLastName())
                                        .maidenName(e.getGuardianMaidenName())
                                        .status(e.getGuardianStatus())
                                        .genre(e.getGuardianGenre())
                                        .emailId(e.getGuardianEmailId())
                                        .jobTitle(e.getGuardianJobTitle())
                                        .company(e.getGuardianCompany())
                                        .telephone(e.getGuardianTelephone())
                                        .mobile(e.getGuardianMobile())
                                        .address(e.getGuardianAddress())
                                        .build()
                        )
                        .healthCondition(e.getHealthCondition())
                        .image(e.getImage())
                        .school(
                                School.builder()
                                        .name(e.getCurrentSchoolName())
                                        .abbr(e.getCurrentSchoolAbbr())
                                        .build()
                        )
                        .createdAt(e.getCreatedAt())
                        .modifyAt(e.getModifyAt())
                        .build();
            }
        }catch (Exception e) {
            System.out.println("Error Caught: " + e.getMessage());
        }
        return null;
    }
}
