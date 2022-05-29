package mk.ukim.finki.uiktp.sweet_delivery.model.metamodel;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @JsonManagedReference
    @ManyToMany
    private List<Item> itemList;

    @ManyToMany
    private List<Order> ordersList;

    private String description;

    private String img_url;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "mm_rating_id")
    private Rating rating;

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
        this.rating = null;
        this.post = null;
    }
}
