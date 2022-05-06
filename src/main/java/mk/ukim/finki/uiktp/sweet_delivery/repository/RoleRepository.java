package mk.ukim.finki.uiktp.sweet_delivery.repository;

import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
