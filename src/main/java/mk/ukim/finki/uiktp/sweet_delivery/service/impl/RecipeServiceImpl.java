package mk.ukim.finki.uiktp.sweet_delivery.service.impl;

import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.RecipeAlreadyExistsException;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.RecipeNotFoundException;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Item;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Post;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Rating;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.repository.RecipeRepository;
import mk.ukim.finki.uiktp.sweet_delivery.service.RecipeService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Optional<Recipe> createRecipe(String recipeName, Integer price, List<Item> itemsList, String description,
                                         String img_url) {

        if(this.recipeRepository.findByName(recipeName).isPresent()) {
            throw new RecipeAlreadyExistsException(recipeName);
        }

        return Optional.of(this.recipeRepository.save(new Recipe(recipeName, price, itemsList, description, img_url)));
    }

    @Override
    public Optional<Recipe> updateRecipePost(Long recipeId, Post post) {
        Recipe recipe = this.recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);

        recipe.setPost(post);

        return Optional.of(this.recipeRepository.save(recipe));
    }

    @Override
    public Optional<Recipe> deleteRecipe(Long recipeId) {
        Recipe recipe = this.recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        this.recipeRepository.deleteById(recipeId);
        return Optional.of(recipe);
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
                .sorted(Comparator.comparing(x -> x.getRating().getRecipeStars()))
                .limit(5).collect(Collectors.toList());
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return this.recipeRepository.findAll();
    }

    @Override
    public Optional<Recipe> getRecipeByName(String recipeName) {
        return this.recipeRepository.findByName(recipeName);
    }

    @Override
    public Optional<Recipe> leaveRating(Long recipeId, Rating rating) {
        // TODO: Change this, recipe must have decimal rating
        Recipe recipe = this.recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        recipe.setRating(rating);
        this.recipeRepository.save(recipe);
        return Optional.of(recipe);
    }
}
