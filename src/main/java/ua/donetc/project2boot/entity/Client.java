package ua.donetc.project2boot.entity;

import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@QueryEntity
@Data
@NoArgsConstructor
@Table(name = "client")
@NamedEntityGraph(name = "client_entity-graph", attributeNodes = @NamedAttributeNode("emailAddresses"))
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    @Column(name = "full_name")
    private String fullName;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private List<EmailAddress> emailAddresses;

    public Client(String fullName, String mobileNumber, List<EmailAddress> emailAddresses) {
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.emailAddresses = emailAddresses;
    }
}
