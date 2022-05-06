package mk.ukim.finki.uiktp.sweet_delivery.service;

import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;

import java.util.Optional;

public interface UserService {
    Optional<User> login(String username, String password);
    Optional<User> register(String username, String password, String firstName,
                            String lastName, String email, String address);
    Optional<User> delete(String username);
    Optional<User> update(Long userId, String username, Boolean changePassword, String oldPassword, String newPassword, String firstName,
                          String lastName, String email, String address);
    Optional<User> findById(Long userId);
    Optional<User> findByUsername(String username);
}
