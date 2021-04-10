package com.server.mothercare.rest.user;

import com.server.mothercare.entities.User;
import com.server.mothercare.entities.UserProfile;
import com.server.mothercare.services.UserService;
import net.minidev.json.JSONObject;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/user")
    public ResponseEntity getUserProfile(HttpServletRequest request){
        User user= userService.userbyUserName(request.getUserPrincipal().getName());
        return user != null? new ResponseEntity(user, HttpStatus.OK) : new ResponseEntity("\"Failure\"", HttpStatus.CONFLICT);
    }
}
