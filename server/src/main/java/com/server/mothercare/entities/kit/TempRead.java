package com.server.mothercare.entities.kit;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.sql.Date;


@Embeddable
@Data
@NoArgsConstructor
public class TempRead {
    private float value;
    private Date time;
}
