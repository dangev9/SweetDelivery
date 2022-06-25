package mk.ukim.finki.uiktp.sweet_delivery.service.impl;

import mk.ukim.finki.uiktp.sweet_delivery.model.enums.PostStatus;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.PostNotFoundException;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.RecipeNotFoundException;
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
import java.util.List;
import java.util.stream.Collectors;

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

        Recipe recipe = this.recipeRepository.findById(postDTO.getRecipeId()).orElseThrow(RecipeNotFoundException::new);
        User user = this.userRepository.findByUsername(postDTO.getUserName()).orElseThrow(UserNotFoundException::new);
        Post post = new Post();
        if(user != null) {
            OffsetDateTime date_created = OffsetDateTime.now();
            post.setDate_created(date_created);
            post.setRecipe(recipe);
            post.setUser(user);
            post.setPostStatus(PostStatus.PENDING);
        }
        return post;
    }

    @Override
    public void deletePost(Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        this.postRepository.deleteById(postId);
    }

    @Override
    public List<Post> getAllPosts() {
        // Return all Posts (pending/approved/denied)
        return this.postRepository.findAll();
    }

    @Override
    public List<Post> getAllApprovedPosts() {
        // Return only APPROVED Posts
        return this.postRepository.findAll()
                .stream().filter(x-> x.getPostStatus().equals(PostStatus.APPROVED))
                .collect(Collectors.toList());
    }

    @Override
    public void approvePost(Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        post.setPostStatus(PostStatus.APPROVED);
        this.postRepository.save(post);
    }

    @Override
    public void declinePost(Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        post.setPostStatus(PostStatus.DECLINED);
        this.postRepository.save(post);
    }
}
