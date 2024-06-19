package com.edusyspro.api.classes;

import com.edusyspro.api.classes.dtos.ClassBasicValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = {"/classes"})
public class ClassController {

    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/basic")
    ResponseEntity<List<ClassBasicValue>> getAllClassesBasicValue() {
        return ResponseEntity.ok(classService.getClassBasicValues(UUID.fromString("19e8cf01-5098-453b-9d65-d57cd17fc548")));
    }

    @GetMapping(value = {"", "/all"})
    ResponseEntity<List<Classe>> getClasses() {
        return ResponseEntity.ok(classService.getClasses());
    }
}
