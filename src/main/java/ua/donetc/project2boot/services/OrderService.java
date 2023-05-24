package ua.donetc.project2boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.donetc.project2boot.models.Order;
import ua.donetc.project2boot.repositories.OrderRepo;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }


    public List<Order> findAll(){
        return orderRepo.findAll();
    }

    public Order findOrderById(int id){
        return orderRepo.findById(id).orElse(null);
    }

    public Order getOrderByUserId(int id){
        return orderRepo.findOrderByUsersId(id);
    }



}
