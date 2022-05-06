package mk.ukim.finki.uiktp.sweet_delivery.repository;

import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.RolePrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, Long> {

}
