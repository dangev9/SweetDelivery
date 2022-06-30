package mk.ukim.finki.uiktp.sweet_delivery.service;

import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Item;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Post;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Rating;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.LeaveRatingDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.RecipeCreationDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.RecipePostUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    Recipe createRecipe(RecipeCreationDTO recipeCreationDTO);
    Recipe updateRecipePost(RecipePostUpdateDTO recipePostUpdateDTO);
    void deleteById(Long recipeId);
    Optional<Recipe> findById(Long recipeId);
    Optional<Recipe> findByRecipeName(String recipeName);
    List<Recipe> getTopRecipes();
    List<Recipe> getAllRecipes();
    Recipe leaveRating(LeaveRatingDTO leaveRatingDTO);
    List<Recipe> returnApprovedRecipePosts();
}
