package com.server.mothercare.rest.user;

import com.server.mothercare.entities.Event;
import com.server.mothercare.entities.User;
import com.server.mothercare.entities.kit.MonitoringDevice;
import com.server.mothercare.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@RestController
@Slf4j
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }


    @PostMapping("/addEvent")
    public ResponseEntity addEvent(@RequestBody Event event, Principal user){
        Optional<User> optionalUser = this.userService.getUserbyUserName(user.getName());
        optionalUser.ifPresent(user1 ->{
            user1.getEvents().add(event);
            this.userService.update(user1);
        });
        return new ResponseEntity(event, HttpStatus.OK);
    }

    @PostMapping("/addDevice")
    public ResponseEntity addDevice(@RequestBody String  jsonString, Principal user){
        JSONObject jsonObject = new JSONObject(jsonString);
        long deviceId = Long.valueOf(jsonObject.getLong("deviceId"));
        MonitoringDevice monitoringDevice = new MonitoringDevice();
        monitoringDevice.setDeviceId(deviceId);
        Optional<User> optionalUser = this.userService.getUserbyUserName(user.getName());
        optionalUser.ifPresent(user1 ->{
            monitoringDevice.setUser(user1);
            user1.getDevices().add(monitoringDevice);
            this.userService.update(user1);
        });
        return new ResponseEntity(HttpStatus.OK);
    }


}
