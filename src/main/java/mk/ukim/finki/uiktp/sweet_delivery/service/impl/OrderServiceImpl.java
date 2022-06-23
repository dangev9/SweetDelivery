package mk.ukim.finki.uiktp.sweet_delivery.service.impl;

import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.OrderException;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.UserNotFoundException;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Order;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.OrderDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.repository.OrderRepository;
import mk.ukim.finki.uiktp.sweet_delivery.repository.RecipeRepository;
import mk.ukim.finki.uiktp.sweet_delivery.repository.UserRepository;
import mk.ukim.finki.uiktp.sweet_delivery.service.OrderService;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, RecipeRepository recipeRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Order createOrder(OrderDTO orderDTO) {

        if(orderDTO.getOrderContent().isEmpty() || orderDTO.getAmount() < 0 || orderDTO.getUserId() == null) {
            throw new OrderException();
        }
        OffsetDateTime dateCreated = OffsetDateTime.now();
        Order order = new Order();
        order.setOrderDate(dateCreated);
        order.setAmount(orderDTO.getAmount());
        order.setDeliveryAddress(orderDTO.getDeliveryAddress());
        User user = this.userRepository.getById(orderDTO.getUserId());
        order.setUser(user);
        List<Recipe> recipeList = new ArrayList<>();
        List<Integer> recipeQuantities = new ArrayList<>();

        orderDTO.getOrderContent().forEach(x -> {
            Recipe recipe = this.recipeRepository.getById(x.getRecipeId());
            recipeList.add(recipe);
            recipeQuantities.add(x.getRecipeQuantity());
        });

        order.setRecipeList(recipeList);
        order.setRecipesQuantity(recipeQuantities);
        this.orderRepository.save(order);
        return order;
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
