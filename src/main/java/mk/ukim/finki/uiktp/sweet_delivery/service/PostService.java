package mk.ukim.finki.uiktp.sweet_delivery.service;

import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Post;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.PostDTO;

import java.util.List;

public interface PostService {
    Post createPost(PostDTO postDTO);
    void deletePost(Long postId);
    void approvePost(Long postId);
    void declinePost(Long postId);
    List<Post> getAllPosts();
    List<Post> getAllApprovedPosts();
}
