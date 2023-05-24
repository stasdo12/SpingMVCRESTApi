package ua.donetc.project2boot.models;

import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@QueryEntity
@Data
@Table(name = "Users")
public class Users  {

    //Default constructor
    public Users() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name should be not empty")
    @Size(min = 3, max = 25, message = "You name should be between 3 and 25 symbols")
    private String username;


    @NotEmpty(message = "E-mail should be not empty")
    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "password_hash")
    private String password;

    @Column(name = "role")
    private String role;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private final List<Order> orderList = new ArrayList<>();


    public Users(String username, String email) {
        this.username = username;
        this.email = email;

    }


}
