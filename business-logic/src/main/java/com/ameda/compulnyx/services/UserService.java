package com.ameda.compulnyx.services;

import com.ameda.compulnyx.dtos.responses.StudentRecordsResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Author: kev.Ameda
 */
public interface UserService {

    List<StudentRecordsResponse> allUsers();

    StudentRecordsResponse getStudentById(Long id);

    ResponseEntity<String> updateStudent(Long id, StudentRecordsResponse updatedStudent, MultipartFile photo);
    boolean softDeleteStudent(Long id);

    List<StudentRecordsResponse> getFilteredStudents(Long studentId, String className, LocalDate startDob, LocalDate endDob, Pageable pageable);

    File generateFilteredExcel(Long studentId, String className, LocalDate startDob, LocalDate endDob);
}
