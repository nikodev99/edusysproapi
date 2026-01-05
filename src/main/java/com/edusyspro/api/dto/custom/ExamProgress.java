package com.edusyspro.api.dto.custom;

public record ExamProgress(
        String examName,
        Double average,
        String examDate
) {
}
