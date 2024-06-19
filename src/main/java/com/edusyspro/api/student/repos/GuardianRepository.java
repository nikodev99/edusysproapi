package com.edusyspro.api.student.repos;

import com.edusyspro.api.student.entities.GuardianEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GuardianRepository extends JpaRepository<GuardianEntity, UUID> {
}
