package mk.ukim.finki.uiktp.sweet_delivery;


import mk.ukim.finki.uiktp.sweet_delivery.model.enums.ItemCategory;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Item;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.LeaveRatingDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.RecipeCreationDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.repository.*;
import mk.ukim.finki.uiktp.sweet_delivery.service.RecipeService;
import mk.ukim.finki.uiktp.sweet_delivery.service.impl.RecipeServiceImpl;
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
public class LeaveRatingTest {
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private UserRepository userRepository;

    private RecipeService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Item item= new Item(5, "Item Name", ItemCategory.FRUITS, 100, "url");
        item.setId(2565L);
        List<Item> items=new ArrayList<>();
        items.add(item);
        Recipe recipe = new Recipe("name", 250, items, "description", "img_url");
        recipe.setId(5588L);

        Mockito.when(this.recipeRepository.save(Mockito.any(Recipe.class))).thenReturn(recipe);

        this.service = Mockito.spy(new RecipeServiceImpl(this.recipeRepository,this.itemRepository,this.postRepository,this.ratingRepository, this.userRepository));
    }

    @Test
    public void testSuccessLeaveRating(){
        List<Long> items=new ArrayList<>();
        items.add(2565L);
        RecipeCreationDTO recipeDTO= new RecipeCreationDTO(250,"description","name", "img_url", items);
        Recipe recipe = this.service.createRecipe(recipeDTO);
        User user=new User("username", "firstName", "lastName", "email", "address", "password", OffsetDateTime.now());

        LeaveRatingDTO leaveRatingDTO= new LeaveRatingDTO(5588L, user.getUsername(),4);

        Mockito.when(this.recipeRepository.findById(Mockito.any())).thenReturn(Optional.of(recipe));
        Mockito.when(this.userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));

        Recipe recipe2=this.service.leaveRating(leaveRatingDTO);
        Mockito.verify(this.service).leaveRating(leaveRatingDTO);

        Assert.assertNotNull("Rating is null", leaveRatingDTO);
        Assert.assertEquals("recipe ratings size do not match", 1, recipe2.getRatings().size());
        Assert.assertEquals("username do not match", "username", recipe2.getRatings().get(0).getUser().getUsername());
        Assert.assertEquals("recipe do not match", Long.valueOf(5588L), recipe2.getRatings().get(0).getRecipe().getId());
        Assert.assertEquals("number of stars do not match", Integer.valueOf(4), recipe2.getRatings().get(0).getRecipeStars());

    }

}

