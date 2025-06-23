package com.ameda.compulnyx.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Author: kev.Ameda
 */

public interface DocumentService {

    public File generateStudentExcel(int count);
    public File processExcelToCsv();
    public void uploadExcelAndSaveToDb(MultipartFile file);
}
