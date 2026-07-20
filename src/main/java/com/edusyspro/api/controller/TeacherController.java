package com.edusyspro.api.controller;

import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.dto.SchoolAffiliationDTO;
import com.edusyspro.api.dto.TeacherDTO;
import com.edusyspro.api.dto.custom.TeacherClassUpdateRequest;
import com.edusyspro.api.dto.custom.TeacherCourseUpdateRequest;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.service.mod.TeacherService;
import jakarta.validation.Valid;
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
        }catch (Exception a) {
            return ResponseEntity.badRequest().body(a.getMessage());
        }
    }

    @PostMapping("/affiliate")
    ResponseEntity<?> affiliateTeacher(@RequestBody SchoolAffiliationDTO schoolAffiliationDTO) {
        try {
            teacherService.saveTeacherSchoolAffiliate(schoolAffiliationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("New Teacher affiliated to school");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{teacherId}/{schoolId}")
    ResponseEntity<?> deleteTeacher(@PathVariable String teacherId, @PathVariable String schoolId) {
        try {
            teacherService.inactivateTeacherSchoolAffiliation(teacherId, schoolId);
            return ResponseEntity.ok("L'affiliation de cet enseignant dans votre école est terminé");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/{schoolId}")
    ResponseEntity<?> getTeacher(@PathVariable String id, @PathVariable String schoolId) {
        try {
            return ResponseEntity.ok(teacherService.findTeacherById(id, schoolId));
        }catch (NotFountException n) {
            return ResponseEntity.badRequest().body(n.getMessage());
        }
    }

    @GetMapping(value = {"/{schoolId}", "/all/{schoolId}"})
    ResponseEntity<Page<TeacherDTO>> getTeachers(
            @PathVariable String schoolId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);
        return ResponseEntity.ok(teacherService.findAllTeachers(schoolId, pageable));
    }

    @GetMapping(value = {"/self/{schoolId}/{teacherId}"})
    ResponseEntity<Page<TeacherDTO>> getTeachers(
            @PathVariable String schoolId,
            @PathVariable String teacherId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);
        return ResponseEntity.ok(teacherService.findAllTeachers(schoolId, teacherId, pageable));
    }

    @GetMapping("/search/{schoolId}")
    ResponseEntity<List<TeacherDTO>> getSearchedTeachers(@PathVariable String schoolId, @RequestParam String q) {
        return ResponseEntity.ok(teacherService.findAllTeachers(schoolId, q));
    }

    @GetMapping("/search-one/{schoolId}")
    ResponseEntity<TeacherDTO> getSearchedTeacher(@PathVariable String schoolId, @RequestParam String q) {
        return ResponseEntity.ok(teacherService.fetchOneByCustomColumn(q, schoolId));
    }

    @GetMapping("/basic/{classeId}")
    ResponseEntity<?> getTeachersBasicValues(@PathVariable int classeId, @RequestParam Section section) {
        return ResponseEntity.ok(teacherService.findAllTeacherBasicValue(classeId, section));
    }

    @GetMapping("/basic-one/{teacherId}")
    ResponseEntity<?> getTeacherBasicValues(@PathVariable long teacherId, @RequestParam int classe) {
        return ResponseEntity.ok(teacherService.findTeacherBasicValue(teacherId, classe));
    }

    @GetMapping("/classe/{teacherId}/{schoolId}")
    ResponseEntity<?> getTeacherClasses(@PathVariable String teacherId, @PathVariable String schoolId) {
        return ResponseEntity.ok(teacherService.fetchOneByCustomColumn(teacherId, schoolId, false));
    }

    @GetMapping("/course/{teacherId}/{schoolId}")
    ResponseEntity<?> getTeacherCourses(@PathVariable String teacherId, @PathVariable String schoolId) {
        return ResponseEntity.ok(teacherService.fetchOneByCustomColumn(teacherId, schoolId, true));
    }

    @GetMapping("/count/{schoolId}")
    ResponseEntity<?> countTeachers(@PathVariable String schoolId) {
        return ResponseEntity.ok(teacherService.countAllTeachers(schoolId));
    }

    @GetMapping("/personal/{teacherId}")
    ResponseEntity<?> getTeacherPersonalInfo (@PathVariable String teacherId) {
        return ResponseEntity.ok(teacherService.fetchTeacherPersonalInfo(teacherId));
    }

    @GetMapping("/{id}/count_student/{schoolId}")
    ResponseEntity<Map<String, Long>> getTeacherStudentCounts(@PathVariable String id, @PathVariable String schoolId) {
        return ResponseEntity.ok(teacherService.count(UUID.fromString(id), UUID.fromString(schoolId)));
    }

    @GetMapping("/count_by_classe/{teacherId}/{schoolId}")
    ResponseEntity<?> countStudentsByClasse(@PathVariable String teacherId, @PathVariable String schoolId) {
        return ResponseEntity.ok(teacherService.countStudentsByClasse(UUID.fromString(teacherId), UUID.fromString(schoolId)));
    }

    @GetMapping("/widgets/{teacherId}")
    ResponseEntity<?> getTeacherWidget(@PathVariable String teacherId, @RequestParam String academicYear) {
        return ResponseEntity.ok(teacherService.fetchTeacherWidgets(teacherId, academicYear));
    }

    @PatchMapping("/classes/{teacherId}/{schoolId}")
    ResponseEntity<?> updateClasses(@PathVariable String teacherId, @PathVariable String schoolId, @Valid @RequestBody TeacherClassUpdateRequest request) {
        try {
            int updated = teacherService.updateTeacherClasses(teacherId, schoolId, request.operationType(), request.classIds());
            if (updated > 0) {
                return ResponseEntity.ok("Modification classe effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found or update failed");
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/courses/{teacherId}/{schoolId}")
    ResponseEntity<?> updateCourses(@PathVariable String teacherId, @PathVariable String schoolId, @Valid @RequestBody TeacherCourseUpdateRequest request) {
        try {
            int updated = teacherService.updateTeacherCourses(teacherId, schoolId, request.operationType(), request.courseIds());
            if (updated > 0) {
                return ResponseEntity.ok("Modification course effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found or update failed");
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
