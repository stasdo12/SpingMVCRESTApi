package ua.donetc.project2boot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@QueryEntity
@Table(name = "prices")
@Data
@IdClass(PriceId.class)
public class Price {
    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "code_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "code_key"))
    @JsonIgnore
    private Code code;


    @Id
    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "val", precision = 10, scale = 7)
    private BigInteger val;
}
