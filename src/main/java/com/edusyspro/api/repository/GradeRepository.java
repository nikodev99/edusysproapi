package com.edusyspro.api.repository;

import com.edusyspro.api.entities.Grade;
import com.edusyspro.api.entities.enums.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Grade g SET g.subSection = ?1 WHERE g.id = ?2")
    int updateGradeSubSectionById(String subSection, int id);

    @Query("SELECT g FROM Grade g LEFT JOIN FETCH g.planning WHERE g.section = ?1")
    List<Grade> findAllBySectionName(Section section);

    Grade getGradeBySection(Section section);

}
