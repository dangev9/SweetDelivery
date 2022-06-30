package mk.ukim.finki.uiktp.sweet_delivery;

import mk.ukim.finki.uiktp.sweet_delivery.model.enums.ItemCategory;
import mk.ukim.finki.uiktp.sweet_delivery.model.enums.PostStatus;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Item;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Post;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.PostDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.repository.PostRepository;
import mk.ukim.finki.uiktp.sweet_delivery.repository.RecipeRepository;
import mk.ukim.finki.uiktp.sweet_delivery.repository.UserRepository;
import mk.ukim.finki.uiktp.sweet_delivery.service.PostService;
import mk.ukim.finki.uiktp.sweet_delivery.service.impl.PostServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PostTest {
    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    private PostStatus postStatus;
    private PostService service;


    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);
        Item item = new Item(5, "Item Name", ItemCategory.FRUITS, 100, "url");
        List<Item> items = new ArrayList<>();
        items.add(item);
        Recipe recipe = new Recipe("name", 250, items, "description", "img_url");
        User user = new User("username", "firstName", "lastName", "email", "address", "password", OffsetDateTime.now());
        Post post = new Post(recipe, OffsetDateTime.now(), user);

        Mockito.when(this.postRepository.save(Mockito.any(Post.class))).thenReturn(post);

        this.service = Mockito.spy(new PostServiceImpl(this.postRepository, this.userRepository, this.recipeRepository));
    }


    @Test
    public void testSuccessCreate(){

        Item item= new Item(5, "Item Name", ItemCategory.FRUITS, 100, "url");
        List<Item> items=new ArrayList<>();
        items.add(item);
        Recipe recipe = new Recipe("name",250,items, "description","img_url");
        recipe.setId(5588L);

        Mockito.when(recipeRepository.findById(Mockito.any())).thenReturn(Optional.of(new Recipe()));

        User user=new User("username", "firstName", "lastName", "email", "address", "password", OffsetDateTime.now());
        user.setId(4L);
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(new User()));

        PostDTO postDTO= new PostDTO(5588L,"username");
        Post post = this.service.createPost(postDTO);
        Mockito.verify(this.service).createPost(postDTO);

        verify(userRepository, Mockito.times(1)).findByUsername(user.getUsername());


        Assert.assertNotNull("Post is null", postDTO);
        Assert.assertEquals("userId do not match", "username", user.getUsername());
        Assert.assertEquals("recipeId do not match", 5588L, recipe.getId().longValue());
    }

    @Test
    public void deletePostTest(){

        Item item= new Item(5, "Item Name", ItemCategory.FRUITS, 100, "url");
        List<Item> items=new ArrayList<>();
        items.add(item);
        Recipe recipe = new Recipe("name",250,items, "description","img_url");
        recipe.setId(5588L);
        User user=new User("username", "firstName", "lastName", "email", "address", "password", OffsetDateTime.now());
        user.setId(4L);

        Mockito.when(recipeRepository.findById(Mockito.any())).thenReturn(Optional.of(new Recipe()));

        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(new User()));


        PostDTO postDTO= new PostDTO(5588L,user.getUsername());
        Post post = this.service.createPost(postDTO);
        post.setId(2563L);

        this.postRepository.deleteById(post.getId());
       Mockito.verify(this.postRepository).deleteById(post.getId());

    }

    @Test
    public void approveTest(){

        Item item= new Item(5, "Item Name", ItemCategory.FRUITS, 100, "url");
        List<Item> items=new ArrayList<>();
        items.add(item);
        Recipe recipe = new Recipe("name",250,items, "description","img_url");
        recipe.setId(5588L);
        User user=new User("username", "firstName", "lastName", "email", "address", "password", OffsetDateTime.now());
        user.setId(4L);

        Post post = new Post(recipe,OffsetDateTime.now(),user);
        post.setId(2563L);
        this.postRepository.save(post);
        this.postRepository.findById(2563L);
        Mockito.verify(this.postRepository).findById(2563L);

        PostStatus status = PostStatus.APPROVED;
        post.setPostStatus(status);

        Mockito.when(postRepository.findById(Mockito.any())).thenReturn(Optional.of(new Post()));

        this.service.approvePost(2563L);
        Mockito.verify(this.service).approvePost(2563L);

        Assert.assertEquals("post status do not match", PostStatus.APPROVED,post.getPostStatus());

    }

    @Test
    public void declineTest(){

        Item item= new Item(5, "Item Name", ItemCategory.FRUITS, 100, "url");
        List<Item> items=new ArrayList<>();
        items.add(item);
        Recipe recipe = new Recipe("name",250,items, "description","img_url");
        recipe.setId(5588L);
        User user=new User("username", "firstName", "lastName", "email", "address", "password", OffsetDateTime.now());
        user.setId(4L);

        Post post = new Post(recipe,OffsetDateTime.now(),user);
        post.setId(2563L);
        this.postRepository.save(post);
        this.postRepository.findById(2563L);
        Mockito.verify(this.postRepository).findById(2563L);

        PostStatus status = PostStatus.DECLINED;
        post.setPostStatus(status);

        Mockito.when(postRepository.findById(Mockito.any())).thenReturn(Optional.of(new Post()));

        this.service.declinePost(2563L);
        Mockito.verify(this.service).declinePost(2563L);

        Assert.assertEquals("post status do not match", PostStatus.DECLINED,post.getPostStatus());

    }


}


