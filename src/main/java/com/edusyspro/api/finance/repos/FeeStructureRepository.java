package com.edusyspro.api.finance.repos;

import com.edusyspro.api.finance.entities.FeeStructures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeStructureRepository extends JpaRepository<FeeStructures, Long> {
}
