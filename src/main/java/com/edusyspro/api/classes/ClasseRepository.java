package com.edusyspro.api.classes;

import com.edusyspro.api.classes.dtos.ClassBasicValue;
import com.edusyspro.api.entities.enums.Section;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClasseRepository extends JpaRepository<ClasseEntity, Integer> {

    @Query("""
        select new com.edusyspro.api.classes.dtos.ClassBasicValue(c.id, c.name, c.category, c.grade.section) from ClasseEntity c where c.grade.school.id = ?1
    """)
    List<ClassBasicValue> findAllBasicValue(UUID schoolID);

    ClasseEntity getClasseById(int id);

    List<ClasseEntity> getClassesByGradeSection(Section section);

}
