package com.server.mothercare.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "motherService";

    private MyCorsFilter myCorsFilter;

    @Autowired
    public ResourceServerConfig(MyCorsFilter myCorsFilter){
        this.myCorsFilter = myCorsFilter;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources){
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.cors().and().addFilterBefore(myCorsFilter, ChannelProcessingFilter.class).authorizeRequests().mvcMatchers(HttpMethod.POST,"/register/newUser").permitAll()
                .mvcMatchers(HttpMethod.GET,"/confirm-account").permitAll()
                .anyRequest().authenticated().and().csrf().disable();
    }
}

