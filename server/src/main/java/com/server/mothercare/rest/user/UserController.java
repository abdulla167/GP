package com.server.mothercare.rest.user;

import com.server.mothercare.entities.Event;
import com.server.mothercare.entities.User;
import com.server.mothercare.entities.kit.MonitoringDevice;
import com.server.mothercare.services.BabyMonitorService;
import com.server.mothercare.services.NotificationService;
import com.server.mothercare.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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


    @GetMapping("/getUser")
    public ResponseEntity getUser(Principal user){
        Optional<User> optionalUser = this.userService.getUserbyUserName(user.getName());
        ResponseEntity responseEntity = null;
        var resultUser = optionalUser.get();
        if (user != null){
            responseEntity = new ResponseEntity(resultUser, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("/connect/{username}")
    public SseEmitter connect(@PathVariable String username){
        log.warn("connected to server");
        User theUser = this.userService.userbyUserName(username);
        SseEmitter sseEmitter = new SseEmitter(-1L);
        sseEmitter.onError((error) -> {
            this.notificationService.removeUser(theUser.getUserId());
        });
        log.error("adding user to be online with id : " + theUser.getUserId());
        this.notificationService.addUser(theUser.getUserId(), sseEmitter);
        return sseEmitter;
    }

    @PostMapping("/addUserInfo")
    public ResponseEntity addUserInfo(@RequestBody String jsonString, Principal user){
        var jsonObject = new JSONObject(jsonString);
        Optional<User> optionalUser = this.userService.getUserbyUserName(user.getName());
        optionalUser.ifPresent(user1 ->{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            user1.setHeight((float) jsonObject.getDouble("height"));
            user1.setHeight((float) jsonObject.getDouble("weight"));
            user1.setHeight((float) jsonObject.getDouble("anemiaRate"));
            try{
                user1.setLastPeriod(LocalDateTime.parse(jsonObject.getString("lastPeriodDate"), formatter));
            } catch (JSONException jsonException){
                user1.setLastPeriod(null);
            }
            try {
                user1.setPregnancyDate(LocalDateTime.parse(jsonObject.getString("pregnancyDate"), formatter));
            } catch (JSONException jsonException){
                user1.setPregnancyDate(null);
            }
            user1.setPeriodLength(jsonObject.getInt("periodLength"));
            user1.setPregnant(jsonObject.getBoolean("pregnant"));
            user1.setHaveChildren(jsonObject.getBoolean("haveChildren"));
            user1.setChildrenNum(jsonObject.getInt("childrenNum"));
            user1.setBloodType(jsonObject.getString("bloodType"));
            user1.setAdditionalInfo(true);
            this.userService.update(user1);
        });
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/skipUserInfo")
    public ResponseEntity skipUserInfo(@RequestBody String jsonString, Principal user){
        var jsonObject = new JSONObject(jsonString);
        Optional<User> optionalUser = this.userService.getUserbyUserName(user.getName());
        optionalUser.ifPresent(user1 ->{
            user1.setAdditionalInfo(true);
            log.error(jsonString);
        });
        return new ResponseEntity(HttpStatus.OK);
    }

}
