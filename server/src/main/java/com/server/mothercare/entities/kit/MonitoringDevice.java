package com.server.mothercare.entities.kit;
import com.server.mothercare.entities.User;

import com.server.mothercare.models.kit.HeartRateRead;
import com.server.mothercare.models.kit.SPO2Read;
import com.server.mothercare.models.kit.TempRead;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "monitoring_device")
@NoArgsConstructor
public class MonitoringDevice {

    @Id
    private Long deviceId;

    @Column(name = "baby_name")
    private String babyName;

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
    private List<SPO2Read> spo2Reads;


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    public MonitoringDevice(Long id, User deviceUser){
        deviceId = id;
        user = deviceUser;
    }

    public MonitoringDevice(Long id){
        deviceId = id;
    }
}
