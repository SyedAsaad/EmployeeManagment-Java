package com.logex.demo;

import com.logex.demo.enums.UserType;
import com.logex.demo.model.Role;
import com.logex.demo.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class EmployeeManagementApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(EmployeeManagementApplication.class);
    }

    private static final List<Role> roleList = new ArrayList<Role>(){{add(new Role(UserType.ADMIN));
        add(new Role(UserType.EMPLOYEE)); add(new Role(UserType.VIEWER));}};

    @Bean
    public CommandLineRunner addRoles(RoleRepository repo) {
        if(repo.findAll().size()<roleList.size()) {
            repo.deleteAll();
            repo.saveAll(roleList);
        }
        return null;
    }
    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

}
