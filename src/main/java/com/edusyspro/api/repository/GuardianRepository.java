package com.edusyspro.api.repository;

import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.dto.custom.GuardianEssential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuardianRepository extends JpaRepository<GuardianEntity, UUID> {

    @Query("select new com.edusyspro.api.dto.custom.GuardianEssential(g.id, g.personalInfo, g.jobTitle, g.company, g.createdAt, g.modifyAt) " +
            "from GuardianEntity g where g.id = ?1")
    Optional<GuardianEssential> findGuardianEntityById(UUID id);

    @Query("select new com.edusyspro.api.dto.custom.GuardianEssential(g.id, g.personalInfo, g.jobTitle, g.company, g.createdAt, g.modifyAt) " +
            "from GuardianEntity g")
    List<GuardianEssential> findAllGuardians();

}
