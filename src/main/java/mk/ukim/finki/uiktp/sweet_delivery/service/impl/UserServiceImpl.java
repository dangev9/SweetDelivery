package mk.ukim.finki.uiktp.sweet_delivery.service.impl;

import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.UsernameTakenException;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.repository.UserRepository;
import mk.ukim.finki.uiktp.sweet_delivery.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> login(String username, String password) {
        if(username==null || username.isEmpty() || password==null || password.isEmpty()){
            throw new InvalidUsernameOrPasswordException();
        }
        return this.userRepository.findByUsernameAndPassword(username, password).or(()->Optional.of(new User()));
    }

    @Override
    public Optional<User> register(String username, String password, String firstName, String lastName, String email, String address) {
        // check for duplicate usernames
        if(this.userRepository.findByUsername(username).isPresent()) {
            throw new UsernameTakenException();
        }

        //TODO: Check for role. Different repositories for different roles?
        OffsetDateTime dateCreated = OffsetDateTime.now();
        return Optional.of(this.userRepository.save(new User(username, firstName, lastName, email, address,
                passwordEncoder.encode(password), dateCreated)));
    }

    @Override
    public Optional<User> delete(String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);

        if(user!=null) {
            this.userRepository.deleteById(user.getId());
            return Optional.of(user);
        } else {
            return Optional.of(new User());
        }
    }

    @Override
    public Optional<User> update(Long userId, String username, Boolean changePassword, String oldPassword,
                                 String newPassword, String firstName, String lastName, String email, String address) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new InvalidUserIdException(userId));

        // Boolean value indicates if user wants to change the password, or leave it unchanged
        // If the user wants to change the password, he must first enter the old password, and if it matches only then change
        if(changePassword && !oldPassword.isEmpty() && !passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAddress(address);

        return Optional.of(this.userRepository.save(user));
    }

    @Override
    public Optional<User> findById(Long userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
