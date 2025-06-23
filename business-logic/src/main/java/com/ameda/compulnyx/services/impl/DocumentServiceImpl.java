package com.ameda.compulnyx.services.impl;

import com.ameda.compulnyx.services.DocumentService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * Service to generate Excel document with random student records.
 * Author: kev.Ameda
 */
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    @Override
    public File generateStudentExcel(int count) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            String dirPath = os.contains("win")
                    ? "C:\\var\\log\\applications\\API\\dataprocessing"
                    : "/var/log/applications/API/dataprocessing";

            File dir = new File(dirPath);
            if (!dir.exists()) dir.mkdirs();

            File file = new File(dir, "students_" + count + "_records.xlsx");

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Students");
                // Header row
                Row header = sheet.createRow(0);
                String[] columns = {"studentId", "firstName", "lastName", "DOB", "class", "score", "status", "photoPath"};
                for (int i = 0; i < columns.length; i++) {
                    header.createCell(i).setCellValue(columns[i]);
                }

                Random rand = new Random();
                String[] classNames = {"Class1", "Class2", "Class3", "Class4", "Class5"};

                for (int i = 0; i < count; i++) {
                    Row row = sheet.createRow(i + 1);
                    row.createCell(0).setCellValue(i + 1); // studentId
                    row.createCell(1).setCellValue(randomString(rand, 3, 8)); // firstName
                    row.createCell(2).setCellValue(randomString(rand, 3, 8)); // lastName
                    row.createCell(3).setCellValue(randomDate(rand, "2000-01-01", "2010-12-31")); // DOB
                    row.createCell(4).setCellValue(classNames[rand.nextInt(classNames.length)]); // class
                    row.createCell(5).setCellValue(rand.nextInt(31) + 55); // score (55-85)
                    row.createCell(6).setCellValue(1); // status (active)
                    row.createCell(7).setCellValue(""); // photoPath
                }

                try (FileOutputStream out = new FileOutputStream(file)) {
                    workbook.write(out);
                }
            }

            return file;

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel file", e);
        }
    }

    private String randomString(Random rand, int minLen, int maxLen) {
        int len = rand.nextInt(maxLen - minLen + 1) + minLen;
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = (char) ('a' + rand.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }

    private String randomDate(Random rand, String start, String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(start, formatter);
        LocalDate endDate = LocalDate.parse(end, formatter);
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return startDate.plusDays(rand.nextInt((int) days + 1)).toString();
    }
}
