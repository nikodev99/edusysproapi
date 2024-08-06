package com.edusyspro.api.service;

import com.edusyspro.api.entities.Reprimand;

import java.util.List;

public interface ReprimandService {

    List<Reprimand> findStudentReprimand(String studentId);

}
