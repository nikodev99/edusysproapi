package com.edusyspro.api.controller;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.service.mod.ClasseService;
import com.edusyspro.api.dto.custom.ClassBasicValue;
import com.edusyspro.api.data.ConstantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = {"/classes"})
public class ClasseController {

    private final ClasseService classeService;

    @Autowired
    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @GetMapping("/basic")
    ResponseEntity<List<?>> getAllClassesBasicValue() {
        return ResponseEntity.ok(classeService.getClassBasicValues(ConstantUtils.SCHOOL_ID));
    }

    @GetMapping(value = {"", "/all"})
    ResponseEntity<List<ClasseDTO>> getClasses() {
        return ResponseEntity.ok(classeService.getClasses());
    }
}
