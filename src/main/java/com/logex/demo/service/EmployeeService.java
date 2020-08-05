package com.logex.demo.service;

import com.logex.demo.config.UtilService;
import com.logex.demo.config.exception.DuplicateRecordException;
import com.logex.demo.config.exception.RecordNotFoundException;
import com.logex.demo.config.exception.ServiceException;
import com.logex.demo.dto.EmployeeDto;
import com.logex.demo.enums.*;
import com.logex.demo.model.Employee;
import com.logex.demo.model.Termination;
import com.logex.demo.repository.EmployeeRepository;
import com.logex.demo.repository.TerminationRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private TerminationRepository terminationRepository;

    @PersistenceContext
    public EntityManager em;

    private static String[] columnName = { "First Name", "Last Name","Date Of Joining","Date Of Birth","Cnic","Cnic Expiry Date",
            "License Num","License Class","License Expiry Date","License Location" ,"Salary","City","State","Country","Phone Num",
            "Employee Type","Address","Remarks","Job Status","Verified Type","Is Terminated?"};

    public void save(EmployeeDto employeeDto) {
        try {
            Employee existEmployee = new Employee();
            if (employeeDto != null) {
                if (employeeDto.getId() != null) {
                    existEmployee = findById(employeeDto.getId());
                }
                if (existEmployee != null) {
                    if (employeeDto.getEmployeeType().equals(EmployeeType.DRIVER.ordinal())) {
                        existEmployee.setLicenseClass(employeeDto.getLicenseClass());
                        existEmployee.setVerifiedType(employeeDto.getVerifiedType());
                        existEmployee.setVehicleType(employeeDto.getVehicleType());
                        existEmployee.setLicenseExpiryDate(employeeDto.getLicenseExpiryDate());
                        existEmployee.setLicenseNumber(employeeDto.getLicenseNumber());
                        existEmployee.setLicenseLocation(employeeDto.getLicenseLocation());
                        existEmployee.setJobStatus(employeeDto.getJobStatus());
                    }
                    existEmployee.setEmpId(employeeDto.getEmpId() != null ? employeeDto.getEmpId() : generateEmpId());
                    existEmployee.setFirstName(employeeDto.getFirstName());
                    existEmployee.setLastName(employeeDto.getLastName());
                    existEmployee.setPhoneNumber(employeeDto.getPhoneNumber());
                    existEmployee.setCnic(employeeDto.getCnic());
                    existEmployee.setCnicExpiryDate(employeeDto.getCnicExpiryDate());
                    existEmployee.setAddress(employeeDto.getAddress());
                    existEmployee.setAlternateAddress(employeeDto.getAlternateAddress());
                    existEmployee.setEmployeeType(employeeDto.getEmployeeType());
                    existEmployee.setCountry(employeeDto.getCountry());
                    existEmployee.setState(employeeDto.getState());
                    existEmployee.setCity(employeeDto.getCity());
                    existEmployee.setSalary(employeeDto.getSalary());
                    existEmployee.setDob(employeeDto.getDob());
                    existEmployee.setJobTitle(employeeDto.getJobTitle());
                    existEmployee.setJoiningDate(employeeDto.getJoiningDate());
                    existEmployee.setRemarks(employeeDto.getRemarks());
                    employeeRepository.save(existEmployee);

                }
            }
        }catch (DataIntegrityViolationException e){
            throw new DuplicateRecordException("Error Occured :"+e.getCause().getCause().toString());
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    private String generateEmpId() {
        String orderId;
        Long id= employeeRepository.getHighestId();

        orderId = "EMP-0"+id;

        return orderId;
    }


    public void delete(Long id) {
        Employee employee = findById(id);
        if(employee!=null){
            employee.setIsDeleted(Boolean.TRUE);
            employeeRepository.save(employee);
        }
    }

    public List<Employee> findAll(){
        return employeeRepository.findAllByIsDeletedFalseOrderById();
    }

    public List<Employee> findAllActiveEmployees(){
        return employeeRepository.findAllByIsDeletedFalseOrderById().
        stream().filter(employee -> employee.getTerminationDetails()==null).collect(Collectors.toList());
    }

    public Employee findById(Long id){
        return employeeRepository.findByIdAndIsDeletedFalse(id);
    }

    public Employee findByEmpId(String id){
        return employeeRepository.findByEmpIdAndIsDeletedFalse(id);
    }

    public ModelAndView addDependencies(ModelAndView modelAndView){
        modelAndView.addObject("jobStatus", JobStatus.keyValues);
        modelAndView.addObject("licenseClass", LicenseClass.keyValues);
        modelAndView.addObject("employeeTypes", EmployeeType.keyValues);
        modelAndView.addObject("verifiedTypes", VerifiedType.keyValues);
        modelAndView.addObject("states",commonService.getStates());
        modelAndView.addObject("cities",commonService.getCities());
        modelAndView.addObject("vehicleTypes", VehicleType.keyValues);
        return modelAndView;
    }

    public EmployeeDto getDto(Employee employee){
        try {
            EmployeeDto employeeDto = new EmployeeDto();
            BeanUtils.copyProperties(employee, employeeDto);
            employeeDto.setEmployeeType(employee.getEmployeeType().ordinal());
            if(employee.getEmployeeType().equals(EmployeeType.DRIVER)) {
                employeeDto.setJobStatus(employee.getJobStatus().ordinal());
                employeeDto.setLicenseClass(employee.getLicenseClass().ordinal());
                employeeDto.setVerifiedType(employee.getVerifiedType().ordinal());
                employeeDto.setVehicleType(employee.getVehicleType().ordinal());
            }
            return employeeDto;
        }catch (Exception e){
            throw new ServiceException(e.getMessage(),e);
        }
    }


    public ByteArrayInputStream exportReport(HttpServletRequest request, HttpServletResponse response) {

        String[] employeeTypeList=request.getParameterValues("employeeType");
        Boolean notTerminatedEmp =  request.getParameter("terminatedEmployee")!=null ? Boolean.TRUE : Boolean.FALSE;

        List<Employee> data = new ArrayList<>();
        if(employeeTypeList==null)
            data = findAll();
        else if(employeeTypeList.length>0)
            data = employeeRepository.getAllByEmployeeTypeList(Arrays.stream(employeeTypeList).map(emp->Integer.parseInt(emp)).collect(Collectors.toList()));

        if(notTerminatedEmp.equals(Boolean.TRUE))
           data =  data.stream().filter(employee -> employee.getTerminationDetails()==null).collect(Collectors.toList());

       return exportExcel(data,"Employee");
    }


    public ByteArrayInputStream exportExcel(List<Employee> data, String employee) {
        try {
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Sheet sheet = UtilService.initializeExcel(workbook,columnName,"Employee");

            // Create Other rows and cells with contacts data
            int rowCount = 1;
            for (Employee emp : data) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;

                row.createCell(columnCount++).setCellValue(emp.getFirstName());
                row.createCell(columnCount++).setCellValue(emp.getLastName());
                row.createCell(columnCount++).setCellValue(emp.getJoiningDate()!=null ? emp.getJoiningDate():"");
                row.createCell(columnCount++).setCellValue(emp.getDob());
                row.createCell(columnCount++).setCellValue(emp.getCnic());
                row.createCell(columnCount++).setCellValue(emp.getCnicExpiryDate());
                row.createCell(columnCount++).setCellValue(emp.getLicenseNumber());
                row.createCell(columnCount++).setCellValue(emp.getLicenseClass()!=null ? emp.getLicenseClass().getTitle() : "-");
                row.createCell(columnCount++).setCellValue(emp.getLicenseExpiryDate());
                row.createCell(columnCount++).setCellValue(emp.getLicenseLocation()!=null ? emp.getLicenseLocation() :"");
                row.createCell(columnCount++).setCellValue(emp.getSalary());
                row.createCell(columnCount++).setCellValue(emp.getCity());
                row.createCell(columnCount++).setCellValue(emp.getState());
                row.createCell(columnCount++).setCellValue(emp.getCountry());
                row.createCell(columnCount++).setCellValue(emp.getPhoneNumber());
                row.createCell(columnCount++).setCellValue(emp.getEmployeeType().getTitle());

                row.createCell(columnCount++).setCellValue(emp.getAddress());
                row.createCell(columnCount++).setCellValue(UtilService.isValid(emp.getRemarks()));
                row.createCell(columnCount++).setCellValue(emp.getJobStatus()!=null ? emp.getJobStatus().getTitle():"-");
                row.createCell(columnCount++).setCellValue(emp.getVerifiedType()!=null ? emp.getVerifiedType().getTitle() : "-");
                row.createCell(columnCount++).setCellValue(emp.getTerminationDetails()!=null ? "YES" : "NO");
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


    public List<Employee> findAllActiveReserveDrivers(){
       return employeeRepository.getAllActiveReserveDrivers(JobStatus.ACTIVE_DRIVER.ordinal(),JobStatus.RESERVE_DRIVER.ordinal(),EmployeeType.DRIVER.ordinal()).
                stream().filter(employee -> employee.getTerminationDetails()==null).collect(Collectors.toList());
    }

    public List<Employee> findAllActiveSupervisor(){
        return employeeRepository.findAllByEmployeeTypeAndIsDeletedFalse(EmployeeType.SUPERVISOR).
                stream().filter(employee -> employee.getTerminationDetails()==null).collect(Collectors.toList());
    }

    public void terminateEmployee(Termination termination, Long empId) {
        try {
            Employee employee=findById(empId);
            if(employee!=null){
                termination.setEmployee(employee);
                terminationRepository.save(termination);
                employee.setTerminationDetails(termination);
                employeeRepository.save(employee);
            }
            else throw new RecordNotFoundException("Employee data not found");
        }catch (Exception e){
            throw new ServiceException(e.getMessage(),e);
        }

    }
}
