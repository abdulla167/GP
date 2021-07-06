package com.server.mothercare.services;

import com.server.mothercare.DAOs.DeviceDAO;
import com.server.mothercare.DAOs.UserDAO;
import com.server.mothercare.entities.User;
import com.server.mothercare.entities.kit.*;
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

    @Autowired
    BabyMonitorServiceImpl(DeviceDAO deviceDAO, UserDAO userDAO){
        this.deviceDAO = deviceDAO;
        this.userDAO = userDAO;
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
        }, ()-> new Exception("This device is not found"));
        log.warn(this.deviceUsers.get(id).toString());
    }

    public void disconnectDevice(long id){
        this.deviceUsers.remove(id);
    }

    public int subscribeDevice(String username, SseEmitter emitter){
        int connectedDevice = 0;
        Optional<List<MonitoringDevice>> userDevices = this.userDAO.getUserDevices(username);
        userDevices.ifPresent(userDevicesList -> {
            for (MonitoringDevice device : userDevicesList){
                Long deviceId = device.getDeviceId();
                if(this.deviceUsers.containsKey(deviceId)){
                    this.deviceUsers.get(deviceId).add(new DeviceUsersSse(username ,emitter));
                }
            }

        });
        if (userDevices.isPresent())
        {
            connectedDevice = 1;
        }
        return connectedDevice;
    }

    public void pushNewData(JSONObject json){
        Long id = json.getLong("deviceId");
        TempRead tempRead = new TempRead(json.getDouble("tempReading"), new Date());
        HeartRateRead heartRateRead = new HeartRateRead(json.getDouble("heartRateReading"), new Date());
        SP02Read sp02Read = new SP02Read(json.getDouble("SPO2Reading"), new Date());
        this.deviceDAO.findById(id).ifPresentOrElse(device -> {
            this.deviceUsers.putIfAbsent(id, new ArrayList<>());
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(()->{
                for (DeviceUsersSse deviceUsersSse: this.deviceUsers.get(id)) {
                    try {
                        deviceUsersSse.getEmitters().send(SseEmitter.event().data(tempRead));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            executor.shutdown();
        }, ()-> new Exception("This device is not found"));
    }

    public void addDevice(MonitoringDevice device){
        this.deviceDAO.save(device);
    }

    @Override
    public void addUserDevice(Long id, String username) {
        User theUser = this.userDAO.userbyUserName("boda");
        this.deviceDAO.findById(id).ifPresentOrElse( device -> new Exception("This device is already exist")
                , ()-> {
                    this.deviceDAO.save(new MonitoringDevice(id, theUser));
                });
    }
}
