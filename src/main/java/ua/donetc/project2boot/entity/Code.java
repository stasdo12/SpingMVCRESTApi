package ua.donetc.project2boot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@QueryEntity
@Table(name = "codes", uniqueConstraints = {
        @UniqueConstraint(name = "name_unique", columnNames = "name")
})
@Data
@NoArgsConstructor
@NamedEntityGraph(name = "code_entity-graph", attributeNodes = @NamedAttributeNode("priceList"))

public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "code", fetch = FetchType.LAZY)
    private List<Price> priceList;


    @Column(name = "created_at")
    private LocalDateTime createdAt;


}
