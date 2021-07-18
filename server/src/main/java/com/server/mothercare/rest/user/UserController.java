package com.server.mothercare.rest.user;

import com.server.mothercare.entities.Event;
import com.server.mothercare.entities.User;
import com.server.mothercare.entities.kit.MonitoringDevice;
import com.server.mothercare.services.BabyMonitorService;
import com.server.mothercare.services.NotificationService;
import com.server.mothercare.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class UserController {
    private UserService userService;
    private BabyMonitorService babyMonitorService;
    private NotificationService notificationService;

    @Autowired
    public UserController(UserService userService, BabyMonitorService babyMonitorService, NotificationService notificationService)
    {
        this.userService = userService;
        this.babyMonitorService = babyMonitorService;
        this.notificationService = notificationService;
    }

    @GetMapping("/connect/{username}")
    public SseEmitter connect(@PathVariable String username){
        log.debug(username);
        User theUser = this.userService.userbyUserName(username);
        SseEmitter sseEmitter = new SseEmitter(-1L);
        sseEmitter.onError((error) -> {
            this.notificationService.removeUser(theUser.getUserId());
        });
        log.error("adding user to be online with id : " + theUser.getUserId());
        this.notificationService.addUser(theUser.getUserId(), sseEmitter);
        return sseEmitter;
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
        String babyName = jsonObject.getString("babyName");
        MonitoringDevice monitoringDevice = new MonitoringDevice();
        monitoringDevice.setDeviceId(deviceId);
        monitoringDevice.setBabyName(babyName);
        Optional<User> optionalUser = this.userService.getUserbyUserName(user.getName());
        optionalUser.ifPresent(user1 ->{
            monitoringDevice.setUser(user1);
            user1.getDevices().add(monitoringDevice);
            this.babyMonitorService.addDevice(monitoringDevice);
        });
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/getDevices")
    public ResponseEntity getUserDevices(Principal user){
        Optional<User> optionalUser = this.userService.getUserbyUserName(user.getName());
        User theUser = optionalUser.get();
        List<MonitoringDevice> monitoringDeviceList = theUser.getDevices();
        for (MonitoringDevice device :
                monitoringDeviceList) {
            log.debug(device.getBabyName());
        }
        return new ResponseEntity(monitoringDeviceList, HttpStatus.OK);
    }


}
