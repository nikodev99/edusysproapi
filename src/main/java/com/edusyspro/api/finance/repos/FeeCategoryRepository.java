package com.edusyspro.api.finance.repos;

import com.edusyspro.api.finance.entities.FeeCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeCategoryRepository extends JpaRepository<FeeCategories, Long> {
}
