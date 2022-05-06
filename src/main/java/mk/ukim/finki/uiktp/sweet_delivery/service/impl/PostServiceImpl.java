package mk.ukim.finki.uiktp.sweet_delivery.service.impl;

import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.PostNotFoundException;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Post;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.repository.PostRepository;
import mk.ukim.finki.uiktp.sweet_delivery.service.PostService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Optional<Post> createPost(Recipe recipe, User user) {

        if(recipe!=null && user!=null) {
            OffsetDateTime date_created = OffsetDateTime.now();
            return Optional.of(this.postRepository.save(new Post(recipe, date_created, user)));
        }

        return Optional.empty();
    }

    @Override
    public Optional<Post> deletePost(Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        this.postRepository.deleteById(postId);
        return Optional.of(post);
    }
}
