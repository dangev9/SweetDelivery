package mk.ukim.finki.uiktp.sweet_delivery.model.metamodel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mm_order", schema="metamodel")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private OffsetDateTime orderDate;

    private Integer amount;

    @Column
    private String deliveryAddress;

    @ManyToMany
    private List<Recipe> recipeList;

    @ManyToOne
    @JoinColumn(name = "ur_user_id")
    private User user;

}
