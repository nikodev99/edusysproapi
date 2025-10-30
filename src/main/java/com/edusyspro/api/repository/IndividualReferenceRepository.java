package com.edusyspro.api.repository;

import com.edusyspro.api.model.IndividualReference;
import com.edusyspro.api.model.enums.IndividualType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IndividualReferenceRepository extends JpaRepository<IndividualReference, Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<IndividualReference> findBySchoolIdAndIndividualTypeAndYear(
            UUID schoolId, IndividualType individualType, int year
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<IndividualReference> findByIndividualTypeAndYear(IndividualType individualType, int year);
}
