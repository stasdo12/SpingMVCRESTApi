package ua.donetc.project2boot.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Data
@EqualsAndHashCode
@ToString
public class PriceId implements Serializable {

    private long code;
    private LocalDateTime time;

}
