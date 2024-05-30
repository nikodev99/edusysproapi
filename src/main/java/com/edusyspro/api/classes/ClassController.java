package com.edusyspro.api.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = {"/api/v1"})
public class ClassController {

    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/classes/basic")
    ResponseEntity<List<ClassBasicValue>> getAllClassesBasicValue() {
        return ResponseEntity.ok(classService.getClassBasicValues(UUID.fromString("27a58e8a-a588-45dd-917e-6b690acd4b22")));
    }

    @GetMapping(value = {"/classes", "/classe/all"})
    ResponseEntity<List<Classe>> getClasses() {
        return ResponseEntity.ok(classService.getClasses());
    }
}
