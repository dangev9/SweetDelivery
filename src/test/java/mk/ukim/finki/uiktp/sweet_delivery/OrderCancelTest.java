package mk.ukim.finki.uiktp.sweet_delivery;

import mk.ukim.finki.uiktp.sweet_delivery.model.enums.ItemCategory;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.OrderException;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Item;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Order;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.repository.OrderRepository;
import mk.ukim.finki.uiktp.sweet_delivery.repository.RecipeRepository;
import mk.ukim.finki.uiktp.sweet_delivery.repository.UserRepository;
import mk.ukim.finki.uiktp.sweet_delivery.service.OrderService;
import mk.ukim.finki.uiktp.sweet_delivery.service.impl.OrderServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class OrderCancelTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    private OrderService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        this.service = Mockito.spy(new OrderServiceImpl(this.orderRepository, this.userRepository, this.recipeRepository));
    }

    @Test
    public void testSuccessCreate() {
        Item item = new Item(5, "Item Name", ItemCategory.FRUITS, 100, "url");
        List<Item> items = new ArrayList<>();
        items.add(item);
        Recipe recipe = new Recipe("name", 250, items, "description", "img_url");
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        User user = new User("username", "firstName", "lastName", "email", "address", "password", OffsetDateTime.now());
        user.setId(4L);
        Order order = new Order(OffsetDateTime.now(), 2500, "address", recipes, user);
        order.setId(123L);

        Mockito.when(this.orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));

        Optional<Order> order1 = this.service.cancelOrder(123L);

        Mockito.verify(this.service).cancelOrder(123L);

        Assert.assertNotNull("Order is null", order1);
        Assert.assertEquals("order list size do not match", 0, this.orderRepository.count());
        Assert.assertEquals("name do not match", Integer.valueOf(2500), order1.get().getAmount());
    }

    @Test
    public void testOrderIdNull() {
        Assert.assertThrows("OrderException expected",
                OrderException.class,
                () -> this.service.cancelOrder(null));
        Mockito.verify(this.service).cancelOrder(null);
    }
}