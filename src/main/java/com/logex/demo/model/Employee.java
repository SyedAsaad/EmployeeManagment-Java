package com.logex.demo.model;

import com.logex.demo.enums.*;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {

    private String empId;
    private String firstName;
    private String lastName;
    private String address;
    private String alternateAddress;
    private String dob;
    @Column(unique = true)
    private String cnic;
    private String cnicExpiryDate;
    private String joiningDate;
    private String jobTitle;
    private String phoneNumber;
    private EmployeeType employeeType;
    private String country;
    private String state;
    private String city;
    private Double salary;
    private JobStatus jobStatus;
    private LicenseClass licenseClass;
    private String licenseNumber;
    private String licenseExpiryDate;
    private String licenseLocation;
    private VerifiedType verifiedType;
    private VehicleType vehicleType;
    private String remarks;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Termination terminationDetails;

    //Getters and setters omitted for brevity

    public void setDetails(Termination details) {
        if (details == null) {
            if (this.terminationDetails != null) {
                this.terminationDetails.setEmployee(null);
            }
        }
        else {
            details.setEmployee(this);
        }
        this.terminationDetails = details;
    }

    public Termination getTerminationDetails() {
        return terminationDetails;
    }

    public void setTerminationDetails(Termination terminationDetails) {
        this.terminationDetails = terminationDetails;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAlternateAddress() {
        return alternateAddress;
    }

    public void setAlternateAddress(String alternateAddress) {
        this.alternateAddress = alternateAddress;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getCnicExpiryDate() {
        return cnicExpiryDate;
    }

    public void setCnicExpiryDate(String cnicExpiryDate) {
        this.cnicExpiryDate = cnicExpiryDate;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(Integer i) {
        this.employeeType = EmployeeType.values()[i];
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer i) {
        this.jobStatus = JobStatus.values()[i];
    }

    public LicenseClass getLicenseClass() {
        return licenseClass;
    }

    public void setLicenseClass(Integer i) {
        this.licenseClass = LicenseClass.values()[i];
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(String licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public VerifiedType getVerifiedType() {
        return verifiedType;
    }

    public void setVerifiedType(Integer i) {
        if(i!=null)
        this.verifiedType = VerifiedType.values()[i];
        else
            this.verifiedType = VerifiedType.NOT_CHECK;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Integer i) {
        this.vehicleType = VehicleType.values()[i];
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLicenseLocation() {
        return licenseLocation;
    }

    public void setLicenseLocation(String licenseLocation) {
        this.licenseLocation = licenseLocation;
    }
}
