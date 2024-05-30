package com.edusyspro.api.classes;

import com.edusyspro.api.entities.enums.Section;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClassRepository extends JpaRepository<ClasseEntity, Integer> {

    @Query("select c.id, c.name, c.category from ClasseEntity c where c.school.id = ?1")
    List<Tuple> findAllBasicValue(UUID schoolID);

    ClasseEntity getClasseById(int id);

    List<ClasseEntity> getClassesByGradeSection(Section section);

}
