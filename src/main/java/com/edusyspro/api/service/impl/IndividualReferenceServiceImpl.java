package com.edusyspro.api.service.impl;

import com.edusyspro.api.model.IndividualReference;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.IndividualType;
import com.edusyspro.api.repository.IndividualReferenceRepository;
import com.edusyspro.api.service.interfaces.IndividualReferenceService;
import com.edusyspro.api.service.interfaces.SchoolService;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Map;
import java.util.UUID;

@Service
public class IndividualReferenceServiceImpl implements IndividualReferenceService {

    private final IndividualReferenceRepository individualReferenceRepository;
    private final SchoolService schoolService;

    private static final Map<IndividualType, Integer> PREFIX_CODE = Map.of(
            IndividualType.STUDENT, 1,
            IndividualType.TEACHER, 2,
            IndividualType.EMPLOYEE, 5,
            IndividualType.GUARDIAN, 7
    );

    public IndividualReferenceServiceImpl(IndividualReferenceRepository individualReferenceRepository, SchoolService schoolService) {
        this.individualReferenceRepository = individualReferenceRepository;
        this.schoolService = schoolService;
    }

    @Override
    public String generateReference(IndividualType type, UUID schoolId) {
        return generatePrefixCode(type, schoolId);
    }

    @Override
    public String generateReference(IndividualType type) {
        return generatePrefixCode(type, null);
    }

    private String generatePrefixCode(IndividualType type, UUID schoolId) {
        boolean isGlobal = (type == IndividualType.TEACHER || type == IndividualType.GUARDIAN);
        UUID keySchool = isGlobal ? null : schoolId;
        String abbr = "";

        if (!isGlobal && keySchool == null) {
            throw new IllegalArgumentException("School id and abbr is required for non global individual reference");
        }

        int year = Year.now().getValue();
        int yy = year % 100;

        IndividualReference counter;

        if (isGlobal) {
            counter = individualReferenceRepository
                    .findByIndividualTypeAndYear(type, year)
                    .orElseGet(() -> {
                        IndividualReference c = IndividualReference.builder()
                                .individualType(type)
                                .year(year)
                                .counter(0L)
                                .build();

                        return individualReferenceRepository.save(c);
                    });
        }else {
            School school = schoolService.getSchool(schoolId.toString());
            abbr = school.getAbbr();
            counter = individualReferenceRepository
                    .findBySchoolIdAndIndividualTypeAndYear(keySchool, type, year)
                    .orElseGet(() -> {
                        IndividualReference c = IndividualReference.builder()
                                .individualType(type)
                                .schoolId(school.getId())
                                .schoolAbbr(school.getAbbr())
                                .year(year)
                                .counter(0L)
                                .build();

                        return individualReferenceRepository.save(c);
                    });
        }

        long seq = counter.getCounter() + 1;
        counter.setCounter(seq);
        individualReferenceRepository.save(counter);

        int prefix = PREFIX_CODE.get(type);
        String yyPart = String.format("%02d", yy);
        String seqPart = String.format("%04d", seq);
        String numberPart = prefix + yyPart + seqPart;

        switch (type) {
            case EMPLOYEE, STUDENT -> {
                return abbr + numberPart;
            }
            case GUARDIAN -> {
                return "GDN" + numberPart;
            }
            case TEACHER -> {
                return "TCH" + numberPart;
            }
            default -> throw new IllegalArgumentException("Invalid individual type " + type);
        }
    }
}
