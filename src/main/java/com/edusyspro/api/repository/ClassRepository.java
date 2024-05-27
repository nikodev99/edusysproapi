package com.edusyspro.api.repository;

import com.edusyspro.api.entities.Classe;
import com.edusyspro.api.entities.enums.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Classe, Integer> {

    Classe getClasseById(int id);

    List<Classe> getClassesByGradeSection(Section section);

}
