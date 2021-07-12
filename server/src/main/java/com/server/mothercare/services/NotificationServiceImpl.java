package com.server.mothercare.services;

import com.server.mothercare.entities.User;
import com.server.mothercare.entities.kit.MonitoringDevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService{
    Hashtable<Long, SseEmitter> notificationList = new Hashtable<>();

    @Override
    public void notifyConnectedDevice(Long id, String message) throws IOException {
        log.error("send message to user with id : " + id);

        if (this.notificationList.containsKey(id)){
            log.error("send message to user with id : " + id);
            this.notificationList.get(id).send(message);
        }
    }

    @Override
    public void removeUser(Long userId){
        this.notificationList.remove(userId);
    }

    @Override
    public void addUser(Long userId, SseEmitter sseEmitter){
        this.notificationList.putIfAbsent(userId, sseEmitter);
        log.error("" + userId);
    }
}
