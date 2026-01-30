package com.edusyspro.api.finance.repos;

import com.edusyspro.api.finance.entities.AccountCharts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountChartRepository extends JpaRepository<AccountCharts, Integer> {
}
