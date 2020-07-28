package com.logex.demo.repository;

import com.logex.demo.enums.UserType;
import com.logex.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByRoleName(UserType userType);
}
