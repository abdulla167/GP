package com.server.mothercare.entities;

import com.server.mothercare.models.kit.SensorRead;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Date;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BabyIssue {
    @Column(name = "description")
    private String description;

    @Column(name = "sensor_read")
    private SensorRead sensorRead;

    @Column(name = "baby_name")
    private String babyName;
}
