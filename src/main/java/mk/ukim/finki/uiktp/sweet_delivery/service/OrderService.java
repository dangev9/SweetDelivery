package mk.ukim.finki.uiktp.sweet_delivery.service;

import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Order;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.OrderDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO);
    Optional<Order> cancelOrder(Long orderId);
    List<Order> getAllOrdersByUser(String username);
}
