package com.edusyspro.api.finance.repos;

import com.edusyspro.api.finance.entities.FeeAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeAssessmentRepository extends JpaRepository<FeeAssessment, Long> {
}
