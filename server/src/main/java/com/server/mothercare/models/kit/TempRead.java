package com.server.mothercare.models.kit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Date;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TempRead {
    private double value;
    private Date time;
}
