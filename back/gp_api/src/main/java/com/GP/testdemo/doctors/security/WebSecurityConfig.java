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
                .dataSource(dataSource)
                .usersByUsernameQuery("select email, password, enabled from sign_in  where email = ?")
                .authoritiesByUsernameQuery("select email, role from sign_in  where email = ?")
                ;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http  //HTTP Basic authentication
                .httpBasic()
                .and()

                .authorizeRequests()

                .antMatchers(HttpMethod.GET, "/doctor/**").permitAll()//hasAnyRole("DOCTOR", "ADMIN")
//                //.antMatchers(HttpMethod.POST, "/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/doctor/**").permitAll()//hasAnyRole("DOCTOR","ADMIN")
                .antMatchers(HttpMethod.DELETE, "/doctor/**").permitAll()//hasAnyRole("DOCTOR","ADMIN")
//                .antMatchers(HttpMethod.GET, "/patient/**").hasAnyRole("PATIENT", "ADMIN")
//                //.antMatchers(HttpMethod.POST, "/patient/**").hasAnyRole("PATIENT", "ADMIN")
 //               .antMatchers(HttpMethod.PUT, "/patient/**").permitAll()//hasAnyRole("PATIENT","ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/patient/**").hasAnyRole("PATIENT","ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}