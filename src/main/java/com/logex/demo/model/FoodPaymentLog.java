package com.logex.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "food_payment_log")
public class FoodPaymentLog extends BaseEntity{
    private String fromDate;
    private String toDate;
    private String totalDays;
    private double amount;
    private double totalAmount;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Employee employee;

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
