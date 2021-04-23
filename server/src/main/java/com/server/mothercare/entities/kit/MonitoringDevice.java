package com.server.mothercare.entities.kit;
import com.server.mothercare.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class MonitoringDevice {
    @Id
    private long deviceId;

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
