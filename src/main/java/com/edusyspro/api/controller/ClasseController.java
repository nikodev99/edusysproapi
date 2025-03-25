package com.edusyspro.api.controller;

import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.service.mod.ClasseService;
import com.edusyspro.api.data.ConstantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/classes"})
public class ClasseController {

    private final ClasseService classeService;

    @Autowired
    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @PostMapping
    ResponseEntity<?> saveClasse(@RequestBody ClasseDTO classeDTO) {
        try {
            return ResponseEntity.ok(classeService.save(classeDTO));
        }catch (AlreadyExistException a) {
            return ResponseEntity.badRequest().body(a.getMessage());
        }
    }

    @GetMapping
    ResponseEntity<Page<ClasseDTO>> getAllClasses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        return ResponseEntity.ok(classeService.getAllClassesBySchool(
                ConstantUtils.SCHOOL_ID,
                ControllerUtils.setSort(page, size, sortCriteria)
        ));
    }

    @GetMapping("/search/")
    ResponseEntity<List<ClasseDTO>> getAllClasses(@RequestParam String q) {
        return ResponseEntity.ok(classeService.getAllClassesBySchool(
                ConstantUtils.SCHOOL_ID,
                String.valueOf(q)
        ));
    }

    @GetMapping("/{classeId}")
    ResponseEntity<ClasseDTO> getOneClasse(@PathVariable int classeId, @RequestParam String academicYear) {
        return ResponseEntity.ok(classeService.getClasseById(classeId, academicYear));
    }

    @GetMapping("/basic")
    ResponseEntity<List<?>> getAllClassesBasicValue() {
        return ResponseEntity.ok(classeService.getClassBasicValues(ConstantUtils.SCHOOL_ID));
    }

    @PutMapping("/{classeId}")
    ResponseEntity<?> updateClasseValues(@PathVariable int classeId, @RequestBody ClasseDTO classeDTO) {
        Map<String, String> response = Map.of();
        try {
            Map<String, Boolean> hasUpdate = classeService.update(classeDTO, classeId);
            if (hasUpdate.containsKey("updated")) {
                response = Map.of("updated", "Mise à jour de la classe effective");
            }
            return ResponseEntity.ok(response);
        }catch (AlreadyExistException a) {
            response = Map.of("error", a.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
