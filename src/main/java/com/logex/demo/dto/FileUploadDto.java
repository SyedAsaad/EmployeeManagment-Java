package com.logex.demo.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class FileUploadDto {

    private MultipartFile file;

    private String monthYear;

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }
}
