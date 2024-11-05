package com.edusyspro.api.repository;

import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.dto.GuardianEssential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GuardianRepository extends JpaRepository<GuardianEntity, UUID> {

    @Query("select new com.edusyspro.api.dto.GuardianEssential(g.id, g.personalInfo.firstName, g.personalInfo.lastName, " +
            "g.personalInfo.maidenName, g.personalInfo.status, g.personalInfo.gender, g.personalInfo.emailId, " +
            "g.jobTitle, g.company, g.personalInfo.telephone, g.personalInfo.mobile, g.personalInfo.address, g.createdAt, g.modifyAt) " +
            "from GuardianEntity g where g.id = ?1")
    GuardianEssential findGuardianEntityById(UUID id);

    @Query("select new com.edusyspro.api.dto.GuardianEssential(g.id, g.personalInfo.firstName, g.personalInfo.lastName, " +
            "g.personalInfo.maidenName, g.personalInfo.status, g.personalInfo.gender, g.personalInfo.emailId, " +
            "g.jobTitle, g.company, g.personalInfo.telephone, g.personalInfo.mobile, g.personalInfo.address, g.createdAt, g.modifyAt) " +
            "from GuardianEntity g")
    List<GuardianEssential> findAllGuardians();

}
