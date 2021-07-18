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

    @GetMapping("/user")
    public ResponseEntity getUserProfile(Principal userPrincipal){
        User user= userService.userbyUserName(userPrincipal.getName());
        return user != null? new ResponseEntity(user, HttpStatus.OK) : new ResponseEntity("\"Failure\"", HttpStatus.CONFLICT);
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
        log.error(event.getTitle());
        Optional<User> optionalUser = this.userService.getUserbyUserName(user.getName());
        optionalUser.ifPresent(user1 ->{
            user1.numOfEvents++;
            event.setId(user1.numOfEvents);
            user1.getEvents().add(event);
            this.userService.update(user1);
        });
        return new ResponseEntity(event, HttpStatus.OK);
    }

    @PostMapping("/editEvent")
    public ResponseEntity editEvent(@RequestBody Event event, Principal user){
        Optional<User> optionalUser = this.userService.getUserbyUserName(user.getName());
        optionalUser.ifPresent(user1 ->{
            log.error("new event : " + event.getTitle() + "id" + String.valueOf(event.getId()));
            log.error("new event : " + event.getPrimaryColor() + " " + event.getSecondaryColor());
            for (var e :
                    user1.getEvents()) {
                log.warn("event : " + e.getTitle() + "id" + String.valueOf(e.getId()));

                if (e.getId() == event.getId()){
                    log.error("old event : " + e.getTitle());
                    e.setTitle(event.getTitle());
                    e.setReminder(event.isReminder());
                    e.setPrimaryColor(event.getPrimaryColor());
                    e.setSecondaryColor(event.getSecondaryColor());
                    e.setTitle(event.getTitle());
                    e.setStartDate(event.getStartDate());
                    e.setEndDate(event.getEndDate());
                }
            }
            this.userService.update(user1);
        });
        return new ResponseEntity(event, HttpStatus.OK);
    }

    @PostMapping("/deleteEvent")
    public ResponseEntity deleteEvent(@RequestBody Event event, Principal user){
        Optional<User> optionalUser = this.userService.getUserbyUserName(user.getName());
        optionalUser.ifPresent(user1 ->{
            for (var e :
                    user1.getEvents()) {
                user1.getEvents().removeIf(event1 -> event1.getId() == event.getId());
            }
            this.userService.update(user1);
        });
        return new ResponseEntity(HttpStatus.OK);
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
