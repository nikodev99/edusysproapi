package com.edusyspro.api.repository;

import com.edusyspro.api.dto.ClassBasicValue;
import com.edusyspro.api.dto.EnrolledStudent;
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
        select new com.edusyspro.api.dto.ClassBasicValue(c.id, c.name, c.category, c.grade.section) from ClasseEntity c where c.grade.school.id = ?1
    """)
    List<ClassBasicValue> findAllBasicValue(UUID schoolID);

    ClasseEntity getClasseById(int id);

    @Query("""
        select c from ClasseEntity c join c.schedule s where c.id = ?1 and s.teacher.id = ?2
    """)
    ClasseEntity getClasseStudentByIdAndTeacherSchedule(int classeId, UUID teacherId);

    @Query(value = """
            select new com.edusyspro.api.dto.EnrolledStudent(e.student.id, e.academicYear, e.student.personalInfo.firstName,
            e.student.personalInfo.lastName, e.student.personalInfo.gender, e.student.personalInfo.emailId, e.student.personalInfo.birthDate,
            e.student.personalInfo.birthCity, e.student.personalInfo.nationality, e.student.reference, e.student.personalInfo.image,
            e.enrollmentDate, e.classe.id, e.classe.name, e.classe.category, e.classe.grade.section, e.classe.monthCost,
            e.student.dadName, e.student.momName) from ClasseEntity c join c.students e where e.academicYear.school.id = ?1
            and e.student.id <> ?2 and e.classe.id = ?3 and e.academicYear.id = ?4 order by e.student.personalInfo.lastName asc
   """)
    Page<EnrolledStudent> getEnrolledStudentsByClassId(int classeId, Pageable pageable);

    List<ClasseEntity> getClassesByGradeSection(Section section);

}
