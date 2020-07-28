package com.logex.demo.service;


import com.logex.demo.config.UtilService;
import com.logex.demo.config.exception.BadRequestException;
import com.logex.demo.config.exception.ServiceException;
import com.logex.demo.dto.FileUploadDto;
import com.logex.demo.dto.PayrollDto;
import com.logex.demo.enums.EmployeeType;
import com.logex.demo.enums.ReportType;
import com.logex.demo.model.Employee;
import com.logex.demo.model.PayRoll;
import com.logex.demo.repository.PayrollRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private FoodPaymentService foodPaymentService;

    @Value("${payroll.file.path}")
    private String fileUrl;

    private static final Double LIFE_INSURANCE = 200.0;
    private static final Double COMPANY_EOBI = 650.0;
    private static final Double EMPLOYEE_EOBI = 130.0;
    private static final Double SESSI = 1050.0;
    private static final Double EDU_CESS = 8.0;
    private static final Double FOOD_ALLOWANCE_PER_DAY = 400.0;

    private static final String[] columnName = {"Emp ID", "Employee name", "CNIC", "Trip Incentive", "Deduction", "EID Ex-Gratia", "Total Days"};

    private static final String[] INTERNAL_REPORT_COLUMN = {"Emp ID", "Employee name", "CNIC","Designation","Date of joining","Remarks","Basic Salary", "Total Days","Trip Incentive","Food Allow.","Outstanding Amount","OT hours","EID Ex-Gratia", "Deduction","Emp EOBI","Gross Salary"};

    private static final String[] EXTERNAL_REPORT_COLUMN = {"Emp ID", "Employee name", "CNIC","Designation","Date of joining","Remarks","Basic Salary", "Total Days","Trip Incentive","Food Allow.","Outstanding Amount","OT hours","EID Ex-Gratia", "Deduction","Life Insurance","Company EOBI","SESSI","Edu. Cess","Gross Salary"};

    public List<PayRoll> findAllByEmployee(Long id) {
        return payrollRepository.findAllByEmployee_IdAndIsDeletedFalse(id);
    }

    public List<PayRoll> findAllByEmployeeType(EmployeeType employeeType) {
        return payrollRepository.findByEmployee_EmployeeTypeAndIsDeletedFalse(employeeType);
    }

    public PayRoll findOne(Long id) {
        return payrollRepository.findByIdAndIsDeletedFalse(id);
    }

    private PayRoll findByEmpIdAndMonth(Long id, String month) {
        return payrollRepository.findByMonthYearAndEmployee_IdAndIsDeletedFalse(month, id);
    }

    public List<PayRoll> findAll() {
        return payrollRepository.findAllByIsDeletedFalse();
    }

    public void delete(Long id) {
        PayRoll payroll=findOne(id);
        if(payroll!=null){
            payroll.setIsDeleted(Boolean.TRUE);
            payrollRepository.save(payroll);
        }
    }

    public ByteArrayInputStream exportExcel() {
        try {
            List<Employee> employeeList = employeeService.findAllActiveReserveDrivers();
            employeeList.addAll(employeeService.findAllActiveSupervisor());
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Sheet sheet = UtilService.initializeExcel(workbook,columnName,"Payroll");

            // Create Other rows and cells with contacts data
            int rowCount = 1;
            for (Employee emp : employeeList) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;

                row.createCell(columnCount++).setCellValue(emp.getEmpId());
                row.createCell(columnCount++).setCellValue(emp.getFirstName() + " "+ emp.getLastName());
                row.createCell(columnCount++).setCellValue(emp.getCnic());
                row.createCell(columnCount++).setCellValue(0.0);
                row.createCell(columnCount++).setCellValue(0.0);
                row.createCell(columnCount++).setCellValue(0.0);
                row.createCell(columnCount++).setCellValue(0);

            }

            for (int i = 0; i < columnName.length; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }

    public void uploadFile(FileUploadDto fileUploadDto) {
        try {
            File uploadedFile = uploadPayrollForm(fileUploadDto.getFile(),fileUploadDto.getFile().getOriginalFilename());
            Workbook workbook = WorkbookFactory.create(uploadedFile);
            Sheet sheet=workbook.getSheetAt(0);

            DataFormatter formatter = new DataFormatter();
            sheet.removeRow(sheet.getRow(0));
            sheet.forEach(row -> {
                if(!UtilService.isEmptyRow(row)) {
                    PayRoll payRoll = new PayRoll();
                    Integer columnCount = 0;
                    payRoll.setEmployee(employeeService.findByEmpId(formatter.formatCellValue(row.getCell(columnCount++))));
                    columnCount += columnCount + 1;
                    payRoll.setTripIncentive(UtilService.isValidDouble(formatter.formatCellValue(row.getCell(columnCount++))));
                    payRoll.setDeduction(UtilService.isValidDouble(formatter.formatCellValue(row.getCell(columnCount++))));
                    payRoll.setEidExGratia(UtilService.isValidDouble(formatter.formatCellValue(row.getCell(columnCount++))));
                    payRoll.setTotalDays(UtilService.isValidInteger(formatter.formatCellValue(row.getCell(columnCount++))));
                    payRoll.setMonthYear(fileUploadDto.getMonthYear());
                    addPayRollValues(payRoll);
                    save(payRoll);
                }
            });
            deletePayrollForm(fileUploadDto.getFile().getOriginalFilename());
        }
        catch (IOException e){
            throw new BadRequestException(e.getMessage());
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(),e);
        }

    }

    private void addPayRollValues(PayRoll payRoll) {
        payRoll.setLifeInsurance(LIFE_INSURANCE);
        payRoll.setEmployeeEobi(EMPLOYEE_EOBI);
        payRoll.setEduCess(EDU_CESS);
        payRoll.setSessi(SESSI);
        payRoll.setCompanyEobi(COMPANY_EOBI);
        payRoll.setFoodAllowanceAmount(payRoll.getEmployee().getEmployeeType().equals(EmployeeType.DRIVER) ? payRoll.getTotalDays() *FOOD_ALLOWANCE_PER_DAY : 0.0);
        payRoll.setBasicSalary(payRoll.getEmployee().getSalary());
        payRoll.setMedicalInsurance(0.0);
        payRoll.setGratuityFund(0.0);
        payRoll.setTravelAllowance(0.0);
        payRoll.setForms(0.0);
        payRoll.setBonusPayable(0.0);
        payRoll.setOtherCharges(0.0);
        payRoll.setOtherAllowance(0.0);
        payRoll.setOverTimeHours(0);
    }

    public File uploadPayrollForm(MultipartFile file, String fileName){
        String fullFileName = fileName;
        File isFileUploaded = UtilService.uploadFile(file, fileUrl, fullFileName);
        if(isFileUploaded == null) throw new BadRequestException("FILE NULL");
        return isFileUploaded;
    }

    private void deletePayrollForm(String fileName){
        String fullFileName = fileName;
        UtilService.deleteFileIfExist(fileUrl, fullFileName);
    }

    public void save(PayRoll payRoll){
        if (findByEmpIdAndMonth(payRoll.getEmployee().getId(), payRoll.getMonthYear()) == null) {
                payrollRepository.save(payRoll);
        }
    }

    public ByteArrayInputStream exportReport(HttpServletRequest request, HttpServletResponse response) {
        String monthYear = request.getParameter("monthYear");
        String reportType = request.getParameter("reportType");
        List<PayRoll> payRollList = payrollRepository.findByMonthYearAndIsDeletedFalse(monthYear);
        List<PayrollDto> payrollDtoList = new ArrayList<>();
        for(PayRoll payRoll:payRollList){
            PayrollDto payrollDto = new PayrollDto();
            BeanUtils.copyProperties(payRoll,payrollDto);
            calculateGrossSalary(payrollDto,reportType);
           payrollDtoList.add(payrollDto);
        }
        return generateExcel(payrollDtoList,reportType);

    }
    private void calculateGrossSalary(PayrollDto payrollDto, String reportType) {
        Double totalFoodPayment = foodPaymentService.getTotalAmountByEmpAndMonth(payrollDto.getEmployee().getId(),payrollDto.getMonthYear());
        Double actualFoodPayment = payrollDto.getFoodAllowanceAmount() - totalFoodPayment;

        Double totalBasicSalary = (payrollDto.getBasicSalary()/UtilService.getTotalDaysInMonth(Integer.parseInt(payrollDto.getMonthYear().split("-")[0]),Integer.parseInt(payrollDto.getMonthYear().split("-")[1])))*payrollDto.getTotalDays();
        if(reportType.equals(ReportType.INTERNAL_REPORT.toString())){
            Double grossSalary=totalBasicSalary + payrollDto.getEidExGratia() + payrollDto.getFoodAllowanceAmount()+ payrollDto.getTripIncentive() - payrollDto.getEmployeeEobi() - payrollDto.getDeduction() - payrollDto.getForms();
            grossSalary+=actualFoodPayment;
            payrollDto.setGrossSalary(Math.ceil(grossSalary));
        }
        else{
            Double grossSalary=totalBasicSalary + payrollDto.getEidExGratia() + payrollDto.getFoodAllowanceAmount() + payrollDto.getTripIncentive()+payrollDto.getCompanyEobi()+payrollDto.getEduCess() + payrollDto.getBonusPayable()+ payrollDto.getSessi() + payrollDto.getLifeInsurance() - payrollDto.getDeduction();
            grossSalary+=actualFoodPayment;
            payrollDto.setGrossSalary(Math.ceil(grossSalary));
        }
    }

    private ByteArrayInputStream generateExcel(List<PayrollDto> payrollDtoList, String reportType) {
        try{
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            String[] columnName = reportType.equals(ReportType.INTERNAL_REPORT.toString()) ? INTERNAL_REPORT_COLUMN : EXTERNAL_REPORT_COLUMN;
            Sheet sheet = UtilService.initializeExcel(workbook,columnName,reportType);

            // Create Other rows and cells with contacts data
            int rowCount = 1;
            for (PayrollDto payrollDto : payrollDtoList) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;

                row.createCell(columnCount++).setCellValue(payrollDto.getEmployee().getEmpId());
                row.createCell(columnCount++).setCellValue(payrollDto.getEmployee().getFirstName() + " "+ payrollDto.getEmployee().getLastName());
                row.createCell(columnCount++).setCellValue(payrollDto.getEmployee().getCnic());
                row.createCell(columnCount++).setCellValue(payrollDto.getEmployee().getEmployeeType().getTitle());
                row.createCell(columnCount++).setCellValue(payrollDto.getEmployee().getJoiningDate());
                row.createCell(columnCount++).setCellValue("");
                row.createCell(columnCount++).setCellValue(payrollDto.getBasicSalary());
                row.createCell(columnCount++).setCellValue(payrollDto.getTotalDays());
                row.createCell(columnCount++).setCellValue(payrollDto.getTripIncentive());
                row.createCell(columnCount++).setCellValue(payrollDto.getFoodAllowanceAmount());
                row.createCell(columnCount++).setCellValue(0.0);
                row.createCell(columnCount++).setCellValue(payrollDto.getOverTimeHours());
                row.createCell(columnCount++).setCellValue(payrollDto.getEidExGratia());
                row.createCell(columnCount++).setCellValue(payrollDto.getDeduction());
                if(reportType.equals(ReportType.INTERNAL_REPORT.toString())){
                    row.createCell(columnCount++).setCellValue(payrollDto.getEmployeeEobi());
                }
                else{
                    row.createCell(columnCount++).setCellValue(payrollDto.getLifeInsurance());
                    row.createCell(columnCount++).setCellValue(payrollDto.getCompanyEobi());
                    row.createCell(columnCount++).setCellValue(payrollDto.getSessi());
                    row.createCell(columnCount++).setCellValue(payrollDto.getEduCess());
                }
                row.createCell(columnCount++).setCellValue(payrollDto.getGrossSalary());

            }

            for (int i = 0; i < columnName.length; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }


    public List<PayRoll> searchPayroll(HttpServletRequest request) {

        String monthYear = request.getParameter("monthYear");
        if(monthYear!=null && !monthYear.isEmpty()){
            return payrollRepository.findByMonthYearAndIsDeletedFalse(monthYear);
        }
        return null;
    }
}
