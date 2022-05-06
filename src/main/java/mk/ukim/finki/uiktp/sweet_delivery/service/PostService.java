package mk.ukim.finki.uiktp.sweet_delivery.service;

import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Post;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;

import java.util.Optional;

public interface PostService {
    Optional<Post> createPost(Recipe recipe, User user);
    Optional<Post> deletePost(Long postId);
}
