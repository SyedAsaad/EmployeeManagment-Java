package com.logex.demo.controller;

import com.logex.demo.config.URLConstants;
import com.logex.demo.dto.FoodPaymentDto;
import com.logex.demo.enums.EmployeeType;
import com.logex.demo.enums.JobStatus;
import com.logex.demo.service.EmployeeService;
import com.logex.demo.service.FoodPaymentService;
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

@Controller()
@RequestMapping("/food/payment")
public class FoodPaymentController {

    @Autowired
    private FoodPaymentService foodPaymentService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    private ModelAndView getFoodPayment(){
        ModelAndView modelAndView = new ModelAndView("foodPayment");
        modelAndView.addObject("foodPayment",new FoodPaymentDto());
        modelAndView.addObject("employeeList",employeeService.findAllActiveReserveDrivers());
//        beachLogService.addDependentDetais(modelAndView);

        return modelAndView;
    }

    @GetMapping(value = "/list")
    private ModelAndView getAllEmployee(){
        ModelAndView modelAndView = new ModelAndView("listFoodPaymentLog");
        modelAndView.addObject("foodPayments", foodPaymentService.findAll());
        return modelAndView;
    }

    @PostMapping(value = "/export")
    private void exportEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        String filename = "foodPayment-"+new Date().getTime()+".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename="+filename);
        ByteArrayInputStream stream = foodPaymentService.exportReport(request,response);
        IOUtils.copy(stream, response.getOutputStream());
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    private String saveFoodPayment(@ModelAttribute FoodPaymentDto foodPaymentDto){
        foodPaymentService.save(foodPaymentDto);
        return "redirect:/food/payment";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    private String delete(@PathVariable Long id){
        foodPaymentService.delete(id);
        return "redirect:/food/payment";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    private ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("foodPayment");
        modelAndView.addObject("foodPayment",foodPaymentService.getDto(foodPaymentService.findOne(id)));
        modelAndView.addObject("employeeList",employeeService.findAllActiveReserveDrivers());
        return modelAndView;
    }

}
