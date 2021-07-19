package com.server.mothercare.rest.kit;


import com.server.mothercare.entities.User;
import com.server.mothercare.entities.kit.DeviceUsersSse;
import com.server.mothercare.entities.kit.MonitoringDevice;
import com.server.mothercare.services.BabyMonitorService;
import com.server.mothercare.services.NotificationService;
import com.server.mothercare.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.json.*;

@RestController
@Slf4j
public class BabyMonitorController {

    BabyMonitorService babyMonitorService;
    UserService userService;

    @Autowired
    BabyMonitorController(BabyMonitorService babyMonitorService, UserService userService){
        this.babyMonitorService = babyMonitorService;
        this.userService = userService;
    }

    /* Device send to this endpoint to connect to server*/
    @PostMapping("device/connect")
    public void connectDevice(@RequestBody String jsonString){
        var jsonObject = new JSONObject(jsonString);
        log.error("connect device : " + jsonObject.getLong("deviceId"));
        this.babyMonitorService.connectDevice(jsonObject.getLong("deviceId"));
    }

    /* Device send to this endpoint to disconnect with server */
    @PostMapping("device/disconnect")
    public void disconnectDevice(@RequestBody String jsonString){
        var jsonObject = new JSONObject(jsonString);
        this.babyMonitorService.disconnectDevice(jsonObject.getLong("deviceId"));
    }

    /* Client send to this endpoint to subscribe to a device with exact id */
    @GetMapping("device/subscribe/{id}")
    public SseEmitter subscribe(@PathVariable String id){
        var deviceId = Long.valueOf(id);
        SseEmitter sseEmitter = new SseEmitter(-1L);
        var result = this.babyMonitorService.subscribeDevice(sseEmitter, deviceId);
        sseEmitter.onCompletion(() -> {
            List<DeviceUsersSse> subscribers = this.babyMonitorService.getEmitters().get(deviceId);
//            subscribers.removeIf(deviceUsersSse -> (deviceUsersSse.getUsername().equals(username)));
        });
        sseEmitter.onError((error) -> {
            sseEmitter.complete();
        });
        return sseEmitter;
    }

    /* Device send to this endpoint to send sensors data to its subscribers */
    @PostMapping("device/push")
    public void sendData(@RequestBody String data){
        JSONObject json = new JSONObject(data);
        this.babyMonitorService.pushNewData(json);
    }

    @GetMapping("/getDevices")
    public ResponseEntity getUserDevices(Principal user){
        log.error("get devices");
        Optional<User> optionalUser = this.userService.getUserbyUserName(user.getName());
        User theUser = optionalUser.get();
        List<MonitoringDevice> monitoringDeviceList = theUser.getDevices();
        for (MonitoringDevice device :
                monitoringDeviceList) {
            log.error(device.getBabyName());
        }
        return new ResponseEntity(monitoringDeviceList, HttpStatus.OK);
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
}
