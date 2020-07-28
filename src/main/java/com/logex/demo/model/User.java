package com.logex.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table
@Entity(name = "user")
public class User extends BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false,unique = true)
    private String email;

    @Column(name="phone_number",length = 30)
    private String phoneNumber;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "address")
    private String address;

    @Column(name="password",length = 200)
    private String password;


    @OneToOne()
    private Role role ;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
