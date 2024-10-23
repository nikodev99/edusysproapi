package com.edusyspro.api.controller;

import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.dto.Teacher;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.service.mod.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @PostMapping
    ResponseEntity<?> saveTeacher(@RequestBody Teacher teacher) {
        System.out.println("Data to save: " + teacher);
        try {
            return ResponseEntity.ok(teacherService.saveTeacher(teacher));
        }catch (AlreadyExistException a) {
            return ResponseEntity.badRequest().body(a.getMessage());
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getTeacher(@PathVariable String id) {
        try {
            return ResponseEntity.ok(teacherService.findTeacherById(id, ConstantUtils.SCHOOL_ID));
        }catch (NotFountException n) {
            return ResponseEntity.badRequest().body(n.getMessage());
        }
    }

    @GetMapping(value = {"", "/all"})
    ResponseEntity<Page<Teacher>> getTeachers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);
        return ResponseEntity.ok(teacherService.findAllTeachers(ConstantUtils.SCHOOL_ID, pageable));
    }

    @GetMapping("/search/")
    ResponseEntity<List<Teacher>> getSearchedTeachers(@RequestParam String q) {
        return ResponseEntity.ok(teacherService.findAllTeachers(ConstantUtils.SCHOOL_ID, q));
    }
}
