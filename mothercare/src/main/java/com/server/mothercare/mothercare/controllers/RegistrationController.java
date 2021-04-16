package com.server.mothercare.mothercare.controllers;

import com.server.mothercare.mothercare.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegistrationController {
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsManager;

    @Autowired
    public RegistrationController(PasswordEncoder passwordEncoder, UserDetailsService userDetailsManager){
        this.passwordEncoder = passwordEncoder;
        this.userDetailsManager= userDetailsManager;
    }

    @PostMapping("newUser")
    public boolean registerUser(@RequestBody User user){
        return true;
    }
}
