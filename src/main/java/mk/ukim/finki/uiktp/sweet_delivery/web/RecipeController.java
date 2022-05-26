package mk.ukim.finki.uiktp.sweet_delivery.web;


import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Post;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Rating;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.RecipeCreationDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.RecipePostUpdateDTO;
import mk.ukim.finki.uiktp.sweet_delivery.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/recipe")
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeController {

    private final RecipeService recipeService;


    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/create")
    public Recipe createRecipe(@RequestBody RecipeCreationDTO recipeCreationDTO){
        return this.recipeService.createRecipe(recipeCreationDTO);
    }

    @PutMapping("/updateRecipePost")
    public Recipe updateRecipePost(@RequestBody RecipePostUpdateDTO recipePostUpdateDTO){
        return this.recipeService.updateRecipePost(recipePostUpdateDTO);
    }

    @DeleteMapping("delete/{recipeId}")
    public void deleteById(@PathVariable Long recipeId){
        this.recipeService.deleteById(recipeId);
    }

    @GetMapping(value = "getById/{id}")
    public Optional<Recipe> getById(@PathVariable Long id){
        return this.recipeService.findById(id);
    }

    @GetMapping("/getTopRecipes")
    public List<Recipe> getTopRecipes(){
        return this.recipeService.getTopRecipes();
    }

    @GetMapping("/findAll")
    public List<Recipe> allRecipes(){
        return this.recipeService.getAllRecipes();
    }

    @PostMapping("/leaveRating/{recipeId}")
    public Optional<Recipe> leaveRating(@PathVariable Long recipeId, Rating rating){
          return this.recipeService.leaveRating(recipeId,rating);
    }


}
