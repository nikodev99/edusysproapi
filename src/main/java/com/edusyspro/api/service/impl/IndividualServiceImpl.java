package com.edusyspro.api.service.impl;

import com.edusyspro.api.auth.user.UserType;
import com.edusyspro.api.dto.IndividualUser;
import com.edusyspro.api.dto.custom.SchoolBasic;
import com.edusyspro.api.repository.IndividualRepository;
import com.edusyspro.api.service.interfaces.IndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IndividualServiceImpl implements IndividualService {

    private final IndividualRepository individualRepository;

    @Autowired
    public IndividualServiceImpl(IndividualRepository individualRepository) {
        this.individualRepository = individualRepository;
    }

    @Override
    public IndividualUser getLoginUser(Long personalId, UserType type) {
        List<Object[]> individualData = new ArrayList<>();
        switch (type) {
            case TEACHER -> individualData = individualRepository.findTeacherIdByPersonalInfoId(personalId);
            case EMPLOYEE -> individualData = individualRepository.findEmployeeIdByPersonalInfoId(personalId);
            case GUARDIAN -> individualData = individualRepository.findGuardianIdPersonalInfoId(personalId);
        }

        if (individualData.isEmpty()) {
            return null;
        }

        final List<Object[]> finalIndividualData = new LinkedList<>(individualData);

        return individualData.stream()
                .findFirst()
                .map(firstRow -> mapToIndividualUser(firstRow, finalIndividualData))
                .orElse(null) ;
    }

    private IndividualUser mapToIndividualUser(Object[] firstRow, List<Object[]> allRows) {
        UUID userId = (UUID)   firstRow[0];
        String fn   = (String) firstRow[1];
        String ln   = (String) firstRow[2];

        List<SchoolBasic> schools = allRows.stream()
                .filter(r -> r[3] != null)
                .map(r -> new SchoolBasic(
                        (UUID)   r[3],
                        (String) r[4],
                        (String) r[5],
                        (String) r[6]
                ))
                .collect(Collectors.toList());

        return IndividualUser.builder()
                .userId(userId)
                .firstName(fn)
                .lastName(ln)
                .schools(schools)
                .build();
    }
}
