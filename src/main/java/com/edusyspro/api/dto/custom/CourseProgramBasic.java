package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.CourseProgramDTO;

import java.time.LocalDate;

public record CourseProgramBasic(
        Long id,
        String topic,
        LocalDate updateDate,
        boolean active
) {
    public CourseProgramDTO toDTO() {
        return CourseProgramDTO.builder()
                .id(id)
                .topic(topic)
                .updateDate(updateDate)
                .active(active)
                .build();
    }
}
