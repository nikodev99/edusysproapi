package com.edusyspro.api.student.repos;

import com.edusyspro.api.student.entities.GuardianEntity;
import com.edusyspro.api.student.models.dtos.GuardianEssential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GuardianRepository extends JpaRepository<GuardianEntity, UUID> {

    @Query("select new com.edusyspro.api.student.models.dtos.GuardianEssential(g.id, g.firstName, g.lastName, g.maidenName, g.status, g.genre, g.emailId, " +
            "g.jobTitle, g.company, g.telephone, g.mobile, g.address, g.createdAt, g.modifyAt) from GuardianEntity g where g.id = ?1")
    GuardianEssential findGuardianEntityById(UUID id);

    @Query("select new com.edusyspro.api.student.models.dtos.GuardianEssential(g.id, g.firstName, g.lastName, g.maidenName, g.status, g.genre, g.emailId, " +
            "g.jobTitle, g.company, g.telephone, g.mobile, g.address, g.createdAt, g.modifyAt) from GuardianEntity g")
    List<GuardianEssential> findAllGuardians();

}
