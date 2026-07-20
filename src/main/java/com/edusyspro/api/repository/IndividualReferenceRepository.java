package com.edusyspro.api.repository;

import com.edusyspro.api.model.IndividualReference;
import com.edusyspro.api.model.enums.IndividualType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IndividualReferenceRepository extends JpaRepository<IndividualReference, Integer> {
    Optional<IndividualReference> findBySchoolIdAndIndividualTypeAndYear(
            UUID schoolId, IndividualType individualType, int year
    );

    Optional<IndividualReference> findByIndividualTypeAndYear(IndividualType individualType, int year);
}
