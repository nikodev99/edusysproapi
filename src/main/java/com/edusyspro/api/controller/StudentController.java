package com.edusyspro.api.controller;

import com.edusyspro.api.dto.StudentDTO;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.model.enums.IndividualType;
import com.edusyspro.api.service.interfaces.GuardianService;
import com.edusyspro.api.service.interfaces.IndividualReferenceService;
import com.edusyspro.api.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final GuardianService guardianService;
    private final IndividualReferenceService individualReferenceService;

    @Autowired
    public StudentController(
            StudentService studentService,
            GuardianService guardianService,
            IndividualReferenceService individualReferenceService
    ) {
        this.studentService = studentService;
        this.guardianService = guardianService;
        this.individualReferenceService = individualReferenceService;
    }

    @GetMapping("/{id}")
    ResponseEntity<StudentDTO> fetchStudentById(@PathVariable String id) {
        return ResponseEntity.ok(studentService.findStudentById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateStudentByField(@PathVariable String id, @RequestBody UpdateField student) {
        try {
            int updated = studentService.updateStudent(student.field(), student.value(), id);
            if (updated > 0) {
                return ResponseEntity.ok("Modification effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("StudentDTO not found or update failed");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/{studentId}")
    @Transactional
    ResponseEntity<?> changeGuardian(@PathVariable String studentId, @RequestBody GuardianEntity guardianPost) {
        String guardianReference = individualReferenceService.generateReference(IndividualType.GUARDIAN);
        GuardianEntity guardian = guardianService.saveOrUpdateGuardian(guardianPost, guardianReference);
        return ResponseEntity.ok(studentService.changeStudentGuardian(studentId, guardian));
    }

    @PatchMapping("/address/{addressId}")
    public ResponseEntity<String> updateStudentAddressByField(@PathVariable long addressId, @RequestBody UpdateField student) {
        try {
            int updated = studentService.updateStudentAddress(student.field(), student.value(), addressId);
            if (updated > 0) {
                return ResponseEntity.ok("Modification effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found or update failed");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/info/{infoId}")
    public ResponseEntity<String> controlUpdateStudentPersonalInfo(@PathVariable long infoId, @RequestBody UpdateField student) {
        try {
            int updated = studentService.updateStudentPersonalInfo(student.field(), student.value(), infoId);
            if (updated > 0) {
                return ResponseEntity.ok("Modification effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Personal info not found or update failed");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/health/{id}")
    public ResponseEntity<String> updateStudentHealthByField(@PathVariable String id, @RequestBody UpdateField student) {
        try {
            int updated = studentService.updateStudentHealth(student.field(), student.value(), id);
            System.out.println("Updated: " + updated);
            if (updated > 0) {
                return ResponseEntity.ok("Modification effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Health Condition not found or update failed");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/address/{studentId}")
    ResponseEntity<Address> getStudentAddress(@PathVariable String studentId) {
        return ResponseEntity.ok(studentService.getStudentAddress(studentId));
    }

    @PatchMapping("/guardian/{guardianId}")
    public ResponseEntity<String> updateStudentGuardianByField(@PathVariable String guardianId, @RequestBody UpdateField student) {
        try {
            int updated = studentService.updateStudentGuardian(student.field(), student.value(), guardianId);
            if (updated > 0) {
                return ResponseEntity.ok("Modification effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GuardianDTO not found or update failed");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
