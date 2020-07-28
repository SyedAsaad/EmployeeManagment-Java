package com.logex.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
public class JpaAuditingConfiguration {

    @Autowired
    private HttpSession httpSession;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAware<String>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if(authentication!=null && !authentication.getPrincipal().toString().equalsIgnoreCase("anonymoususer"))
                {
                    return Optional.of(authentication.getName());
                }
                return Optional.of("superadmin@anonymous.com");

            }
        };

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }


}