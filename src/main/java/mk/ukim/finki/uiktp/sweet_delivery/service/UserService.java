package mk.ukim.finki.uiktp.sweet_delivery.service;

import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.UserUpdateDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService {
    String login(String username, String password);
    Optional<User> register(String username, String password, String firstName,
                            String lastName, String email, String address);
    String signUp (User user);
    Optional<User> delete(String username);
    User update(UserUpdateDTO userUpdateDTO);
    Optional<User> findById(Long userId);
    Optional<User> findByUsername(String username);
    public User whoami(HttpServletRequest req);
    public String refresh(String username);
}
