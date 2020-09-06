package com.logex.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


//    @Autowired
//    private CustomAuthenticationProvider authenticationProvider;

//    .antMatchers("/shift").hasAnyAuthority("ADMIN")

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/assets/**")
                .antMatchers("/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling()
                .accessDeniedHandler(new RestAccessDeniedHandler())
                .and().authorizeRequests()
                .antMatchers("/","/login**","/logout","/userLogin").permitAll()
                .antMatchers("/employee").hasAnyAuthority("ADMIN",
                "EMPLOYEE")
                .anyRequest().authenticated()

                .and().formLogin().loginPage("/login").failureUrl("/login?error=true")
                .and()
                .logout().logoutUrl("/logout").permitAll().clearAuthentication(true).invalidateHttpSession(true).deleteCookies()
                .logoutSuccessUrl("/").and().exceptionHandling().accessDeniedPage("/error");
    }

    /*	@Bean
	public AccessDeniedExceptionResolver accessDeniedExceptionResolver() {
		AccessDeniedExceptionResolver accessDeniedExceptionResolver = new AccessDeniedExceptionResolver();
		return accessDeniedExceptionResolver;
	}*/



//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider);
//    }
}
