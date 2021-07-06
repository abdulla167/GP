package com.server.mothercare.services;

import com.server.mothercare.entities.kit.DeviceUsersSse;
import com.server.mothercare.entities.kit.MonitoringDevice;
import org.json.JSONObject;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Hashtable;
import java.util.List;

public interface BabyMonitorService {
    public void sendDataToUsers(JSONObject vitalSignReads);

    public Hashtable<Long, List<DeviceUsersSse>> getEmitters();

    public void connectDevice(long id);

    public void disconnectDevice(long id);

    public int subscribeDevice(String username, SseEmitter emitter);

    public void pushNewData(JSONObject json);

    public void addUserDevice(Long id, String username);

    public void addDevice(MonitoringDevice device);

}
