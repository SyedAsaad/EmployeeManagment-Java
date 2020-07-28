package com.logex.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "employee_termination")
public class Termination extends BaseEntity{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id")
    private Employee employee;
    private String remarks;
    private String date;

    public Termination(Employee employee) {
        this.employee = employee;
    }

    public Termination() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
