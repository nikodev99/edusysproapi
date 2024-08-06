package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.model.Reprimand;

import java.util.List;

public interface ReprimandService {

    List<Reprimand> findStudentReprimand(String studentId);

}
