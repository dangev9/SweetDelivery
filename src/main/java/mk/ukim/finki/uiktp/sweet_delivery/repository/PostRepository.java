package mk.ukim.finki.uiktp.sweet_delivery.repository;

import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
