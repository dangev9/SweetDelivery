package mk.ukim.finki.uiktp.sweet_delivery.model.metamodel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mm_recipe", schema="metamodel")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean in_stock;

    @Column
    private String name;

    private Integer price;

    @ManyToMany
    private List<Item> itemList;

    @ManyToMany
    private List<Order> ordersList;

    private String description;

    private String img_url;

    @ManyToOne
    @JoinColumn(name = "mm_rating_id")
    private Rating rating;

    @OneToOne
    @JoinColumn(name  = "mm_post_id")
    private Post post;


}
