package com.edusyspro.api.repository;

import com.edusyspro.api.dto.StudentEssential;
import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.model.HealthCondition;
import com.edusyspro.api.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {

    StudentEntity findStudentEntityById(UUID id);

    @Query("select s.address from StudentEntity s where s.id = ?1")
    Optional<Address> findStudentEntityAddressByStudentId(UUID id);

    @Query("select s.guardian from StudentEntity s where s.id = ?1")
    Optional<GuardianEntity> getStudentEntityGuardianByStudentId(UUID id);

    @Query("select s.healthCondition from StudentEntity s where s.id = ?1")
    Optional<HealthCondition> findStudentEntityHealthConditionByStudentId(UUID id);

    @Query("""
            select new com.edusyspro.api.dto.StudentEssential(s.id, s.firstName, s.lastName, s.gender, s.emailId, s.birthDate, \
            s.birthCity, s.nationality, s.dadName, s.momName, s.reference, s.telephone, s.address, s.image) \
            from StudentEntity s where s.guardian.id = ?1
    """)
    Optional<List<StudentEssential>> findStudentByGuardianId(UUID guardianId);
}
