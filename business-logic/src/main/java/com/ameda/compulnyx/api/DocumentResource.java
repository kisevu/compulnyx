package com.ameda.compulnyx.api;

import com.ameda.compulnyx.services.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Author: kev.Ameda
 */
@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
@Slf4j
public class DocumentResource {

    private final DocumentService documentService;

    @GetMapping("/generate")
    public ResponseEntity<Resource> generateExcel(@RequestParam int count) throws IOException {
        File file = documentService.generateStudentExcel(count);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        log.info("generate resource endpoint");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(resource);
    }

    @GetMapping("/process")
    public ResponseEntity<Resource> processExcelToCSV() throws IOException {
        File csvFile = documentService.processExcelToCsv();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(csvFile));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFile.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(csvFile.length())
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }

    @PostMapping("/upload/excel")
    public ResponseEntity<String> uploadStudentExcel(@RequestParam("file") MultipartFile file) {
        documentService.uploadExcelAndSaveToDb(file);
        return ResponseEntity.ok("Upload successful");
    }



}
