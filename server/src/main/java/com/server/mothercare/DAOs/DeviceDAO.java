package com.server.mothercare.DAOs;

import com.server.mothercare.entities.kit.MonitoringDevice;
import org.springframework.data.repository.CrudRepository;

public interface DeviceDAO extends CrudRepository<MonitoringDevice, Long> {
}
