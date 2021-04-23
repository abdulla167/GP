package com.server.mothercare.rest.user;

import com.server.mothercare.entities.Event;
import com.server.mothercare.entities.User;
import com.server.mothercare.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
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

    @PostMapping("/addEvent")
    public ResponseEntity addEvent(@RequestBody Event event){
        log.debug(event.getEventName());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity getUserProfile(HttpServletRequest request){
        User user= userService.userbyUserName(request.getUserPrincipal().getName());
        return user != null? new ResponseEntity(user, HttpStatus.OK) : new ResponseEntity("\"Failure\"", HttpStatus.CONFLICT);
    }
}
