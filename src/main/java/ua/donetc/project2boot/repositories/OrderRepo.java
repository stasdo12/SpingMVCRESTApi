package ua.donetc.project2boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.donetc.project2boot.models.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> {

    Order findOrderByUsersId(int id);

}
