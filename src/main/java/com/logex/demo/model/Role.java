package com.logex.demo.model;

import com.logex.demo.enums.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    @Column(
            name="role_name", unique = true
    )
    private UserType roleName;


    public Role(UserType roleName) {
        this.roleName = roleName;
    }

    public Role() {
    }

    public UserType getRoleName() {
        return roleName;
    }

    public void setRoleName(Integer i) {
        this.roleName = UserType.values()[i];
    }
}
