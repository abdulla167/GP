package com.server.mothercare.rest.kit;


import com.server.mothercare.entities.kit.DeviceUsersSse;
import com.server.mothercare.services.BabyMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;
import java.util.List;

import org.json.*;

@RestController
public class BabyMonitorController {

    BabyMonitorService babyMonitorService;

    @Autowired
    BabyMonitorController(BabyMonitorService babyMonitorService){
        this.babyMonitorService = babyMonitorService;
    }

    @PostMapping("device/add")
    public void addDevice(@RequestBody String jsonString, Principal user){
        var jsonObject = new JSONObject(jsonString);
        this.babyMonitorService.addUserDevice(jsonObject.getLong("deviceId"), user.getName());
    }

    @PostMapping("device/connect")
    public void connectDevice(@RequestBody String jsonString){
        var jsonObject = new JSONObject(jsonString);
        this.babyMonitorService.connectDevice(jsonObject.getLong("deviceId"));
    }

    @PostMapping("device/disconnect")
    public void disconnectDevice(@RequestBody String jsonString){
        var jsonObject = new JSONObject(jsonString);
        this.babyMonitorService.disconnectDevice(jsonObject.getLong("deviceId"));
    }

    @PostMapping("/subscribe")
    public SseEmitter subscribe(@RequestBody String jsonString, Principal user){
        var jsonObject = new JSONObject(jsonString);
        SseEmitter sseEmitter = new SseEmitter(-1L);
        int result = this.babyMonitorService.subscribeDevice(user.getName(), sseEmitter);
        if (result == 0){
            sseEmitter = null;
        }
        sseEmitter.onCompletion(() -> {
            List<DeviceUsersSse> subscribers = this.babyMonitorService.getEmitters().get(jsonObject.getLong("deviceId"));
            subscribers.removeIf(deviceUsersSse -> (deviceUsersSse.getUsername().equals(user.getName())));
        });
        return sseEmitter;
    }

    @PostMapping("device/push")
    public void insert(@RequestBody String data){
        System.out.println(data);
        JSONObject json = new JSONObject(data);
        this.babyMonitorService.pushNewData(json);
    }
}
