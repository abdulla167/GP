package com.server.mothercare.rest.user;

import com.server.mothercare.entities.UserProfile;
import com.server.mothercare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

//    @GetMapping("/profile/{userId}")
//    public UserProfile getUserProfile(@PathVariable int userId){
//        return this.userService.getUserProfile(userId);
//    }
}
