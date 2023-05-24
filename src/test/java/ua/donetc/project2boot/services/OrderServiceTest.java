package ua.donetc.project2boot.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ua.donetc.project2boot.models.Order;
import ua.donetc.project2boot.models.Users;
import ua.donetc.project2boot.repositories.OrderRepo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@SpringBootTest
@Transactional
class OrderServiceTest {


    @Mock
    private OrderRepo orderRepo;

    @InjectMocks
    private OrderService orderService;


    @Test
    @WithMockUser
    void findAll() {
        List<Order> expectedOrders = Arrays.asList(
                new Order(),
                new Order()
        );
        when(orderRepo.findAll()).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.findAll();
        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    @WithMockUser
    void findOrderById() {
        Order order = new Order();
        int id = order.getId();
        when(orderRepo.findById(id)).thenReturn(Optional.of(order));
        Order actualOrder = orderService.findOrderById(id);
        assertEquals(order, actualOrder);
    }


    @Test
    public void testGetOrderByUserId() {
        Users user1 = new Users("John", "john@example.com");
        Order order = new Order();
        order.setUsers(user1);
        when(orderRepo.findOrderByUsersId(user1.getId())).thenReturn(order);
    }
}