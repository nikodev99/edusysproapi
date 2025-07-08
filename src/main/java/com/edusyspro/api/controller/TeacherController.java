package com.edusyspro.api.controller;

import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.dto.TeacherDTO;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.service.mod.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    ResponseEntity<?> saveTeacher(@RequestBody TeacherDTO teacherDTO) {
        try {
            return ResponseEntity.ok(teacherService.saveTeacher(teacherDTO));
        }catch (AlreadyExistException a) {
            return ResponseEntity.badRequest().body(a.getMessage());
        }
    }

    @GetMapping("/{id}/{schoolId}")
    ResponseEntity<?> getTeacher(@PathVariable(name = "id") String id, @PathVariable(name = "schoolId") String schoolId) {
        try {
            return ResponseEntity.ok(teacherService.findTeacherById(id, schoolId));
        }catch (NotFountException n) {
            return ResponseEntity.badRequest().body(n.getMessage());
        }
    }

    @GetMapping(value = {"/{schoolId}", "/all/{schoolId}"})
    ResponseEntity<Page<TeacherDTO>> getTeachers(
            @PathVariable(name = "schoolId") String schoolId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);
        return ResponseEntity.ok(teacherService.findAllTeachers(schoolId, pageable));
    }

    @GetMapping("/search/{schoolId}")
    ResponseEntity<List<TeacherDTO>> getSearchedTeachers(@PathVariable(name = "schoolId") String schoolId, @RequestParam String q) {
        return ResponseEntity.ok(teacherService.findAllTeachers(schoolId, q));
    }

    @GetMapping("/basic/{classeId}")
    ResponseEntity<?> getTeachersBasicValues(@PathVariable int classeId, @RequestParam Section section) {
        return ResponseEntity.ok(teacherService.findAllTeacherBasicValue(classeId, section));
    }

    @GetMapping("/basic-one/{teacherId}")
    ResponseEntity<?> getTeacherBasicValues(@PathVariable long teacherId, @RequestParam int classe) {
        return ResponseEntity.ok(teacherService.findTeacherBasicValue(teacherId, classe));
    }

    @GetMapping("/count/{schoolId}")
    ResponseEntity<?> countTeachers(@PathVariable(name = "schoolId") String schoolId) {
        return ResponseEntity.ok(teacherService.countAllTeachers(schoolId));
    }

    @GetMapping("/{id}/count_student")
    ResponseEntity<Map<String, Long>> getTeacherStudentCounts(@PathVariable String id) {
        return ResponseEntity.ok(teacherService.count(UUID.fromString(id)));
    }

    @GetMapping("/count_by_classe/{teacherId}")
    ResponseEntity<?> countStudentsByClasse(@PathVariable String teacherId) {
        return ResponseEntity.ok(teacherService.countStudentsByClasse(UUID.fromString(teacherId)));
    }

    @PatchMapping("/{id}")
    ResponseEntity<String> updateTeacherField(@PathVariable String id, @RequestBody UpdateField teacher) {
        try {
            int updated = teacherService.updateTeacherField(id, teacher);
            if (updated > 0) {
                return ResponseEntity.ok("Modification " + teacher.field() + " effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found or update failed");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
