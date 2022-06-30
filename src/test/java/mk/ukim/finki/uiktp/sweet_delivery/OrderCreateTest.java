package mk.ukim.finki.uiktp.sweet_delivery;

import mk.ukim.finki.uiktp.sweet_delivery.model.enums.ItemCategory;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.OrderException;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Item;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Order;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.OrderDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.OrderRecipeDTO;
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

@RunWith(MockitoJUnitRunner.class)
public class OrderCreateTest {

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
        Item item= new Item(5, "Item Name", ItemCategory.FRUITS, 100, "url");
        List<Item> items=new ArrayList<>();
        items.add(item);
        Recipe recipe = new Recipe("name", 250, items, "description", "img_url");
        List<Recipe> recipes=new ArrayList<>();
        recipes.add(recipe);
        User user=new User("username", "firstName", "lastName", "email", "address", "password", OffsetDateTime.now());
        user.setId(4L);
        Order order= new Order(OffsetDateTime.now(),2500,"address", recipes, user);
        order.setId(123L);
        Mockito.when(this.orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        this.service = Mockito.spy(new OrderServiceImpl( this.orderRepository, this.userRepository, this.recipeRepository ));
    }

    @Test
    public void testSuccessCreate(){
        Item item= new Item(5, "Item Name", ItemCategory.FRUITS, 100, "url");
        List<Item> items=new ArrayList<>();
        items.add(item);
        Recipe recipe = new Recipe("name",250,items, "description","img_url");
        recipe.setId(5588L);
        OrderRecipeDTO orderRecipeDTO= new OrderRecipeDTO(5588L, 5);
        User user=new User("username", "firstName", "lastName", "email", "address", "password", OffsetDateTime.now());
        user.setId(4L);
        List<OrderRecipeDTO> orderRecipeDTOS=new ArrayList<>();
        orderRecipeDTOS.add(orderRecipeDTO);

        Mockito.when(this.userRepository.getById(Mockito.any())).thenReturn(user);

        OrderDTO orderDTO=new OrderDTO(2500, "address", orderRecipeDTOS, user.getId());

        Order order= this.service.createOrder(orderDTO);

        Mockito.verify(this.service).createOrder(orderDTO);

        Assert.assertNotNull("Order is null", order);
        Assert.assertEquals("amount do not match", Integer.valueOf(2500), order.getAmount());
        Assert.assertEquals("address do not match", "address", order.getDeliveryAddress());
        Assert.assertEquals("order recipe size not match", 1, order.getRecipeList().size());
        Assert.assertEquals("user id not match", Long.valueOf(4L), order.getUser().getId());

    }

    @Test
    public void testUserIdNull() {

        Item item= new Item(5, "Item Name", ItemCategory.FRUITS, 100, "url");
        List<Item> items=new ArrayList<>();
        items.add(item);
        Recipe recipe = new Recipe("name",250,items, "description","img_url");
        recipe.setId(5588L);
        OrderRecipeDTO orderRecipeDTO= new OrderRecipeDTO(5588L, 5);
        List<OrderRecipeDTO> orderRecipeDTOS=new ArrayList<>();
        orderRecipeDTOS.add(orderRecipeDTO);

        OrderDTO orderDTO=new OrderDTO(2500, "address", orderRecipeDTOS, null);

        Assert.assertThrows("OrderException expected",
                OrderException.class,
                () -> this.service.createOrder(orderDTO));
        Mockito.verify(this.service).createOrder(orderDTO);
    }

    @Test
    public void testOrderContentIsEmpty() {
        Item item= new Item(5, "Item Name", ItemCategory.FRUITS, 100, "url");
        List<Item> items=new ArrayList<>();
        items.add(item);
        Recipe recipe = new Recipe("name",250,items, "description","img_url");
        recipe.setId(5588L);
        User user=new User("username", "firstName", "lastName", "email", "address", "password", OffsetDateTime.now());
        user.setId(4L);
        List<OrderRecipeDTO> orderRecipeDTOS=new ArrayList<>();

        OrderDTO orderDTO=new OrderDTO(2500, "address", orderRecipeDTOS, user.getId());

        Assert.assertThrows("OrderException expected",
                OrderException.class,
                () -> this.service.createOrder(orderDTO));
        Mockito.verify(this.service).createOrder(orderDTO);
    }

    @Test
    public void testAmountLessThanZero() {

        Item item= new Item(5, "Item Name", ItemCategory.FRUITS, 100, "url");
        List<Item> items=new ArrayList<>();
        items.add(item);
        Recipe recipe = new Recipe("name",250,items, "description","img_url");
        recipe.setId(5588L);
        OrderRecipeDTO orderRecipeDTO= new OrderRecipeDTO(5588L, 5);
        List<OrderRecipeDTO> orderRecipeDTOS=new ArrayList<>();
        orderRecipeDTOS.add(orderRecipeDTO);
        User user=new User("username", "firstName", "lastName", "email", "address", "password", OffsetDateTime.now());
        user.setId(4L);

        OrderDTO orderDTO=new OrderDTO(-5, "address", orderRecipeDTOS, user.getId());

        Assert.assertThrows("OrderException expected",
                OrderException.class,
                () -> this.service.createOrder(orderDTO));
        Mockito.verify(this.service).createOrder(orderDTO);
    }

}
