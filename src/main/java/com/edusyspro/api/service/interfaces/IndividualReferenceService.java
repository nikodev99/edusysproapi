package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.model.enums.IndividualType;

import java.util.UUID;

public interface IndividualReferenceService {
    String generateReference(IndividualType type, UUID schoolId);
    String generateReference(IndividualType type);
}
