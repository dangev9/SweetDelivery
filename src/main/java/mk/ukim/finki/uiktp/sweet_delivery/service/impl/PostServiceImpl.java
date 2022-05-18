package mk.ukim.finki.uiktp.sweet_delivery.service.impl;

import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.PostNotFoundException;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.UserNotFoundException;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Post;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.PostDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.repository.PostRepository;
import mk.ukim.finki.uiktp.sweet_delivery.repository.RecipeRepository;
import mk.ukim.finki.uiktp.sweet_delivery.repository.UserRepository;
import mk.ukim.finki.uiktp.sweet_delivery.service.PostService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, RecipeRepository recipeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Post createPost(PostDTO postDTO) {

        Recipe recipe = this.recipeRepository.getById(postDTO.getRecipeId());
        User user = this.userRepository.findById(postDTO.getUserId()).orElseThrow(UserNotFoundException::new);
        Post post = new Post();
        if(user != null) {
            OffsetDateTime date_created = OffsetDateTime.now();
            post.setDate_created(date_created);
            post.setRecipe(recipe);
            post.setUser(user);
        }
        return post;
    }

    @Override
    public void deletePost(Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        this.postRepository.deleteById(postId);
    }
}
