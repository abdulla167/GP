package com.server.mothercare.services;

import com.server.mothercare.DAOs.DeviceDAO;
import com.server.mothercare.DAOs.UserDAO;
import com.server.mothercare.entities.kit.*;
import com.server.mothercare.models.kit.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class BabyMonitorServiceImpl implements BabyMonitorService{
    Hashtable<Long,List<DeviceUsersSse>> deviceUsers = new Hashtable<>();

    DeviceDAO deviceDAO;

    UserDAO userDAO;

    NotificationService notificationService;


    @Autowired
    BabyMonitorServiceImpl(DeviceDAO deviceDAO, UserDAO userDAO, NotificationService notificationService){
        this.deviceDAO = deviceDAO;
        this.userDAO = userDAO;
        this.notificationService = notificationService;
    }

    @Override
    public void sendDataToUsers(JSONObject vitalSignReads) {

    }

    @Override
    public Hashtable<Long, List<DeviceUsersSse>> getEmitters() {
        return deviceUsers;
    }

    public void connectDevice(long id){
        this.deviceDAO.findById(id).ifPresentOrElse(device -> {
            this.deviceUsers.putIfAbsent(id, new ArrayList<>());
            try {
                log.error("loading...");
                this.notificationService.notifyConnectedDevice(device.getUser().getUserId(), "connected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, ()-> new Exception("This device is not found"));
    }

    @Override
    public void disconnectDevice(long id){
        this.deviceDAO.findById(id).ifPresentOrElse(device -> {
            this.deviceUsers.remove(id);
            try {
                this.notificationService.notifyConnectedDevice(device.getUser().getUserId(), "disconnect");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, ()-> new Exception("This device is not found"));
    }

    @Override
    public int subscribeDevice(SseEmitter emitter, Long id){
        int connectedDevice = 0;
        Optional<MonitoringDevice> device = this.deviceDAO.findById(id);
        device.ifPresent(userDevice -> {
            if(this.deviceUsers.containsKey(id)){
                String username = userDevice.getUser().getUsername();
                log.error(username +" :"+ id );
                this.deviceUsers.get(id).add(new DeviceUsersSse(username, emitter));
            }
        });
        if (device.isPresent())
        {
            connectedDevice = 1;
        }
        return connectedDevice;
    }

    @Override
    public void pushNewData(JSONObject json){
        Long id = json.getLong("deviceId");
        this.deviceDAO.findById(id).ifPresentOrElse(device -> {
            this.deviceUsers.putIfAbsent(id, new ArrayList<>());
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(()->{
                TempRead tempRead = new TempRead(json.getDouble("tempRead"), new Date());
                HeartRateRead heartRateRead = new HeartRateRead(json.getDouble("heartrateRead"), new Date());
                SPO2Read spo2Read = new SPO2Read(json.getDouble("spo2Read"), new Date());
                PositionRead positionRead = new PositionRead(json.getDouble("positionRead"), new Date());
                SensorsReads sensorsReads = new SensorsReads(tempRead, spo2Read, heartRateRead, positionRead);
                for (DeviceUsersSse deviceUsersSse: this.deviceUsers.get(id)) {
                    try {
                        deviceUsersSse.getEmitters().send(SseEmitter.event().data(sensorsReads));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            executor.shutdown();
        }, ()-> new Exception("This device is not found"));
    }

    @Override
    public void addDevice(MonitoringDevice device){
        this.deviceDAO.save(device);
    }


}
