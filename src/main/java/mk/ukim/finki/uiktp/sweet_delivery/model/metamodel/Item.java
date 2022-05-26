package mk.ukim.finki.uiktp.sweet_delivery.model.metamodel;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.uiktp.sweet_delivery.model.enums.ItemCategory;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mm_item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer items_in_stock;

    @Column
    private String name;

    @Enumerated(value = EnumType.STRING)
    private ItemCategory itemCategory;

    private Integer price;

    private String img_url;

    @ManyToMany
    @JsonBackReference
    private List<Recipe> recipeList;

    public Item(Integer items_in_stock, String name, ItemCategory itemCategory, Integer price, String img_url) {
        this.items_in_stock = items_in_stock;
        this.name = name;
        this.itemCategory = itemCategory;
        this.price = price;
        this.img_url = img_url;
    }
}
