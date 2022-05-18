package mk.ukim.finki.uiktp.sweet_delivery.service;

import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Post;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.PostDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;

import java.util.Optional;

public interface PostService {
    Post createPost(PostDTO postDTO);
    void deletePost(Long postId);
}
