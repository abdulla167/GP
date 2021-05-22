package com.server.mothercare.services;

import com.server.mothercare.DAOs.DeviceDAO;
import com.server.mothercare.entities.kit.DeviceUsersSse;
import com.server.mothercare.entities.kit.HeartRateRead;
import com.server.mothercare.entities.kit.SP02Read;
import com.server.mothercare.entities.kit.TempRead;
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

    @Autowired
    BabyMonitorServiceImpl(DeviceDAO deviceDAO){
        this.deviceDAO = deviceDAO;
    }

    @Override
    public void sendDataToUsers(JSONObject vitalSignReads) {

    }

    @Override
    public Hashtable<Long, List<DeviceUsersSse>> getEmitters() {
        return deviceUsers;
    }

    public void addConnectedDevice(long id){
        this.deviceDAO.findById(id).ifPresentOrElse(device -> {
            this.deviceUsers.putIfAbsent(id, new ArrayList<>());
        }, ()-> new Exception("This device is not found"));

        log.warn(this.deviceUsers.get(id).toString());
    }

    public void removeConnectedDevice(long id){
        this.deviceUsers.remove(id);
    }

    public void subscribeDevice(Long deviceId, String username, SseEmitter emitter) throws Exception {
        if(this.deviceUsers.containsKey(deviceId)){
            this.deviceUsers.get(deviceId).add(new DeviceUsersSse(username ,emitter));
        }else {
            throw new Exception("Device is not connected");
        }
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
    };
}
