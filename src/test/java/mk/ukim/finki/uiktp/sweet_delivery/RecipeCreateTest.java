package mk.ukim.finki.uiktp.sweet_delivery;


import mk.ukim.finki.uiktp.sweet_delivery.model.enums.ItemCategory;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.RecipeAlreadyExistsException;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Item;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.RecipeCreationDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RecipeCreateTest {
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
        List<Item> items=new ArrayList<>();
        items.add(item);
        Recipe recipe = new Recipe("name", 250, items, "description", "img_url");


        Mockito.when(this.recipeRepository.save(Mockito.any(Recipe.class))).thenReturn(recipe);

        this.service = Mockito.spy(new RecipeServiceImpl(this.recipeRepository,this.itemRepository,this.postRepository,this.ratingRepository, this.userRepository));
    }

    @Test
    public void testSuccessCreate(){
        List<Long> items=new ArrayList<>();
        items.add(2565L);
        RecipeCreationDTO recipeDTO= new RecipeCreationDTO(250,"description","name", "img_url", items);
        Recipe recipe = this.service.createRecipe(recipeDTO);

        Mockito.verify(this.service).createRecipe(recipeDTO);

        Assert.assertNotNull("Recipe is null", recipeDTO);
        Assert.assertEquals("name do not match", "name", recipe.getName());
        Assert.assertEquals("price do not match", Integer.valueOf(250), recipe.getPrice());
        Assert.assertEquals("description do not match", "description", recipe.getDescription());
        Assert.assertEquals("img_url do not match", "img_url", recipe.getImg_url());
        Assert.assertEquals("item_list size not match", 1, recipe.getItemList().size());

    }
    @Test
    public void testDuplicateRecipe() {
        List<Long> items=new ArrayList<>();
        items.add(2565L);
        RecipeCreationDTO recipeDTO= new RecipeCreationDTO(250,"description","name", "img_url", items);
        Recipe recipe = new Recipe("name",250,null,"description", "url");
        Mockito.when(this.recipeRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(recipe));
        Assert.assertThrows("RecipeAlreadyExistsException expected",
                RecipeAlreadyExistsException.class,
                () -> this.service.createRecipe(recipeDTO));
        Mockito.verify(this.service).createRecipe(recipeDTO);
    }

}
