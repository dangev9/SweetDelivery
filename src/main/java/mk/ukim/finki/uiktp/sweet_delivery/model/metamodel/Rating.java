package mk.ukim.finki.uiktp.sweet_delivery.model.metamodel;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mm_rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ur_user_id")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "mm_recipe_id")
    private Recipe recipe;

    @Column
    private Integer recipeStars;

}
