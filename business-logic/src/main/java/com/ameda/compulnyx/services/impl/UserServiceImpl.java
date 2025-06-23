package com.ameda.compulnyx.services.impl;

import com.ameda.compulnyx.dtos.responses.StudentRecordsResponse;
import com.ameda.compulnyx.entities.User;
import com.ameda.compulnyx.repository.UserRepository;
import com.ameda.compulnyx.services.UserService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: kev.Ameda
 */

@Service
@Transactional
public class UserServiceImpl  implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<StudentRecordsResponse> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users.stream()
                .map(this::mapToStudentRecordsResponse)
                .toList();
    }

    @Override
    public boolean softDeleteStudent(Long id) {
        return userRepository.findById(id).map(student -> {
            student.getStudent().setStatus(0);
            userRepository.save(student);
            return true;
        }).orElse(false);
    }

    @Override
    public StudentRecordsResponse getStudentById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return mapToStudentRecordsResponse(byId.get());
    }

    @Override
    public ResponseEntity<String> updateStudent(Long id, StudentRecordsResponse updatedStudent, MultipartFile photo) {
        return userRepository.findById(id).map(student -> {
            student.getStudent().setFirstName(updatedStudent.getFirstName());
            student.getStudent().setLastName(updatedStudent.getLastName());
            student.getStudent().setDob(updatedStudent.getDob());
            student.getStudent().setClassName(updatedStudent.getClassName());
            student.getStudent().setScore(updatedStudent.getScore());

            if (photo != null && !photo.isEmpty()) {
                if (!List.of("image/png", "image/jpeg").contains(photo.getContentType())) {
                    return ResponseEntity.badRequest().body("Invalid file type");
                }

                if (photo.getSize() > (5 * 1024 * 1024)) {
                    return ResponseEntity.badRequest().body("File too large (max 5MB)");
                }

                try {
                    String ext = getExtension(photo.getOriginalFilename());
                    String filename = id + "-" + UUID.randomUUID() + ext;

                    String photoDir = System.getProperty("os.name").toLowerCase().contains("win")
                            ? "C:\\var\\log\\applications\\API\\StudentPhotos"
                            : "/var/log/applications/API/StudentPhotos";

                    File dir = new File(photoDir);
                    if (!dir.exists()) dir.mkdirs();

                    File dest = new File(dir, filename);
                    photo.transferTo(dest);

                    student.getStudent().setPhotoPath(dest.getAbsolutePath());
                } catch (IOException e) {
                    return ResponseEntity.internalServerError().body("Failed to save photo");
                }
            }

            userRepository.save(student);
            return ResponseEntity.ok("Student updated successfully");
        }).orElse(ResponseEntity.notFound().build());
    }

    private String getExtension(String fileName) {
        return fileName != null && fileName.contains(".")
                ? fileName.substring(fileName.lastIndexOf('.'))
                : "";
    }


    @Override
    public List<StudentRecordsResponse> getFilteredStudents(Long studentId, String className, LocalDate startDob, LocalDate endDob, Pageable pageable) {
        List<User> filtered = userRepository.findAll();
        for (User s : filtered) {
            if (studentId != null && !s.getStudentId().equals(studentId)) continue;
            if (className != null && !s.getStudent().getClassName().equalsIgnoreCase(className)) continue;
            if (startDob != null && s.getStudent().getDob().isBefore(startDob)) continue;
            if (endDob != null && s.getStudent().getDob().isAfter(endDob)) continue;

            filtered.add(s);
        }
        filtered.sort(Comparator.comparing(User::getStudentId));
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filtered.size());
        List<User> pagedStudents = (start <= end) ? filtered.subList(start, end) : new ArrayList<>();
        List<StudentRecordsResponse> responseList = new ArrayList<>();
        for (User s : pagedStudents) {
            responseList.add(new StudentRecordsResponse(
                    s.getStudentId(),
                    s.getStudent().getFirstName(),
                    s.getStudent().getLastName(),
                    s.getStudent().getDob(),
                    s.getStudent().getClassName(),
                    s.getStudent().getScore(),
                    s.getStudent().getStatus(),
                    s.getStudent().getPhotoPath()
            ));
        }
        return responseList;
    }

    @Override
    public File generateFilteredExcel(Long studentId, String className, LocalDate startDob, LocalDate endDob) {
        List<User> students = userRepository.findAll().stream()
                .filter(s -> studentId == null || Objects.equals(s.getStudentId(), studentId))
                .filter(s -> className == null || s.getStudent().getClassName().equalsIgnoreCase(className))
                .filter(s -> startDob == null || !s.getStudent().getDob().isBefore(startDob))
                .filter(s -> endDob == null || !s.getStudent().getDob().isAfter(endDob))
                .collect(Collectors.toList());

        String os = System.getProperty("os.name").toLowerCase();
        String dirPath = os.contains("win")
                ? "C:\\var\\log\\applications\\API\\StudentExports"
                : "/var/log/applications/API/StudentExports";

        File dir = new File(dirPath);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, "filtered_student_report_" + System.currentTimeMillis() + ".xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Filtered Report");

            String[] columns = {"studentId", "firstName", "lastName", "DOB", "class", "score", "status", "photoPath"};

            Row header = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }
            int rowIdx = 1;
            for (User student : students) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(student.getStudentId());
                row.createCell(1).setCellValue(student.getStudent().getFirstName());
                row.createCell(2).setCellValue(student.getStudent().getLastName());
                row.createCell(3).setCellValue(student.getStudent().getDob().toString());
                row.createCell(4).setCellValue(student.getStudent().getClassName());
                row.createCell(5).setCellValue(student.getStudent().getScore());
                row.createCell(6).setCellValue(student.getStudent().getStatus());
                row.createCell(7).setCellValue(student.getStudent().getPhotoPath());
            }

            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
            }

            return file;
        } catch (IOException e) {
            throw new RuntimeException("Error generating filtered Excel report", e);
        }
    }

private StudentRecordsResponse mapToStudentRecordsResponse(User user){
    return StudentRecordsResponse.builder()
            .studentId(user.getStudentId())
            .firstName(user.getStudent().getFirstName())
            .lastName(user.getStudent().getLastName())
            .dob(user.getStudent().getDob())
            .className(user.getStudent().getClassName())
            .score(user.getStudent().getScore())
            .status(user.getStudent().getStatus())
            .photoPath(user.getStudent().getPhotoPath())
            .build();
}
}
