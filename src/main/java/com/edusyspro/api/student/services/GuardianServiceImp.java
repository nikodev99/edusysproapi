package com.edusyspro.api.student.services;

import com.edusyspro.api.student.models.Guardian;
import com.edusyspro.api.student.models.dtos.GuardianEssential;
import com.edusyspro.api.student.repos.GuardianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GuardianServiceImp implements GuardianService {

    private final GuardianRepository guardianRepository;

    @Autowired
    public GuardianServiceImp(GuardianRepository guardianRepository) {
        this.guardianRepository = guardianRepository;
    }

    @Override
    public Guardian findGuardianById(UUID id) {
        GuardianEssential essential = guardianRepository.findGuardianEntityById(id);
        Guardian guardian = new Guardian();
        if (essential != null)
            guardian = populateGuardian(essential);

        return guardian;
    }

    @Override
    public List<Guardian> findAll() {
        List<GuardianEssential> allGuardians = guardianRepository.findAllGuardians();
        List<Guardian> guardians = new ArrayList<>();
        if (!allGuardians.isEmpty())
            guardians = allGuardians.stream()
                    .map(this::populateGuardian)
                    .toList();

        return guardians;
    }

    private Guardian populateGuardian(GuardianEssential essential) {
        return Guardian.builder()
                .id(essential.getId())
                .firstName(essential.getFirstName())
                .lastName(essential.getLastName())
                .maidenName(essential.getMaidenName())
                .status(essential.getStatus())
                .genre(essential.getGenre())
                .emailId(essential.getEmailId())
                .jobTitle(essential.getJobTitle())
                .company(essential.getCompany())
                .telephone(essential.getTelephone())
                .mobile(essential.getMobile())
                .address(essential.getAddress())
                .createdAt(essential.getCreatedAt())
                .modifyAt(essential.getModifyAt())
                .build();
    }
}
