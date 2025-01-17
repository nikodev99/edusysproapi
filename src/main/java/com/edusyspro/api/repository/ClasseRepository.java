package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.ClassBasicValue;
import com.edusyspro.api.dto.custom.ClasseEssential;
import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.enums.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClasseRepository extends JpaRepository<ClasseEntity, Integer> {

    @Query("""
        select new com.edusyspro.api.dto.custom.ClassBasicValue(c.id, c.name, c.category, c.grade.section) from ClasseEntity c where c.grade.school.id = ?1
    """)
    List<ClassBasicValue> findAllBasicValue(UUID schoolID);

    @Query("""
        select new com.edusyspro.api.dto.custom.ClasseEssential(c.id, c.name, c.category, c.grade.section, c.grade.subSection,
        c.roomNumber, c.createdAt) from ClasseEntity c where c.grade.school.id = ?1
    """)
    Page<ClasseEssential> findAllCLassesBySchool(UUID schoolID, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.ClasseEssential(c.id, c.name, c.category, c.grade.section, c.grade.subSection,
        c.roomNumber, c.createdAt) from ClasseEntity c where c.grade.school.id = ?1 and (lower(c.name) like lower(?2) or
        lower(c.category) like lower(?2) or lower(c.grade.section) like lower(?2)) order by c.createdAt desc
    """)
    List<ClasseEssential> findAllCLassesBySchool(UUID schoolID, String classeName);

    @Query("""
        select new com.edusyspro.api.dto.custom.ClasseEssential(c.id, c.name, c.category, c.grade.section, c.grade.subSection,
        c.roomNumber, c.createdAt) from ClasseEntity c where c.id = ?1
    """)
    ClasseEssential findClasseById(int id);

    List<ClasseEntity> getClassesByGradeSection(Section section);

    boolean existsByName(String classeName);

    //TEST
    ClasseEntity getClasseById(int id);
}
