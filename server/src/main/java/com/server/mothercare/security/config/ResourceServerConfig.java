package com.server.mothercare.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "motherService";



    @Override
    public void configure(ResourceServerSecurityConfigurer resources){
        resources.resourceId(RESOURCE_ID);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().mvcMatchers(HttpMethod.POST,"/register/newUser").permitAll()
                .mvcMatchers(HttpMethod.GET,"/confirm-account").permitAll()
                .anyRequest().authenticated().and().csrf().disable();
    }
}

