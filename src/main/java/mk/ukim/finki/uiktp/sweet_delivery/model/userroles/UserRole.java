package mk.ukim.finki.uiktp.sweet_delivery.model.userroles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ur_user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ur_role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "ur_user_id")
    private User user;

}
