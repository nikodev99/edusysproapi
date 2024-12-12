package com.edusyspro.api.dto;

import com.edusyspro.api.model.Assignment;
import com.edusyspro.api.model.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreDTO {
    private long id;
    private Assignment assignment;
    private StudentEntity studentEntity;
    private byte obtainedMark;
}
