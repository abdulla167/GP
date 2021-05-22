package com.server.mothercare.services;

import com.server.mothercare.entities.kit.DeviceUsersSse;
import org.json.JSONObject;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Hashtable;
import java.util.List;

public interface BabyMonitorService {
    public void sendDataToUsers(JSONObject vitalSignReads);

    public Hashtable<Long, List<DeviceUsersSse>> getEmitters();

    public void addConnectedDevice(long id);

    public void removeConnectedDevice(long id);

    public void subscribeDevice(Long deviceId,String username, SseEmitter emitter) throws Exception;

    public void pushNewData(JSONObject json);

}
