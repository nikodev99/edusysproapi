package com.edusyspro.api.student.repos;

import com.edusyspro.api.student.entities.StudentEntity;
import com.edusyspro.api.student.models.dtos.StudentEssential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {

    /*@Query("""
            select new com.edusyspro.api.student.models.dtos.StudentEssential(s.id, s.firstName, s.lastName, s.gender, \
            s.emailId, s.birthDate, s.birthCity,s.nationality, s.dadName, s.momName, s.reference, s.telephone, s.address, \
            s.guardian.firstName, s.guardian.lastName, s.guardian.maidenName, s.guardian.status, s.guardian.genre, s.guardian.emailId, \
            s.guardian.jobTitle, s.guardian.company, s.guardian.telephone, s.guardian.mobile, s.guardian.address, s.healthCondition, \
            s.image, s.school.name, s.school.abbr, s.createdAt, s.modifyAt) \
            from StudentEntity s where s.id = ?1
    """)
    StudentEssential getStudentById(UUID uuid);*/

    StudentEntity findStudentEntityById(UUID id);

}
