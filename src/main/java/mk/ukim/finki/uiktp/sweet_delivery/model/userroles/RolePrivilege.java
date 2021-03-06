package mk.ukim.finki.uiktp.sweet_delivery.model.userroles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ur_role_privilege", schema="userroles")
public class RolePrivilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ur_privilege_id")
    private Privilege privilege;

    @ManyToOne
    @JoinColumn(name = "ur_role_id")
    private Role role;
}
