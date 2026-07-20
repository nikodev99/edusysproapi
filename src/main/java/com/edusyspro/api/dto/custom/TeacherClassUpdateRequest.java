package com.edusyspro.api.dto.custom;

import com.edusyspro.api.model.enums.OperationType;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TeacherClassUpdateRequest (
    @NotNull
    OperationType operationType,

    @NotNull
    List<Integer> classIds,

    Long schoolAffiliated
) {

}
