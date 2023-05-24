package ua.donetc.project2boot.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.donetc.project2boot.models.Users;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Integer> {

    Optional<Users> findUsersByUsername(String name);

    List<Users> findByUsernameStartingWith(String username);

    List<Users> findUsersByRole(String role);

}
