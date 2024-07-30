package com.edusyspro.api.student.repos;

import com.edusyspro.api.entities.Address;
import com.edusyspro.api.student.entities.GuardianEntity;
import com.edusyspro.api.student.entities.HealthCondition;
import com.edusyspro.api.student.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
