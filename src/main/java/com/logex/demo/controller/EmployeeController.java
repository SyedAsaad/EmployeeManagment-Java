package com.logex.demo.controller;

import com.logex.demo.config.URLConstants;
import com.logex.demo.dto.EmployeeDto;
import com.logex.demo.dto.ResponseDto;
import com.logex.demo.enums.EmployeeType;
import com.logex.demo.enums.JobStatus;
import com.logex.demo.model.Employee;
import com.logex.demo.model.Termination;
import com.logex.demo.service.EmployeeService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller()
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;



    @GetMapping
    private ModelAndView getEmployee(){
        ModelAndView modelAndView = new ModelAndView("employee");
        modelAndView.addObject("employee",new Employee());
        employeeService.addDependencies(modelAndView);
        return modelAndView;
    }

    @GetMapping(value = URLConstants.LIST_ALL)
    private ResponseDto<Employee> getEmployees(){
        List<Employee> employeeList = employeeService.findAllActiveEmployees();
        return new ResponseDto<Employee>(Boolean.FALSE,employeeList);
    }

    @GetMapping(value = "/list")
    private ModelAndView getAllEmployee(){
        ModelAndView modelAndView = new ModelAndView("listEmployee");
        modelAndView.addObject("employees",employeeService.findAll());
        modelAndView.addObject("empTypes",EmployeeType.keyValues);
        modelAndView.addObject("jobStatuses",JobStatus.keyValues);
        employeeService.addDependencies(modelAndView);
        return modelAndView;
    }

    @PostMapping(value = "/export")
    private void exportEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        String filename = "employee-"+ new Date().getTime()+".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename="+filename);
                ByteArrayInputStream stream = employeeService.exportReport(request,response);
        IOUtils.copy(stream, response.getOutputStream());
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    private String saveEmployee(@ModelAttribute EmployeeDto employee){
        employeeService.save(employee);
        return "redirect:/employee";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    private String delete(@PathVariable Long id){
        employeeService.delete(id);
        return "redirect:/employee/list";
    }


    @GetMapping(value = "/terminate/{empId}/")
    private ModelAndView terminate(@PathVariable Long empId){
        ModelAndView modelAndView = new ModelAndView("terminationDetail");
        modelAndView.addObject("termination",new Termination(employeeService.findById(empId)));
        return modelAndView;
    }

    @PostMapping(value = {"terminate/{id}"+URLConstants.SAVE_URL,URLConstants.EDIT_URL+URLConstants.SAVE_URL})
    private String terminate(@ModelAttribute Termination termination,@PathVariable Long id){
        employeeService.terminateEmployee(termination,id);
        return "redirect:/employee/list";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    private ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("employee");
        modelAndView.addObject("employee",employeeService.getDto(employeeService.findById(id)));
        employeeService.addDependencies(modelAndView);
        return modelAndView;
    }

    @GetMapping(value = "/terminated")
    private ModelAndView getTerminatedEmployee(){
        ModelAndView modelAndView = new ModelAndView("terminatedEmployee");
        modelAndView.addObject("employees",employeeService.findAllTerminatedEmployees());
        employeeService.addDependencies(modelAndView);
        return modelAndView;
    }

    @GetMapping(value = "/terminate"+URLConstants.DELETE_URL)
    private String deleteTermination(@PathVariable Long empId){
        employeeService.unTerminateEmployee(empId);
        return "redirect:/employee/terminated";
    }


}
