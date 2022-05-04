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
@Table(name="mm_item", schema="metamodel")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean in_stock;

    @Column
    private String name;

    private Integer price;

    private String img_url;

    @ManyToMany
    private List<Recipe> recipeList;




}
