package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ScoreDTO;

public record ScoreBasicValue(byte obtainedMark) {

    public ScoreDTO toDTO() {
        return ScoreDTO.builder()
                .obtainedMark(obtainedMark)
                .build();
    }

}
