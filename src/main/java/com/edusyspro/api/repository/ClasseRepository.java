package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.*;
import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.enums.AffiliationStatus;
import com.edusyspro.api.model.enums.Section;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClasseRepository extends JpaRepository<ClasseEntity, Integer> {

    @Query("""
        select new com.edusyspro.api.dto.custom.ClassBasicValue(c.id, c.name, c.category, g.section, d.name, d.code)
        from ClasseEntity c left join c.grade g left join c.department d where g.school.id = ?1 or d.school.id = ?1
    """)
    List<ClassBasicValue> findAllBasicValue(UUID schoolID);

    @Query("""
        select new com.edusyspro.api.dto.custom.ClasseEssential(c.id, c.name, c.category, g.section, g.subSection,
        c.roomNumber, d.name, d.code, c.monthCost, c.createdAt) from ClasseEntity c left join c.grade g left join c.department d
        where (g is null or g.school.id = ?1) and (d is null or d.school.id = ?1)
    """)
    Page<ClasseEssential> findAllClassesBySchool(UUID schoolID, Pageable pageable);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.ClasseEssential(c.id, c.name, c.category, g.section, g.subSection,
        c.roomNumber, d.name, d.code, c.monthCost, c.createdAt) FROM ClasseEntity c LEFT JOIN c.grade g LEFT JOIN c.department d JOIN c.classTeachers ct
        JOIN ct.affiliation a WHERE a.teacher.id = ?1 AND a.status = ?2 AND a.school.id = ?3
    """)
    Page<ClasseEssential> findAllClasseTeacherContext(UUID teacherId, AffiliationStatus status, UUID schoolId, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.ClasseEssential(c.id, c.name, c.category, g.section, g.subSection,
        c.roomNumber, d.name, d.code, c.monthCost, c.createdAt) from ClasseEntity c left join c.grade g left join c.department d
        where ((g is null or g.school.id = ?1) or (d is null or d.school.id = ?1)) and (lower(c.name) like lower(?2) or
        lower(c.category) like lower(?2) or lower(g.section) like lower(?2)) order by c.createdAt desc
    """)
    List<ClasseEssential> findAllClassesBySchool(UUID schoolID, String classeName);

    @Query("""
        SELECT new com.edusyspro.api.dto.custom.ClasseEssential(c.id, c.name, c.category, g.section, g.subSection,
        c.roomNumber, d.name, d.code, c.monthCost, c.createdAt) FROM ClasseEntity c LEFT JOIN c.grade g LEFT JOIN c.department d JOIN c.classTeachers ct
        JOIN ct.affiliation a WHERE a.teacher.id = ?1 AND a.status = ?2 AND a.school.id = ?3 and (lower(c.name) like lower(?2) or
        lower(c.category) like lower(?2) or lower(g.section) like lower(?2)) order by c.createdAt desc
    """)
    List<ClasseEssential> findAllClasseTeacherContext(UUID teacherId, AffiliationStatus status, UUID schoolId, String classeName);

    @Query("""
        select new com.edusyspro.api.dto.custom.ClasseEssential(c.id, c.name, c.category, c.grade.section, c.grade.subSection,
        c.roomNumber, c.department.name, c.department.code, c.monthCost, c.createdAt) from ClasseEntity c left join c.department
        where c.id = ?1
    """)
    ClasseEssential findClasseById(int id);

    @Query("""
        select new com.edusyspro.api.dto.custom.CourseBasicValue(
            c.id, c.course, c.courseType, c.abbr, c.discipline
        ) from ClasseEntity cl join cl.principalCourse c where cl.id = ?1
    """)
    Optional<CourseBasicValue> findClassePrincipalCourse(int classeId);

    @Query("""
        select new com.edusyspro.api.dto.custom.GradeBasicValue(c.grade.id, c.grade.section, c.grade.subSection,
        c.grade.createdAt, c.grade.modifyAt) from ClasseEntity c where c.id = ?1
    """)
    GradeBasicValue findGradeByClasseId(int classeId);

    @Modifying
    @Transactional
    @Query("""
        update ClasseEntity c set c.name = ?1, c.category = ?2, c.grade.id = ?3, c.roomNumber = ?4, c.principalCourse.id = ?5, c.monthCost = ?6 where c.id = ?7
    """)
    Optional<Integer> updateClasseValues(
            String classeName,
            String classeCategory,
            int gradeId,
            int roomNumber,
            int courseId,
            BigDecimal monthCost,
            int classeId
    );

    @Modifying
    @Transactional
    @Query("UPDATE ClasseEntity c SET c.principalCourse.id = ?2 WHERE c.id = ?1")
    Optional<Integer> updateClassePrincipalCourse(int classeId, int courseId);

    List<ClasseEntity> getClassesByGradeSection(Section section);

    @Query("""
        select count(c.id) from ClasseEntity c left join c.grade g where g.id = ?1 and lower(c.name) = lower(?2)
    """ )
    Long countBySchoolAndName(Integer gradeId, String classeName);

    //UPDATE CLASSE:
    @Query("select c.id from ClasseEntity c left join c.grade g left join c.department d " +
            "where c.id in :ids and " +
            "((g is not null and g.school.id = :schoolId) or (d is not null and d.school.id = :schoolId))")
    List<Integer> findValidIdsForSchool(@Param("ids") List<Integer> ids, @Param("schoolId") UUID schoolId);

    //TEST
    ClasseEntity getClasseById(int id);
}
