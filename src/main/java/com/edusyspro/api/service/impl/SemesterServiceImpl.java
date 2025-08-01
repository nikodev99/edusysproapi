package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.model.Semester;
import com.edusyspro.api.repository.SemesterRepository;
import com.edusyspro.api.service.interfaces.SemesterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepository;

    public SemesterServiceImpl(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    @Override
    public Semester save(Semester entity) {
        Optional<Semester> fetchedSemester = entity.getSemesterId() != null
                ? semesterRepository.findSemesterBySemesterId(entity.getSemesterId())
                : semesterRepository.findSemesterBySemesterName(entity.getSemesterName());

        if(fetchedSemester.isPresent()) {
            Optional<Semester> sameSemester = semesterRepository.findSemesterByAcademicYearId(entity.getAcademicYear().getId());
            if (sameSemester.isPresent()) {
                throw new AlreadyExistException("Semester\\Trimestre existe déjà");
            }else {
                Semester semester = fetchedSemester.get();
                semesterRepository.updateSemesterBySemesterId(entity.getAcademicYear(), semester.getSemesterId());
                return semester;
            }
        }
        return semesterRepository.save(entity);
    }

    @Override
    public List<Semester> saveAll(List<Semester> entities) {
        entities.forEach(this::save);
        return entities;
    }

    @Override
    public List<Semester> fetchAll() {
        return List.of();
    }

    @Override
    public List<Semester> fetchAll(String schoolId) {
        return semesterRepository.getAllBySchoolId(UUID.fromString(schoolId));
    }

    @Override
    public Page<Semester> fetchAll(String schoolId, Pageable pageable) {
        return null;
    }

    @Override
    public List<Semester> fetchAll(Object... args) {
        return List.of();
    }

    @Override
    public Page<Semester> fetchAll(Pageable pageable, Object... args) {
        return null;
    }

    @Override
    public List<Semester> fetchAllById(Integer id) {
        return List.of();
    }

    @Override
    public List<Semester> fetchAllById(Object... arg) {
        return List.of();
    }

    @Override
    public Page<Semester> fetchAllByOtherEntityId(String otherEntityId, Pageable pageable) {
        return null;
    }

    @Override
    public List<Semester> fetchAllByOtherEntityId(String otherEntityId) {
        return List.of();
    }

    @Override
    public List<Semester> fetchAllByOtherEntityId(Object... arg) {
        return List.of();
    }

    @Override
    public Semester fetchOneById(Integer id) {
        return null;
    }

    @Override
    public Semester fetchOneById(Integer id, String schoolId) {
        return null;
    }

    @Override
    public Semester fetchOneById(Integer id, Object... args) {
        return null;
    }

    @Override
    public Semester fetchOneByCustomColumn(String columnValue, String schoolId) {
        return null;
    }

    @Override
    public Semester fetchOneByCustomColumn(String columnValue) {
        return null;
    }

    @Override
    public Semester fetchOneByCustomColumn(String columnValue, Object... args) {
        return null;
    }

    @Override
    public Semester fetchOneById(Object... arg) {
        return null;
    }

    @Override
    public int update(Semester entity) {
        return 0;
    }

    @Override
    public Map<String, Boolean> update(Semester entity, Integer id) {
        return Map.of();
    }

    @Override
    public int patch(Integer id, UpdateField field) {
        return 0;
    }

    @Override
    public int delete(Semester entity) {
        return 0;
    }

    @Override
    public Map<String, Long> count(Integer id) {
        return Map.of();
    }

    @Override
    public Map<String, Long> count(String schoolId) {
        return Map.of();
    }

    @Override
    public Map<String, Long> count(Object... args) {
        return Map.of();
    }
}
