package com.GP.testdemo.doctors.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                //.passwordEncoder(new BCryptPasswordEncoder()
                .usersByUsernameQuery("select email, password, enabled from doctor_sign  where doctor_sign.email = ?")
                .authoritiesByUsernameQuery("select email, role from doctor_sign  where doctor_sign.email = ?")
                .dataSource(dataSource);
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http  //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                //.antMatchers(HttpMethod.POST, "/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/doctor/**").hasAnyRole("DOCTOR","ADMIN")
                .antMatchers(HttpMethod.DELETE, "/doctor/**").hasAnyRole("DOCTOR","ADMIN")
                .antMatchers(HttpMethod.GET, "/patient/**").hasAnyRole("PATIENT", "ADMIN")
                //.antMatchers(HttpMethod.POST, "/patient/**").hasAnyRole("PATIENT", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/patient/**").hasAnyRole("PATIENT","ADMIN")
                .antMatchers(HttpMethod.DELETE, "/patient/**").hasAnyRole("PATIENT","ADMIN")
                .and()
                .csrf().disable()
               .formLogin().disable();
    }
}