package com.server.mothercare.rest.kit;


import com.server.mothercare.entities.kit.DeviceUsersSse;
import com.server.mothercare.services.BabyMonitorService;
import com.server.mothercare.services.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;
import java.util.List;

import org.json.*;

@RestController
@Slf4j
public class BabyMonitorController {

    BabyMonitorService babyMonitorService;

    @Autowired
    BabyMonitorController(BabyMonitorService babyMonitorService){
        this.babyMonitorService = babyMonitorService;
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
}
