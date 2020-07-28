package com.logex.demo.repository;

import com.logex.demo.enums.UserType;
import com.logex.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    List<User> findAllByRole_RoleName(UserType userType);
}
