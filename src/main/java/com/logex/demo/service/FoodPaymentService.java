package com.logex.demo.service;

import com.logex.demo.config.Queries;
import com.logex.demo.config.UtilService;
import com.logex.demo.config.exception.ServiceException;
import com.logex.demo.dto.FoodPaymentDto;
import com.logex.demo.model.Employee;
import com.logex.demo.model.FoodPaymentLog;
import com.logex.demo.repository.FoodPaymentRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class FoodPaymentService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private FoodPaymentRepository foodPaymentRepository;

    @PersistenceContext
    public EntityManager em;

    String[] columnName = { "First Name", "Last Name", "Cnic","Phone Num","Job Status","From Date","To Date",
            "Total Days","Amount","Total Amount"};


    public void delete(Long id) {

    }

    public void save(FoodPaymentDto foodPaymentDto) {
        try {
            FoodPaymentLog exitfoodPaymentLog = new FoodPaymentLog();
            if (foodPaymentDto != null) {
                if (foodPaymentDto.getId() != null) {
                    exitfoodPaymentLog = findOne(foodPaymentDto.getId());

                }
                if (exitfoodPaymentLog != null) {
                    for (Long employee : foodPaymentDto.getEmployees()) {
                        FoodPaymentLog foodPaymentLog = new FoodPaymentLog();
                        foodPaymentLog.setId(exitfoodPaymentLog.getId());
                        foodPaymentLog.setFromDate(foodPaymentDto.getFromDate());
                        foodPaymentLog.setToDate(foodPaymentDto.getToDate());
                        foodPaymentLog.setAmount(foodPaymentDto.getAmount());
                        foodPaymentLog.setTotalDays(foodPaymentDto.getTotalDays());
                        foodPaymentLog.setTotalAmount(foodPaymentDto.getAmount() * Integer.parseInt(foodPaymentDto.getTotalDays()));
                        foodPaymentLog.setEmployee(employeeService.findById(employee));
                        String id = checkIfPeriodExist(foodPaymentLog.getFromDate(), foodPaymentLog.getToDate(), foodPaymentLog.getEmployee().getId());
                        Boolean exist = Boolean.TRUE;
                        if (id != null)
                            exist = foodPaymentLog.getId() != null ? foodPaymentLog.getId().equals(Long.parseLong(id)) : Boolean.FALSE;
                        if (exist)
                            foodPaymentRepository.save(foodPaymentLog);
                    }
                }
            }
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    public FoodPaymentLog findOne(Long id){
        return foodPaymentRepository.findByIdAndIsDeletedFalse(id);
    }

    public List<FoodPaymentLog> findAll(){
        return foodPaymentRepository.findAllByIsDeletedFalse();
    }

    private String checkIfPeriodExist(String fromDate, String toDate, Long empId){
        try {
            StringBuilder criteria = new StringBuilder();
            int parameterNo = 1;
            Map<Integer, Object> parameters = new LinkedHashMap<>();

            if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                criteria.append(" AND STR_TO_DATE(a.from_date,'%d-%m-%Y') >= STR_TO_DATE( ? ,'%d-%m-%Y')");
                parameters.put(parameterNo, fromDate);
                parameterNo++;
                criteria.append(" AND STR_TO_DATE(a.to_date,'%d-%m-%Y') <= STR_TO_DATE( ? ,'%d-%m-%Y')");
                parameters.put(parameterNo, toDate);
                parameterNo++;
            }
            if (empId != null) {
                criteria.append("AND b.id = ?");
                parameters.put(parameterNo, empId);
                parameterNo++;
            }

            Query query = em.createNativeQuery(Queries.periodExistOfEmployee.replace("criteria", criteria.toString()));
            List<Object> results = UtilService.toParameterized(query, parameters, parameterNo).getResultList();
            String id = results.size() > 0 && results.get(0) != null ? results.get(0).toString() : null;
            return id;
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    public Double getTotalAmountByEmpAndMonth(Long id, String monthYear) {
        try {
            Integer month = Integer.parseInt(monthYear.split("-")[0]);
            Integer year = Integer.parseInt(monthYear.split("-")[1]);
            String fromDate = UtilService.formatDate(1, month, year);
            String toDate = UtilService.getEndDate(month, year);
            StringBuilder criteria = new StringBuilder();
            int parameterNo = 1;
            Map<Integer, Object> parameters = new LinkedHashMap<>();

            if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                criteria.append(" AND STR_TO_DATE(a.from_date,'%d-%m-%Y') >= STR_TO_DATE( ? ,'%d-%m-%Y')");
                parameters.put(parameterNo, fromDate);
                parameterNo++;
                criteria.append(" AND STR_TO_DATE(a.to_date,'%d-%m-%Y') <= STR_TO_DATE( ? ,'%d-%m-%Y')");
                parameters.put(parameterNo, toDate);
                parameterNo++;
            }
            if (id != null) {
                criteria.append("AND b.id = ?");
                parameters.put(parameterNo, id);
                parameterNo++;

            }

            Query query = em.createNativeQuery(Queries.totalAmountOfEmpByMonth.replace("criteria", criteria.toString()));
            List<Object> results = UtilService.toParameterized(query, parameters, parameterNo).getResultList();

            Double totalAmount = UtilService.isValidDouble(results.get(0) != null ? results.get(0).toString() : "0");
            return totalAmount;
        }
        catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public FoodPaymentDto getDto(FoodPaymentLog foodPaymentLog) {
        FoodPaymentDto foodPaymentDto = new FoodPaymentDto();
        foodPaymentDto.addEmployee(foodPaymentLog.getEmployee().getId());
        foodPaymentDto.setAmount(foodPaymentLog.getAmount());
        foodPaymentDto.setFromDate(foodPaymentLog.getFromDate());
        foodPaymentDto.setToDate(foodPaymentLog.getToDate());
        foodPaymentDto.setId(foodPaymentLog.getId());
        foodPaymentDto.setTotalAmount(foodPaymentLog.getTotalAmount());
        foodPaymentDto.setTotalDays(foodPaymentLog.getTotalDays());
        return foodPaymentDto;
    }

    public ByteArrayInputStream exportReport(HttpServletRequest request, HttpServletResponse response) {

        List<FoodPaymentDto> data = searchFoodPayment(request);
        return exportExcel(data,"Employee");
    }


    public ByteArrayInputStream exportExcel(List<FoodPaymentDto> data, String employee) {
        try {
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Sheet sheet = UtilService.initializeExcel(workbook,columnName,"FoodPayment");

            // Create Other rows and cells with contacts data
            int rowCount = 1;
            for (FoodPaymentDto foodPaymentDto : data) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;

                row.createCell(columnCount++).setCellValue(foodPaymentDto.getEmployee().getFirstName());
                row.createCell(columnCount++).setCellValue(foodPaymentDto.getEmployee().getLastName());
                row.createCell(columnCount++).setCellValue(foodPaymentDto.getEmployee().getCnic());
                row.createCell(columnCount++).setCellValue(foodPaymentDto.getEmployee().getPhoneNumber());
                row.createCell(columnCount++).setCellValue(foodPaymentDto.getEmployee().getJobStatus().getTitle());
                row.createCell(columnCount++).setCellValue(foodPaymentDto.getFromDate());
                row.createCell(columnCount++).setCellValue(foodPaymentDto.getToDate());
                row.createCell(columnCount++).setCellValue(foodPaymentDto.getTotalDays());
                row.createCell(columnCount++).setCellValue(foodPaymentDto.getAmount());
                row.createCell(columnCount++).setCellValue(foodPaymentDto.getTotalAmount());

            }

//            for (int i = 0; i < columnName.length; i++) {
//
//            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }

    public List<FoodPaymentDto> searchFoodPayment(HttpServletRequest request) {
        StringBuilder criteria = new StringBuilder();
        int parameterNo = 1;
        Map<Integer,Object> parameters = new LinkedHashMap<>();

        if(request.getParameter("fromDate") != null && !request.getParameter("fromDate").toString().isEmpty()) {
            criteria.append(" AND STR_TO_DATE(a.from_date,'%d-%m-%Y') >= STR_TO_DATE( ? ,'%d-%m-%Y')");
            parameters.put(parameterNo, request.getParameter("fromDate"));
            parameterNo++;
        }
        if(request.getParameter("toDate") != null && !request.getParameter("toDate").toString().isEmpty()){
            criteria.append(" AND STR_TO_DATE(a.to_date,'%d-%m-%Y') <= STR_TO_DATE( ? ,'%d-%m-%Y')");
            parameters.put(parameterNo,request.getParameter("toDate"));
            parameterNo++;
        }

        Query query = em.createNativeQuery(Queries.foodPaymentFilter.replace("criteria", criteria.toString()));
        List<Object> results = UtilService.toParameterized(query,parameters,parameterNo).getResultList();
        List<FoodPaymentDto> data = new ArrayList<>();

        for(Object object: results){
            int i = 0;
            Object[] obj = (Object[])object;
            FoodPaymentDto foodPaymentDto = new FoodPaymentDto();
            foodPaymentDto.setFromDate(UtilService.isValid(obj[i++]));
            foodPaymentDto.setToDate(UtilService.isValid(obj[i++]));
            foodPaymentDto.setTotalDays(UtilService.isValid(obj[i++]));
            foodPaymentDto.setTotalAmount((Double)obj[i++]);
            foodPaymentDto.setAmount((Double) obj[i++]);
            Employee employee = new Employee();
            employee.setFirstName(UtilService.isValid(obj[i++]));
            employee.setLastName(UtilService.isValid(obj[i++]));
            employee.setCnic(UtilService.isValid(obj[i++]));
            employee.setPhoneNumber(UtilService.isValid(obj[i++]));
            employee.setJobStatus((Integer)obj[i++]);
            foodPaymentDto.setEmployee(employee);
            data.add(foodPaymentDto);


        }
        return data;
    }
}
