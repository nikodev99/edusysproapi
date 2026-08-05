package com.edusyspro.api.controller;

import com.edusyspro.api.auth.response.MessageResponse;
import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.StudentBossDTO;
import com.edusyspro.api.dto.TeacherBossDTO;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.service.interfaces.ClasseBossService;
import com.edusyspro.api.service.mod.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/classes"})
public class ClasseController {

    private final ClasseService classeService;
    private final ClasseBossService<StudentBossDTO> studentBossService;
    private final ClasseBossService<TeacherBossDTO> teacherBossService;

    @Autowired
    public ClasseController(
            ClasseService classeService,
            ClasseBossService<StudentBossDTO> studentBossService,
            ClasseBossService<TeacherBossDTO> teacherBossService
    ) {
        this.classeService = classeService;
        this.studentBossService = studentBossService;
        this.teacherBossService = teacherBossService;
    }

    @PostMapping
    ResponseEntity<?> saveClasse(@RequestBody ClasseDTO classeDTO) {
        try {
            return ResponseEntity.ok(classeService.save(classeDTO));
        }catch (AlreadyExistException a) {
            return ResponseEntity.badRequest().body(a.getMessage());
        }
    }

    @GetMapping("/all/{schoolId}")
    ResponseEntity<Page<ClasseDTO>> getAllClasses(
            @PathVariable String schoolId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        return ResponseEntity.ok(classeService.getAllClassesBySchool(
                schoolId, ControllerUtils.setSort(page, size, sortCriteria)
        ));
    }

    @GetMapping("/all/{schoolId}/{teacherId}")
    ResponseEntity<?> getAllClasse(
            @PathVariable String schoolId,
            @PathVariable String teacherId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        return ResponseEntity.ok(
                classeService.fetchAll(ControllerUtils.setSort(page, size, sortCriteria), teacherId, schoolId)
        );
    }

    @GetMapping("/search/{schoolId}")
    ResponseEntity<List<ClasseDTO>> getAllClasses(@PathVariable String schoolId, @RequestParam String q) {
        return ResponseEntity.ok(classeService.getAllClassesBySchool(
                schoolId, String.valueOf(q)
        ));
    }

    @GetMapping("/search/{schoolId}/{teacherId}")
    ResponseEntity<List<ClasseDTO>> getAllClasses(@PathVariable String schoolId, @PathVariable String teacherId, @RequestParam String q) {
        return ResponseEntity.ok(classeService.fetchAllById(
                teacherId, schoolId, String.valueOf(q)
        ));
    }

    @GetMapping("/{classeId}")
    ResponseEntity<ClasseDTO> getOneClasse(
            @PathVariable int classeId,
            @RequestParam String academicYear,
            @RequestParam(required = false, defaultValue = "false") boolean basic
    ) {
        if (basic) {
            return ResponseEntity.ok(classeService.getClasseById(classeId));
        }
        return ResponseEntity.ok(classeService.getClasseById(classeId, academicYear));
    }

    @GetMapping("/basic/{schoolId}")
    ResponseEntity<List<?>> getAllClassesBasicValue(@PathVariable String schoolId) {
        return ResponseEntity.ok(classeService.getClassBasicValues(schoolId));
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

    @PatchMapping("/{classeId}")
    ResponseEntity<?> updatePrincipalCourse(@PathVariable int classeId, @RequestBody UpdateField field) {
        return ResponseEntity.ok(classeService.patch(classeId, field));
    }

    @PostMapping("/student_boss")
    ResponseEntity<?> addClasseStudentBoss(@RequestBody StudentBossDTO studentBoss) {
        try {
            int insertedStudentBossId = studentBossService.saveClasseBoss(studentBoss);
            return ResponseEntity.ok(MessageResponse.builder()
                    .message("New teacher " + insertedStudentBossId + " boss successfully added")
                    .timestamp(Instant.now().toString())
                    .build());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/teacher_boss")
    ResponseEntity<?> addClasseTeacherBoss(@RequestBody TeacherBossDTO studentBoss) {
        try {
            int insertedTeacherBossId = teacherBossService.saveClasseBoss(studentBoss);
            return ResponseEntity.ok(MessageResponse.builder()
                    .message("New teacher " + insertedTeacherBossId + " boss successfully added")
                    .timestamp(Instant.now().toString())
                    .build());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
