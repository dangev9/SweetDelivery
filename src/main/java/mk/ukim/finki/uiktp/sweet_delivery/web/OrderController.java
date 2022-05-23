package mk.ukim.finki.uiktp.sweet_delivery.web;

import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Order;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /*
    @PostMapping("/create")
    public Order createOrder(@RequestBody OrderDTO orderDTO){
        return this.orderService.createOrder(orderDTO);
    }
     */

    @PostMapping("/create")
    public Optional<Order> createOrder(@RequestParam Integer amount, @RequestParam String deliveryAddress, @RequestParam List<Recipe> recipeList, @RequestParam User user)
    {
        return this.orderService.createOrder(amount, deliveryAddress, recipeList, user);
    }

    @DeleteMapping("/delete/{orderId}")
    public void cancelOrder(@PathVariable Long orderId)
    {
        this.orderService.cancelOrder(orderId);
    }

    @GetMapping("/all")
    public List<Order> getAllOrdersByUser(String username)
    {
        return this.orderService.getAllOrdersByUser(username);
    }
}
