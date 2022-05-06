package mk.ukim.finki.uiktp.sweet_delivery.service;

import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Item;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Post;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Rating;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    Optional<Recipe> createRecipe(String recipeName, Integer price, List<Item> itemsList, String description,
                                  String img_url);
    Optional<Recipe> updateRecipePost(Long recipeId, Post post);
    Optional<Recipe> deleteRecipe(Long recipeId);
    Optional<Recipe> findById(Long recipeId);
    Optional<Recipe> findByRecipeName(String recipeName);
    List<Recipe> getTopRecipes();
    List<Recipe> getAllRecipes();
    Optional<Recipe> getRecipeByName(String recipeName);
    Optional<Recipe> leaveRating(Long recipeId, Rating rating);
}
