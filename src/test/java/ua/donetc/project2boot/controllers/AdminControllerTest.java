package ua.donetc.project2boot.controllers;

import com.binance.api.client.BinanceApiRestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.donetc.project2boot.models.Users;
import ua.donetc.project2boot.repositories.UserRepo;
import ua.donetc.project2boot.services.OrderService;
import ua.donetc.project2boot.services.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private BinanceApiRestClient binanceAPI;

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;


    @MockBean
    private UserRepo userRepository;

    @Test
    @WithMockUser
    void apiPage() throws Exception {
        mockMvc.perform(get("/admin/api-resources"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/api-page"));
    }

    @Test
    @WithMockUser
    void adminPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/index"));
    }

    @Test
    @WithMockUser
    void show() throws Exception {
        List<Users> users = Arrays.asList(
                new Users("Bob", "qq@gmail.com"),
                new Users("Bob1", "qq1@gmail.com"),
                new Users("Bob2", "qq2@gmail.com")
        );
        when(userService.findUsersByRole("ROLE_USER")).thenReturn(users);

        mockMvc.perform(get("/admin/show-all-users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/show-all-users"))
                .andExpect(model().attribute("users", users));
    }

    @Test
    @WithMockUser
    void showUserInfo() throws Exception {
        int userId = 1;
        Users user = new Users();
        user.setId(userId);
        user.setUsername("John Doe");

        when(userService.findOneUser(userId)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/show-all-users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/show-user-info"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user));

        verify(userService).findOneUser(userId);

    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void delete() throws Exception {
        int userId = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/show-all-users/{id}", userId).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/show-all-users"));

        verify(userService).deleteUser(userId);



    }

    @Test
    @WithMockUser
    void editUser() throws Exception {
        int userId = 1;
        Users user = new Users();
        user.setId(userId);
        user.setUsername("John Doe");

        when(userService.findOneUser(userId)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/show-all-users/{id}/edit", userId))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/edit-user"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user));

        verify(userService).findOneUser(userId);
    }

    @Test
    @WithMockUser
    public void testUpdate() throws Exception {
        // Create a test user
        Users user = new Users();
        user.setUsername("Bob");
        user.setEmail("test@example.com");
        user.setPassword("testPass");
        user.setRole("ROLE_USER");

        // Prepare the mock service behavior
        doNothing().when(userService).updateUser(any(Integer.class), any(Users.class));

        // Perform the PATCH request
        mockMvc.perform(MockMvcRequestBuilders.patch("/show-all-users/1").with(csrf())
                        .flashAttr("user", user))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/show-all-users"));

        // Verify that the userService.updateUser method was called with the expected arguments
        verify(userService).updateUser(1, user);

        //TODO finish this test

    }



    @Test
    @WithMockUser
    void planetPage() throws Exception {
        mockMvc.perform(get("/admin/planet"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/loading-page"));
    }
}