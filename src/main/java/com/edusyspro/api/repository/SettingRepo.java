package com.edusyspro.api.repository;

import com.edusyspro.api.model.Settings;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface SettingRepo extends JpaRepository<Settings, Long> {
    List<Settings> findAllByTenantId(UUID tenantId);

    Settings findByTenantIdAndTitle(UUID tenantId, String title);

    @Modifying
    @Transactional
    @Query("UPDATE Settings s SET s.value = ?3 WHERE s.tenantId = ?1 AND s.title = ?2")
    int updateByTenantIdAndTitle(UUID tenantId, String title, JsonNode value);

    boolean existsByTenantIdAndTitle(UUID tenantId, String title);
}
