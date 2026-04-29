package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.CourseProgramDTO;
import com.edusyspro.api.model.enums.ProgramStatus;

import java.time.LocalDate;
import java.util.List;

public interface CourseProgramBasic {
    Long getId();
    String getProgramName();
    String getTopicTitle();
    String getClasse();
    ProgramStatus getProgramStatus();
}
