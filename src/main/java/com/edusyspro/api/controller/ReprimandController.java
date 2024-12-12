package com.edusyspro.api.controller;

import com.edusyspro.api.model.Reprimand;
import com.edusyspro.api.service.interfaces.ReprimandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/blame"})
public class ReprimandController {

    private final ReprimandService reprimandService;

    @Autowired
    public ReprimandController(ReprimandService reprimandService) {
        this.reprimandService = reprimandService;
    }

    @GetMapping("/{studentId}")
    ResponseEntity<List<Reprimand>> getReprimands(@PathVariable long studentId) {
        return ResponseEntity.ok(reprimandService.findStudentReprimand(studentId));
    }

    @GetMapping("/teacher_some/{teacherId}")
    ResponseEntity<?> getSomeStudentReprimandsByTeacher(@PathVariable String teacherId) {
        return ResponseEntity.ok(reprimandService.fetchSomeStudentReprimandedByTeacher(Long.parseLong(teacherId)));
    }

}
