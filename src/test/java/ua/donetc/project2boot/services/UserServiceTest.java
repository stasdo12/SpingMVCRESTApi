package ua.donetc.project2boot.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ua.donetc.project2boot.models.Users;
import ua.donetc.project2boot.repositories.UserRepo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@SpringBootTest
@Transactional
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void findAll() {
        List<Users> expectedUsers = Arrays.asList(
                new Users("Stan", "stan@gmail.com"),
                new Users("Stan1", "stan1@gmail.com")
        );
        when(userRepo.findAll()).thenReturn(expectedUsers);

        List<Users> actualUsers = userService.findAll();
        assertEquals(expectedUsers, actualUsers);

    }

    @Test
    @WithMockUser
    void findOneUser() {
        Users users = new Users("Bob", "bob@gmail.com");
        int id = users.getId();
        when(userRepo.findById(id)).thenReturn(Optional.of(users));

        Users actualUser = userService.findOneUser(id);
        assertEquals(users, actualUser);
    }

    @Test
    void saveUser() {
        Users users = new Users();
        users.setUsername("Bob");
        users.setPassword("testPass");
        when(passwordEncoder.encode("testPass")).thenReturn("encodePass");

        userService.saveUser(users);

        verify(passwordEncoder).encode("testPass");
        verify(userRepo).save(users);

        assertEquals("encodePass", users.getPassword());
        assertEquals("ROLE_USER", users.getRole());

    }

    @Test
    void deleteUser() {
       int userId =1;
       userService.deleteUser(userId);
       verify(userRepo).deleteById(userId);

    }

    @Test
    void updateUser() {

        Users users = new Users("Bob", "bob@gmail.com");
        int userID = users.getId();
        userService.updateUser(userID, users);
        verify(userRepo).save(users);

    }

    @Test
    void getUserByFullName() {
        Users users = new Users("Bob", "bob@gamil.com");
        String userName = users.getUsername();
        when(userRepo.findUsersByUsername(userName)).thenReturn(Optional.of(users));

        Users actualUsers = userService.getUserByFullName(userName).orElse(null);
        assertEquals(users, actualUsers);
    }

    @Test
    void findUsersByRole() {
       List<Users>  users = Arrays.asList(
               new Users("Stan", "stan@gmail.com"),
               new Users("Stan1", "stan1@gmail.com")
       );
        String userRole = users.get(1).getRole();
        when(userRepo.findUsersByRole(userRole)).thenReturn(users);

        List<Users> actualUser = userService.findUsersByRole(userRole);
        assertEquals(users, actualUser);
    }
}