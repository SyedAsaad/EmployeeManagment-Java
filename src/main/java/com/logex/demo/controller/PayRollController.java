package com.logex.demo.controller;

import com.logex.demo.config.URLConstants;
import com.logex.demo.dto.FileUploadDto;
import com.logex.demo.dto.PayrollDto;
import com.logex.demo.enums.ReportType;
import com.logex.demo.service.EmployeeService;
import com.logex.demo.service.FoodPaymentService;
import com.logex.demo.service.PayrollService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/payroll")
public class PayRollController {

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private FoodPaymentService foodPaymentService;


    @GetMapping
    private ModelAndView getPayrollForm(){
        ModelAndView modelAndView =  new ModelAndView("payroll");
        modelAndView.addObject("payroll",new FileUploadDto());
        return modelAndView;
    }

    @PostMapping(value = "/import")
    private String savePayRoll(@ModelAttribute FileUploadDto fileUploadDto){
        payrollService.uploadFile(fileUploadDto);
        return "redirect:/payroll";
    }

    @GetMapping(value = "/export")
    private void export(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        String filename = "employeePayroll-"+new Date().getTime()+".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename="+filename);
        ByteArrayInputStream stream = payrollService.exportExcel();
        IOUtils.copy(stream, response.getOutputStream());
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    private String savePayRoll(@ModelAttribute PayrollDto payrollDto){
//        payrollService.save(payrollDto);
        return "redirect:/payroll";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    private String delete(@PathVariable Long id){
        payrollService.delete(id);
        return "redirect:/payroll";
    }

    @GetMapping(value = URLConstants.REPORT)
    private ModelAndView getPayrollReport(){
        ModelAndView modelAndView =  new ModelAndView("payrollList");
//        modelAndView.addObject("payroll",new FileUploadDto());
        return modelAndView;
    }

    @PostMapping(value = URLConstants.EXPORT_REPORT,  params="action=excel")
    private void exportReport(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        String filename = request.getParameter("reportType")+"-"+new Date().getTime()+".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename="+filename);
        ByteArrayInputStream stream = payrollService.exportReport(request,response);
        IOUtils.copy(stream, response.getOutputStream());
    }

    @PostMapping(value = URLConstants.EXPORT_REPORT,  params="action=search")
    private ModelAndView exportReport(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("payrollList");
        modelAndView.addObject("payRollList",payrollService.searchPayroll(request));
        return modelAndView;
    }
}
