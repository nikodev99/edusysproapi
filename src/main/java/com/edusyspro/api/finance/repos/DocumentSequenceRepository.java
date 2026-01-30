package com.edusyspro.api.finance.repos;

import com.edusyspro.api.finance.entities.DocumentSequence;
import com.edusyspro.api.finance.enums.DocumentType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentSequenceRepository extends JpaRepository<DocumentSequence, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ds FROM DocumentSequence ds WHERE ds.schoolId = ?1 AND ds.code = ?2 AND ds.type = ?3 AND ds.year = ?4")
    Optional<DocumentSequence> findBySchoolAndCategoryAndType(UUID schoolId, String code, DocumentType type, Year year);

    @Query("SELECT s.abbr FROM School s WHERE s.id = ?1")
    Optional<String> findSchoolAbbr(UUID schoolId);
}
