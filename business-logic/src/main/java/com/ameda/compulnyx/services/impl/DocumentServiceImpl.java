package com.ameda.compulnyx.services.impl;

import com.ameda.compulnyx.dtos.responses.StudentRecordsResponse;
import com.ameda.compulnyx.entities.User;
import com.ameda.compulnyx.repository.UserRepository;
import com.ameda.compulnyx.services.DocumentService;
import com.ameda.compulnyx.services.UserService;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * Service to generate Excel document from database records.
 */
@Slf4j
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final UserService userService;
    private final UserRepository userRepository;

    public DocumentServiceImpl(UserService userService,
                               UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public File generateStudentExcel(int count) {
        try {
            List<StudentRecordsResponse> allStudents = userService.allUsers();

            if (count > allStudents.size()) {
                count = allStudents.size();
            }

            List<StudentRecordsResponse> selectedStudents = allStudents.subList(0, count);

            String os = System.getProperty("os.name").toLowerCase();
            String dirPath = os.contains("win")
                    ? "C:\\var\\log\\applications\\API\\dataprocessing"
                    : "/var/log/applications/API/dataprocessing";

            File dir = new File(dirPath);
            if (!dir.exists()) dir.mkdirs();

            File file = new File(dir, "students_" + count + "_records.xlsx");

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Students");

                Row header = sheet.createRow(0);
                String[] columns = {"studentId", "firstName", "lastName", "DOB", "class", "score", "status", "photoPath"};
                for (int i = 0; i < columns.length; i++) {
                    header.createCell(i).setCellValue(columns[i]);
                }

                int rowIndex = 1;
                for (StudentRecordsResponse student : selectedStudents) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(student.getStudentId());
                    row.createCell(1).setCellValue(student.getFirstName());
                    row.createCell(2).setCellValue(student.getLastName());
                    row.createCell(3).setCellValue(student.getDob().toString());
                    row.createCell(4).setCellValue(student.getClassName());
                    row.createCell(5).setCellValue(student.getScore());
                    row.createCell(6).setCellValue(student.getStatus());
                    row.createCell(7).setCellValue(student.getPhotoPath());
                }

                try (FileOutputStream out = new FileOutputStream(file)) {
                    workbook.write(out);
                }
            }
            log.info("generate service impl layer");
            return file;

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel file", e);
        }
    }

    @Override
    public File processExcelToCsv() {
        String os = System.getProperty("os.name").toLowerCase();
        String dirPath = os.contains("win")
                ? "C:\\var\\log\\applications\\API\\dataprocessing"
                : "/var/log/applications/API/dataprocessing";

        File excelFile = new File(dirPath, "students_*.xlsx");
        File latestExcelFile = getLatestExcelFile(dirPath);

        if (latestExcelFile == null) {
            throw new RuntimeException("No Excel file found for processing.");
        }

        File csvFile = new File(dirPath, latestExcelFile.getName().replace(".xlsx", ".csv"));

        try (
                Workbook workbook = new XSSFWorkbook(new FileInputStream(latestExcelFile));
                FileWriter writer = new FileWriter(csvFile);
                CSVWriter csvWriter = new CSVWriter(writer)
        ) {
            Sheet sheet = workbook.getSheetAt(0);
            boolean firstRow = true;

            for (Row row : sheet) {
                List<String> data = new ArrayList<>();

                for (int i = 0; i < 8; i++) {
                    if (row.getCell(i) == null) {
                        data.add("");
                        continue;
                    }

                    if (!firstRow && i == 5) { // score column
                        double score = row.getCell(i).getNumericCellValue() + 10;
                        data.add(String.valueOf(score));
                    } else {
                        data.add(row.getCell(i).toString());
                    }
                }

                csvWriter.writeNext(data.toArray(new String[0]));
                firstRow = false;
            }

            return csvFile;
        } catch (IOException e) {
            throw new RuntimeException("Failed to process Excel to CSV", e);
        }
    }

    @Override
    public void uploadExcelAndSaveToDb(MultipartFile file) {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                Long studentId = (long) row.getCell(0).getNumericCellValue();

                Optional<User> existingStudentOpt = userRepository.findById(studentId);
                if (existingStudentOpt.isPresent()) {
                    User student = existingStudentOpt.get();
                    int excelScore = (int) row.getCell(5).getNumericCellValue();
                    int updatedScore = excelScore + 5;

                    student.getStudent().setScore(updatedScore);
                    userRepository.save(student);
                } else {
                    System.out.println("Student ID " + studentId + " not found.");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload and update Excel file", e);
        }
    }


    private File getLatestExcelFile(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles((dir1, name) -> name.endsWith(".xlsx"));

        if (files == null || files.length == 0) return null;

        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        return files[0];
    }


}
