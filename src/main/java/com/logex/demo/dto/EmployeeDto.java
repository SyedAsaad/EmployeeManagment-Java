package com.logex.demo.dto;


import javax.validation.constraints.NotNull;

public class EmployeeDto {
    private Long id;
    private String empId;
    @NotNull(message = "First name cannot be null")
    private String firstName;
    @NotNull(message = "Last name cannot be null")
    private String lastName;
    @NotNull(message = "Address cannot be null")
    private String address;
    private String alternateAddress;
    private String dob;
    private String cnic;
    private String cnicExpiryDate;
    private String joiningDate;
    private String jobTitle;
    private String phoneNumber;
    private Integer employeeType;
    private String country;
    private String state;
    private String city;
    private Double salary;
    private Integer jobStatus;
    private Integer licenseClass;
    private String licenseNumber;
    private String licenseExpiryDate;
    private String licenseLocation;
    private Integer verifiedType;
    private Integer vehicleType;
    private String remarks;
    private Boolean isTerminated;

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

    public Integer getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(Integer employeeType) {
        this.employeeType = employeeType;
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

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Integer getLicenseClass() {
        return licenseClass;
    }

    public void setLicenseClass(Integer licenseClass) {
        this.licenseClass = licenseClass;
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

    public Integer getVerifiedType() {
        return verifiedType;
    }

    public void setVerifiedType(Integer verifiedType) {
        this.verifiedType = verifiedType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getIsTerminated() {
        return this.isTerminated;
    }

    public void setIsTerminated(Boolean isTerminated) {
        this.isTerminated = isTerminated;
    }

    public String getLicenseLocation() {
        return licenseLocation;
    }

    public void setLicenseLocation(String licenseLocation) {
        this.licenseLocation = licenseLocation;
    }
}
