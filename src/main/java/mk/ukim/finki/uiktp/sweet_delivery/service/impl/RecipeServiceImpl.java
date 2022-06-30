package mk.ukim.finki.uiktp.sweet_delivery.service.impl;

import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.ItemNotFoundException;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.RecipeAlreadyExistsException;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.RecipeNotFoundException;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.UserNotFoundException;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Item;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Post;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Rating;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.ItemDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.LeaveRatingDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.RecipeCreationDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.RecipePostUpdateDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.repository.*;
import mk.ukim.finki.uiktp.sweet_delivery.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final ItemRepository itemRepository;
    private final PostRepository postRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository, ItemRepository itemRepository, PostRepository postRepository, RatingRepository ratingRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.itemRepository = itemRepository;
        this.postRepository = postRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Recipe createRecipe(RecipeCreationDTO recipeCreationDTO) {

        if(this.recipeRepository.findByName(recipeCreationDTO.getName()).isPresent()) {
            throw new RecipeAlreadyExistsException(recipeCreationDTO.getName());
        }

        Recipe recipe = new Recipe();
        if(recipeCreationDTO.getName()!=null){
            recipe.setName(recipeCreationDTO.getName());
        }
        if(recipeCreationDTO.getImg_url()!=null){
            recipe.setImg_url(recipeCreationDTO.getImg_url());
        }
        if(recipeCreationDTO.getDescription()!=null){
            recipe.setDescription(recipeCreationDTO.getDescription());
        }
        if(recipeCreationDTO.getPrice()!=null){
            recipe.setPrice(recipeCreationDTO.getPrice());
        }
        List<Item> itemList = new ArrayList<>();
        if(recipeCreationDTO.getItemList()!=null && recipeCreationDTO.getItemList().size()>0){
            List<Long> list = recipeCreationDTO.getItemList();
            for(Long l : list){
                Item item = this.itemRepository.getById(l);
                itemList.add(item);
            }
        }else{
            recipe.setItemList(null);
        }

        recipe.setItemList(itemList);
        recipe.setOrdersList(null);
        return this.recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipePost(RecipePostUpdateDTO recipePostUpdateDTO) {
        Recipe recipe = this.recipeRepository.findById(recipePostUpdateDTO.getRecipeId()).orElseThrow(RecipeNotFoundException::new);
        Post post = this.postRepository.getById(recipePostUpdateDTO.getPostId());
        recipe.setPost(post);

        return this.recipeRepository.save(recipe);
    }

    @Override
    public void deleteById(Long recipeId) {
        Recipe recipe = this.recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        this.recipeRepository.deleteById(recipeId);
    }

    @Override
    public Optional<Recipe> findById(Long recipeId) {
        return this.recipeRepository.findById(recipeId);
    }

    @Override
    public Optional<Recipe> findByRecipeName(String recipeName) {
        return this.recipeRepository.findByName(recipeName);
    }

    @Override
    public List<Recipe> getTopRecipes() {
        return this.recipeRepository.findAll().stream()
                .sorted(Comparator.comparing(Recipe::getAverageRating).reversed())
                .limit(5).collect(Collectors.toList());
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return this.recipeRepository.findAll();
    }

    @Override
    public Recipe leaveRating(LeaveRatingDTO leaveRatingDTO) {
        Recipe recipe = this.recipeRepository.findById(leaveRatingDTO.getRecipeId()).orElseThrow(RecipeNotFoundException::new);
        Rating rating = new Rating();
        User user = this.userRepository.findByUsername(leaveRatingDTO.getUsername()).orElseThrow(UserNotFoundException::new);
        rating.setUser(user);
        rating.setRecipe(recipe);
        rating.setRecipeStars(leaveRatingDTO.getRecipeStars());
        this.ratingRepository.save(rating);
        List<Rating> recipeRatings = recipe.getRatings();
        recipeRatings.add(rating);
        recipe.setRatings(recipeRatings);
        this.recipeRepository.save(recipe);
        return recipe;
    }

    @Override
    public List<Recipe> returnApprovedRecipePosts() {
     List<Recipe> allRecipes = this.recipeRepository.findAll();
     List<Recipe> approvedRecipes = new ArrayList<>();

        for (Recipe allRecipe : allRecipes) {
            if(allRecipe.getPost()!=null && allRecipe.getPost().getPostStatus().name().equals("APPROVED")){
                approvedRecipes.add(allRecipe);
            }

        }

        return approvedRecipes;
    }
}
