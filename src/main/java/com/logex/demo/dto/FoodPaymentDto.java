package com.logex.demo.dto;

import com.logex.demo.model.Employee;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class FoodPaymentDto {
    private Long id;
    private String fromDate;
    private String toDate;
    private String totalDays;
    private double amount;
    private double totalAmount;
    private List<Long> employees;
    private Employee employee;

    public FoodPaymentDto() {
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Long> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Long> employees) {
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void addEmployee(Long employeeId){
        if(employees==null){
            employees = new ArrayList<>();}
        this.employees.add(employeeId);
    }
}
