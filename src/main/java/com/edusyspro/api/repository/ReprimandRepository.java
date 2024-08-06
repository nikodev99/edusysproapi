package com.edusyspro.api.repository;

import com.edusyspro.api.entities.Reprimand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReprimandRepository extends JpaRepository<Reprimand, Long> {

    Optional<List<Reprimand>> findReprimandsByStudentId(UUID studentId);

}
