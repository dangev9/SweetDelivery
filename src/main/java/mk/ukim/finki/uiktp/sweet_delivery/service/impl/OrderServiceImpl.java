package mk.ukim.finki.uiktp.sweet_delivery.service.impl;

import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.OrderException;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.UserNotFoundException;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Order;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.repository.OrderRepository;
import mk.ukim.finki.uiktp.sweet_delivery.repository.UserRepository;
import mk.ukim.finki.uiktp.sweet_delivery.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Order> createOrder(Integer amount, String deliveryAddress, List<Recipe> recipeList, User user) {

        if(recipeList.isEmpty() || amount < 0 || user == null) {
            throw new OrderException();
        }
        OffsetDateTime dateCreated = OffsetDateTime.now();
        return Optional.of(this.orderRepository.save(new Order(dateCreated, amount, deliveryAddress, recipeList, user)));
    }

    @Override
    public Optional<Order> cancelOrder(Long orderId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(OrderException::new);
        this.orderRepository.deleteById(orderId);
        return Optional.of(order);
    }

    @Override
    public List<Order> getAllOrdersByUser(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return user.getOrders();
    }
}
