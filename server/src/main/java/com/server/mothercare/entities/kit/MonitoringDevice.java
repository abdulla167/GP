package com.server.mothercare.entities.kit;
import com.server.mothercare.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "monitoring_device")
@NoArgsConstructor
public class MonitoringDevice {

    @Id
    private Long deviceId;


    @ElementCollection
    @CollectionTable(
            name = "temperature_reads",
            joinColumns = @JoinColumn(name = "deviceId")
    )
    private List<TempRead> tempReads;

    @ElementCollection
    @CollectionTable(
            name = "heartrate_reads",
            joinColumns = @JoinColumn(name = "deviceId")
    )
    private List<HeartRateRead> heartRateReads;

    @ElementCollection
    @CollectionTable(
            name = "spo2_reads",
            joinColumns = @JoinColumn(name = "deviceId")
    )
    private List<SP02Read> spo2Reads;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable=false)
    private User user;
}
