package com.edusyspro.api.controller;

import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.dto.Teacher;
import com.edusyspro.api.exception.AlreadyExistException;
import com.edusyspro.api.repository.TeacherRepository;
import com.edusyspro.api.service.interfaces.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherController(TeacherService teacherService, TeacherRepository teacherRepository) {
        this.teacherService = teacherService;
        this.teacherRepository = teacherRepository;
    }

    @PostMapping
    ResponseEntity<?> saveTeacher(@RequestBody Teacher teacher) {
        System.out.println("Teacher à inséré: " + teacher);
        try {
            return ResponseEntity.ok(teacherService.save(teacher));
        }catch (AlreadyExistException a) {
            return ResponseEntity.badRequest().body(a.getMessage());
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getTeacher(@PathVariable String id) {
        return ResponseEntity.ok(teacherRepository.findById(UUID.fromString(id)));
    }

    @GetMapping(value = {"", "/all"})
    ResponseEntity<Page<Teacher>> getTeachers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);
        return ResponseEntity.ok(teacherService.fetchAll(ConstantUtils.SCHOOL_ID, pageable));
    }

    @GetMapping("/search/")
    ResponseEntity<List<Teacher>> getSearchedTeachers(@RequestParam String q) {
        return ResponseEntity.ok(teacherService.fetchAll(ConstantUtils.SCHOOL_ID, q));
    }
}
