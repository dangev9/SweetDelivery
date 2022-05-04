package mk.ukim.finki.uiktp.sweet_delivery.model.metamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mm_post", schema="metamodel")
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
}
