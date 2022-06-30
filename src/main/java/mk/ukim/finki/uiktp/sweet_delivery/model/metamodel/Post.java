package mk.ukim.finki.uiktp.sweet_delivery.model.metamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.uiktp.sweet_delivery.model.enums.PostStatus;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mm_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "post")
    private Recipe recipe;

    @Column
    private OffsetDateTime date_created;

    @ManyToOne
    @JoinColumn(name = "ur_user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private PostStatus postStatus;

    public Post(Recipe recipe, OffsetDateTime date_created, User user) {
        this.recipe = recipe;
        this.date_created = date_created;
        this.user = user;
    }
}
