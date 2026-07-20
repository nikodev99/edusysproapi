package com.edusyspro.api.repository;

import com.edusyspro.api.model.TeacherSchoolAffiliation;
import com.edusyspro.api.model.enums.AffiliationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TeacherSchoolAffiliationRepository extends JpaRepository<TeacherSchoolAffiliation, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE TeacherSchoolAffiliation t SET t.status = ?2 WHERE t.id = ?1")
    void inactivate(long id, AffiliationStatus status);
}
