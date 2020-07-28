package com.logex.demo.config;

import com.logex.demo.config.exception.BadRequestException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UtilService {


    public static Boolean isValidFile(MultipartFile file, Long minSize, Long maxSize, List<String> extension) {

        if (file.getContentType() != null && file.getSize() > minSize && file.getSize() <= maxSize) {
            String ext = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
            if (extension.stream().filter(fileExt -> fileExt.equalsIgnoreCase(ext)).findAny().isPresent()) return true;
        }
        return false;
    }

    public static File uploadFile(MultipartFile fileData, String dirPath, String fileName) throws BadRequestException {
        try {

            byte[] btDataFile = fileData.getBytes();
            File file = new File(dirPath);
            if (!file.exists())
                file.mkdir();

            file = new File(dirPath + "/" + fileName);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(btDataFile);
            stream.close();

            return file;
        } catch (Exception e) {
//            logger.error("Error in uploading image. " + e.getMessage());
            return null;
        }
    }

    public static byte[] fetchImageFromDirectory(String filename, String dirPath) {

        try {
            File file = new File(dirPath + filename);
            return Files.readAllBytes(file.toPath());
        } catch (Exception e) {
//            logger.error("Utility.fetchImageFromDirectory: File Not Found On Server");
        }
        return null;
    }

    public static void deleteFileIfExist(String dirPath, String fileName) {
        try {
//            logger.info("Utility.deleteFileIfExist: Started");
            File file = new File(dirPath + fileName);
            Files.deleteIfExists(file.toPath());
        } catch (Exception e) {
//            logger.error("Error in deleting image. " + e.getMessage());
        }
    }

    public static Query toParameterized(Query query, Map<Integer, Object> parameters, int parameterNo) {
        for (int i = 1; i < parameterNo; i++) {
            query.setParameter(i, parameters.get(i));
        }
        return query;
    }

    public static String isValid(Object str) {
        return str == null ? "" : str.toString();
    }

    public static Double isValidDouble(String str) {
        return str == null || str=="" ? 0.0 : Double.parseDouble(str);
    }

    public static Integer isValidInteger(String str){
        return str == null || str=="" ? 0 : Integer.parseInt(str);
    }

    public static void createFolder(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists())
            file.mkdir();
    }


    public static Sheet initializeExcel(Workbook workbook, String[] columnName,String sheetName){

        Sheet sheet = workbook.createSheet(sheetName);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 11);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);


        for (int i = 0; i < columnName.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnName[i]);
            cell.setCellStyle(headerCellStyle);
            sheet.autoSizeColumn(i);
        }
        return sheet;

    }

    public static String getEndDate(Integer month, Integer year){
        Calendar c = Calendar.getInstance();
        c.set(year,month-1,1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(c.getTime());
    }

    public static Integer getTotalDaysInMonth(Integer month, Integer year){
        LocalDate date = LocalDate.of(2019, month, 1);
        return date.lengthOfMonth();
    }

    public static String formatDate(Integer day,Integer month,Integer year){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
        LocalDate localDate =LocalDate.of(year,month,day);
        return localDate.format(formatter);
    }

    public static boolean isEmptyRow(Row row){
        boolean isEmptyRow = true;
        for(int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++){
            Cell cell = row.getCell(cellNum);
            if(cell != null && cell.getCellType() != CellType.BLANK && !cell.toString().isEmpty()){
                isEmptyRow = false;
            }
        }
        return isEmptyRow;
    }


}
