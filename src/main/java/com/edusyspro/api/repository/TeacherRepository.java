package com.edusyspro.api.repository;

import com.edusyspro.api.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    Page<Teacher> findAllBySchoolId(UUID schoolId, Pageable pageable);

    @Query("select t from Teacher t where t.school.id = ?1 and (lower(t.lastName) like lower(?2) or lower(t.firstName) like lower(?2)) order by t.lastName asc")
    List<Teacher> findAllBySchoolId(UUID schoolId, String lastname);

    Optional<Teacher> findTeacherByIdAndSchoolId(UUID id, UUID schoolId);

    boolean existsByEmailIdAndSchoolId(String emailId, UUID schoolId);
}
