package com.edusyspro.api.repository;

import com.edusyspro.api.dto.ClassBasicValue;
import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.enums.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClasseRepository extends JpaRepository<ClasseEntity, Integer> {

    @Query("""
        select new com.edusyspro.api.dto.ClassBasicValue(c.id, c.name, c.category, c.grade.section) from ClasseEntity c where c.grade.school.id = ?1
    """)
    List<ClassBasicValue> findAllBasicValue(UUID schoolID);

    ClasseEntity getClasseById(int id);

    List<ClasseEntity> getClassesByGradeSection(Section section);

}
