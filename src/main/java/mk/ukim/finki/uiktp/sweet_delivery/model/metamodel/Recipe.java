package mk.ukim.finki.uiktp.sweet_delivery.model.metamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.RecipeNotFoundException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mm_recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    private Integer price;

    @JsonIgnore
    @ManyToMany
    private List<Item> itemList;

    @ManyToMany
    private List<Order> ordersList;

    private String description;

    private String img_url;

    @ManyToMany
    @JsonManagedReference
    @JoinColumn(name = "mm_rating_id")
    private List<Rating> ratings;

    @OneToOne
    @JoinColumn(name  = "mm_post_id")
    private Post post;

    public Recipe(String name, Integer price, List<Item> itemList, String description, String img_url) {
        this.name = name;
        this.price = price;
        this.itemList = itemList;
        this.description = description;
        this.img_url = img_url;
        this.ordersList = new ArrayList<>();
        this.ratings = new ArrayList<>();
        this.post = null;
    }

    public Double getAverageRating(){
        if(this.ratings.size() == 0){
            return 0.0;
        }
        return this.ratings.stream().flatMapToInt(x -> IntStream.of(x.getRecipeStars())).average().getAsDouble();
    }
}
