package com.logex.demo.dto;

import com.logex.demo.model.Employee;

import java.time.Month;
import java.util.List;

public class PayrollDto {

    private Long id;
    private Employee employee;
    private String monthYear;
    private String location;
    private String remarks;
    private Integer totalDays;
    private Double tripIncentive;
    private Double foodAllowanceAmount;
    private Double otherCharges;
    private Integer overTimeHours;
    private Double eidExGratia;
    private Double travelAllowance;
    private Double otherAllowance;
    private Double gratuityFund;
    private Double bonusPayable;
    private Double lifeInsurance;
    private Double medicalInsurance;
    private Double companyEobi;
    private Double employeeEobi;
    private Double sessi;
    private Double eduCess;
    private Double forms;
    private Boolean allEmployees;
    private Double deduction;
    private Double grossSalary;
    private Double basicSalary;

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

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Double getTripIncentive() {
        return tripIncentive;
    }

    public void setTripIncentive(Double tripIncentive) {
        this.tripIncentive = tripIncentive;
    }

    public Double getFoodAllowanceAmount() {
        return foodAllowanceAmount;
    }

    public void setFoodAllowanceAmount(Double foodAllowanceAmount) {
        this.foodAllowanceAmount = foodAllowanceAmount;
    }

    public Double getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(Double otherCharges) {
        this.otherCharges = otherCharges;
    }

    public Integer getOverTimeHours() {
        return overTimeHours;
    }

    public void setOverTimeHours(Integer overTimeHours) {
        this.overTimeHours = overTimeHours;
    }

    public Double getEidExGratia() {
        return eidExGratia;
    }

    public void setEidExGratia(Double eidExGratia) {
        this.eidExGratia = eidExGratia;
    }

    public Double getTravelAllowance() {
        return travelAllowance;
    }

    public void setTravelAllowance(Double travelAllowance) {
        this.travelAllowance = travelAllowance;
    }

    public Double getOtherAllowance() {
        return otherAllowance;
    }

    public void setOtherAllowance(Double otherAllowance) {
        this.otherAllowance = otherAllowance;
    }

    public Double getGratuityFund() {
        return gratuityFund;
    }

    public void setGratuityFund(Double gratuityFund) {
        this.gratuityFund = gratuityFund;
    }

    public Double getBonusPayable() {
        return bonusPayable;
    }

    public void setBonusPayable(Double bonusPayable) {
        this.bonusPayable = bonusPayable;
    }

    public Double getLifeInsurance() {
        return lifeInsurance;
    }

    public void setLifeInsurance(Double lifeInsurance) {
        this.lifeInsurance = lifeInsurance;
    }

    public Double getMedicalInsurance() {
        return medicalInsurance;
    }

    public void setMedicalInsurance(Double medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }

    public Double getCompanyEobi() {
        return companyEobi;
    }

    public void setCompanyEobi(Double companyEobi) {
        this.companyEobi = companyEobi;
    }

    public Double getEmployeeEobi() {
        return employeeEobi;
    }

    public void setEmployeeEobi(Double employeeEobi) {
        this.employeeEobi = employeeEobi;
    }

    public Double getSessi() {
        return sessi;
    }

    public void setSessi(Double sessi) {
        this.sessi = sessi;
    }

    public Double getEduCess() {
        return eduCess;
    }

    public void setEduCess(Double eduCess) {
        this.eduCess = eduCess;
    }

    public Double getForms() {
        return forms;
    }

    public void setForms(Double forms) {
        this.forms = forms;
    }

    public Boolean getAllEmployees() {
        return allEmployees;
    }

    public void setAllEmployees(Boolean allEmployees) {
        this.allEmployees = allEmployees;
    }

    public Double getDeduction() {
        return deduction;
    }

    public void setDeduction(Double deduction) {
        this.deduction = deduction;
    }

    public Double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(Double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }
}
