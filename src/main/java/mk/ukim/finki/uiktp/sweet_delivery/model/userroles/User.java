package mk.ukim.finki.uiktp.sweet_delivery.model.userroles;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Coupon;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Order;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Post;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Rating;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ur_user", schema="userroles")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column
    private String address;

    @Column
    @JsonIgnore
    private String password;

    @Column
    @CreationTimestamp
    private OffsetDateTime date_created;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<UserRole> userRoles;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Rating> rated_recipes;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Coupon> coupons;

}
