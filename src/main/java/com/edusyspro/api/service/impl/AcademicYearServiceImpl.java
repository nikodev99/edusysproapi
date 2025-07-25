package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.AcademicYearDTO;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.model.School;
import com.edusyspro.api.repository.AcademicYearRepository;
import com.edusyspro.api.repository.context.UpdateContext;
import com.edusyspro.api.service.interfaces.AcademicYearService;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearRepository academicYearRepository;
    private final UpdateContext updateContext;

    @Autowired
    public AcademicYearServiceImpl(AcademicYearRepository academicYearRepository, UpdateContext updateContext) {
        this.academicYearRepository = academicYearRepository;
        this.updateContext = updateContext;
    }

    @Override
    public Boolean addAcademicYear(AcademicYearDTO academicYear) {
        boolean exists = academicYearExists(academicYear);
        UUID addedAcademicYearId = null;
        if (exists)
            throw new AlreadyExistException("Academic Year already exists");

        AcademicYearDTO currentAcademicYear = getCurrentAcademicYear(
                academicYear.getSchool().getId().toString()
        );

        boolean status = changeAcademicYearStatus(currentAcademicYear);

        if (status)
            addedAcademicYearId = academicYearRepository
                    .save(academicYear.toEntity())
                    .getId();

        return addedAcademicYearId != null;
    }

    @Override
    public int updateAcademicYearField(String academicYearId, UpdateField field) {
        return updateContext.updateAcademicYearField(field.field(), field.value(), academicYearId);
    }

    @Override
    public List<AcademicYearDTO> getAcademicYearsFromYear(String schoolId, int year) {
        return academicYearRepository.findAllBeginningOfYear(UUID.fromString(schoolId), year);
    }

    @Override
    public AcademicYearDTO getAcademicYearsFromDate(String schoolId, LocalDate date) {
        return academicYearRepository.findBeginningOfYear(UUID.fromString(schoolId), date);
    }

    @Override
    public AcademicYearDTO getCurrentAcademicYear(String schoolId) {
        return academicYearRepository.findAcademicYearBySchoolIdAndCurrentIsTrue(UUID.fromString(schoolId));
    }

    @Override
    public AcademicYearDTO getAcademicYearById(String academicYearId) {
        return academicYearRepository.findAcademicYearById(UUID.fromString(academicYearId))
                .orElseThrow(() -> new NotFountException("Academic Year Not found for ID: " + academicYearId));
    }

    @Override
    public List<AcademicYearDTO> getAllSchoolAcademicYear(String schoolId) {
        return academicYearRepository.findAllBySchoolId(UUID.fromString(schoolId));
    }

    public String getAcademicYearForSchool(School school) {
        Optional<Tuple> year = academicYearRepository.findBySchool(school.getId());
        Tuple tuple = year.orElseThrow();
        LocalDate startDate = LocalDate.parse(tuple.get(0).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(tuple.get(1).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return startDate.getYear() + "-" + endDate.getYear();
    }

    private Boolean changeAcademicYearStatus(AcademicYearDTO currentAcademicYear) {
        int updated = academicYearRepository.changeAcademicYearStatus(
                currentAcademicYear.getId()
        );
        return updated > 0;
    }

    /**
     * Checks if an AcademicYear with the same start date, end date, and school ID already exists in the database.
     *
     * @param academicYear the AcademicYear object containing the start date, end date, and school information to be checked
     * @return true if an academic year with the same attributes exists, false otherwise
     */
    private Boolean academicYearExists(AcademicYearDTO academicYear) {
        Long count = academicYearRepository.countAcademicByStartDateAndEndDate(
                academicYear.getStartDate(),
                academicYear.getEndDate(),
                academicYear.getSchool().getId()
        );

        return count > 0;
    }
}
