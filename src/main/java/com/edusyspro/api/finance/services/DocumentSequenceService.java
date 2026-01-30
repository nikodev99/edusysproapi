package com.edusyspro.api.finance.services;

import com.edusyspro.api.finance.entities.DocumentSequence;
import com.edusyspro.api.finance.enums.DocumentType;
import com.edusyspro.api.finance.repos.DocumentSequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DocumentSequenceService {
    private final DocumentSequenceRepository documentSequenceRepository;

    @Transactional
    public String generateInvoiceNumber(String schoolId, String code) {
        return generateDocumentNumber(schoolId, code, DocumentType.INV);
    }

    @Transactional
    public String generateVoucherNumber(String schoolId, String code) {
        return generateDocumentNumber(schoolId, code, DocumentType.PMT);
    }

    @Transactional
    public String generateExpenseNumber(String schoolId, String code) {
        return generateDocumentNumber(schoolId, code, DocumentType.EXP);
    }

    @Transactional
    public String generateDocumentNumber(String schoolId, String code, DocumentType type) {
        Year currentYear = Year.now();

        DocumentSequence sequence = documentSequenceRepository.findBySchoolAndCategoryAndType(UUID.fromString(schoolId), code, type, currentYear)
                .orElseGet(() -> createNewDocumentSequence(schoolId, code, type, currentYear));

        sequence.setLastSequence(sequence.getLastSequence() + 1);
        sequence.setTotalGenerated(sequence.getTotalGenerated() + 1);

        DocumentSequence savedDocument = documentSequenceRepository.save(sequence);

        return String.format("%s/%s/%s/%s/%d",
                savedDocument.getSchoolAbbr(),
                savedDocument.getCode(),
                savedDocument.getType().name(),
                String.valueOf(savedDocument.getYear().getValue()).substring(2),
                savedDocument.getLastSequence()
        );
    }

    public Long getTotalDocumentsGenerated(UUID schoolId, String code, DocumentType type, Year year) {
        return documentSequenceRepository
                .findBySchoolAndCategoryAndType(schoolId, code, type, year)
                .map(DocumentSequence::getTotalGenerated)
                .orElse(0L);
    }

    private DocumentSequence createNewDocumentSequence(String schoolId, String categoryCode, DocumentType documentType, Year year) {
        String schoolAbbr = documentSequenceRepository.findSchoolAbbr(UUID.fromString(schoolId))
                .orElse(null);

        return DocumentSequence.builder()
                .schoolId(UUID.fromString(schoolId))
                .schoolAbbr(schoolAbbr)
                .code(categoryCode)
                .type(documentType)
                .year(year)
                .lastSequence(0L)
                .totalGenerated(0L)
                .build();
    }
}
