package com.logex.demo.model;

import javax.persistence.*;
import java.time.Month;

@Entity
@Table(name = "payroll")
public class PayRoll extends BaseEntity{

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Employee employee;
    private String monthYear;
    private String location;
    private String remarks;
    private Integer totalDays;
    private Double tripIncentive;
    private Double FoodAllowanceAmount;
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
    private Double deduction;
    private Double basicSalary;


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        return FoodAllowanceAmount;
    }

    public void setFoodAllowanceAmount(Double foodAllowanceAmount) {
        FoodAllowanceAmount = foodAllowanceAmount;
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

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public Double getDeduction() {
        return deduction;
    }

    public void setDeduction(Double deduction) {
        this.deduction = deduction;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }
}
