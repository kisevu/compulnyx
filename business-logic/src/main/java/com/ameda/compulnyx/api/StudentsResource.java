package com.ameda.compulnyx.api;

import com.ameda.compulnyx.dtos.responses.StudentRecordsResponse;
import com.ameda.compulnyx.entities.User;
import com.ameda.compulnyx.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Author: kev.Ameda
 */

@RestController
@RequestMapping("/students")
@SecurityRequirement(name = "bearerAuth")
public class StudentsResource {

    private static Logger log = LoggerFactory.getLogger(StudentsResource.class);
    private final UserService userService;

    public StudentsResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all-students")
    public ResponseEntity<?> getAllStudents(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.allUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getStudentById(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateStudent(
            @PathVariable Long id,
            @RequestPart("student") StudentRecordsResponse updatedStudent,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
        return userService.updateStudent(id, updatedStudent, photo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        return userService.softDeleteStudent(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
    @GetMapping("/report")
    public List<StudentRecordsResponse> getFilteredReport(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDob,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDob
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getFilteredStudents(studentId, className, startDob, endDob, pageable);
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportReportToExcel(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDob,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDob
    ) throws IOException {
        File file = userService.generateFilteredExcel(studentId, className, startDob, endDob);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(resource);
    }


}
