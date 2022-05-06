package mk.ukim.finki.uiktp.sweet_delivery.service;

import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Order;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> createOrder(Integer amount, String deliveryAddress, List<Recipe> recipeList, User user);
    Optional<Order> cancelOrder(Long orderId);
    List<Order> getAllOrdersByUser(String username);
}
