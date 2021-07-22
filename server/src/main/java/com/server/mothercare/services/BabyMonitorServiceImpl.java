package com.server.mothercare.services;

import com.server.mothercare.DAOs.DeviceDAO;
import com.server.mothercare.DAOs.UserDAO;
import com.server.mothercare.entities.BabyIssue;
import com.server.mothercare.entities.kit.*;
import com.server.mothercare.models.DeviceUsersSse;
import com.server.mothercare.models.kit.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;


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
    public Hashtable<Long, List<DeviceUsersSse>> getEmitters() {
        return deviceUsers;
    }

    @Override
    @Transactional
    public void connectDevice(long id){
        this.deviceDAO.findById(id).ifPresentOrElse(device -> {
            this.deviceUsers.putIfAbsent(id, new ArrayList<>());
            try {
                log.info("loading...");
                this.notificationService.notifyConnectedDevice(device.getUser().getUserId(), "connected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, ()-> new Exception("This device is not found"));
    }

    @Override
    @Transactional
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
    @Transactional
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
    @Transactional
    public void saveSensorRead(JSONObject json){
        Long id = json.getLong("deviceId");
        this.deviceDAO.findById(id).ifPresentOrElse(device -> {
            this.deviceUsers.putIfAbsent(id, new ArrayList<>());
            var users = this.deviceUsers.get(id);
            if (users.size() > 0){
                var username = users.get(0).getUsername();
                var optionalUser = this.userDAO.getUserbyUserName(username);
                optionalUser.ifPresent(user1 -> {
                    boolean issueFlag = false;
                    var babyIssues = user1.getBabyIssues();
                    TempRead tempRead = new TempRead(json.getDouble("tempRead"), new Date());
                    PositionRead positionRead = new PositionRead(json.getDouble("positionRead"), new Date());
                    HeartRateRead heartRateRead = new HeartRateRead(json.getDouble("heartrateRead"), new Date());
                    SPO2Read spo2Read = new SPO2Read(json.getDouble("spo2Read"), new Date());
                    SensorsReads sensorsReads = new SensorsReads(tempRead, spo2Read, heartRateRead, positionRead);
                    if (36.1 > tempRead.getValue() || tempRead.getValue() > 37.9){
                        var issue = new BabyIssue("Something wrong with temperature",
                                new SensorRead(tempRead.getValue(), tempRead.getTime()), device.getBabyName());
                        babyIssues.add(issue);
                        issueFlag = true;
                    }
                    if (70 > heartRateRead.getValue() || 160 < heartRateRead.getValue()){
                        var issue = new BabyIssue("Something wrong with heart rate",
                                new SensorRead(heartRateRead.getValue(), heartRateRead.getTime()), device.getBabyName());
                        babyIssues.add(issue);
                        issueFlag = true;
                    }
                    if (spo2Read.getValue() < 97){
                        var issue = new BabyIssue("Something wrong with blood oxygen",
                                new SensorRead(spo2Read.getValue(), spo2Read.getTime()), device.getBabyName());
                        babyIssues.add(issue);
                        issueFlag = true;
                    }
                    device.getSpo2Reads().add(spo2Read);
                    device.getHeartRateReads().add(heartRateRead);
                    device.getTempReads().add(tempRead);
                    device.getPositionReads().add(positionRead);
                    deviceDAO.save(device);
                    userDAO.save(user1);
                    if (issueFlag == true){
                        this.notifyWithBabyIssue(user1.getUserId());
                    }
                    this.notifyOnlineUser(id, sensorsReads);
                });
            }
        }, ()-> new Exception("This device is not found"));
    }


    private void notifyWithBabyIssue(Long userId){
        try {
            this.notificationService.notifyConnectedDevice(userId, "baby_issue");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notifyOnlineUser(Long id, SensorsReads sensorsReads){
        for (DeviceUsersSse deviceUsersSse: deviceUsers.get(id)) {
            try {
                deviceUsersSse.getUsername();
                deviceUsersSse.getEmitters().send(SseEmitter.event().data(sensorsReads));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @Transactional
    public void addDevice(MonitoringDevice device){
        this.deviceDAO.save(device);
    }


}
