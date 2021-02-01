package com.server.mothercare.rest.user;

import com.server.mothercare.entities.User;
import com.server.mothercare.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
public class RegistrationController {
   private PasswordEncoder encoder;
   private UserService userService;

   @Autowired
   public RegistrationController(PasswordEncoder encoder, UserService userService){
      this.encoder = encoder;
      this.userService = userService;
   }

   @PostMapping(value = "/register/newUser")
   public User registerUser(@RequestBody User user){
      user.setPassword(this.encoder.encode(user.getPassword()));
      this.userService.registerUser(user);
      return user;
   }
}