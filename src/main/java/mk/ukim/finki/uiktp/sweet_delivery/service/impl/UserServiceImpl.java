package mk.ukim.finki.uiktp.sweet_delivery.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.uiktp.sweet_delivery.config.JwtTokenProvider;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.CustomException;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.UsernameTakenException;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.UserUpdateDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.repository.UserRepository;
import mk.ukim.finki.uiktp.sweet_delivery.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public String login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).get().getAppUserRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
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
    public String signUp(User appUser) {
        if (!userRepository.existsByUsername(appUser.getUsername())) {
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            userRepository.save(appUser);
            return jwtTokenProvider.createToken(appUser.getUsername(), appUser.getAppUserRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
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
    public User update(UserUpdateDTO userUpdateDTO) {

        User user = this.userRepository.findById(userUpdateDTO.getUserId()).orElseThrow(() -> new InvalidUserIdException(userUpdateDTO.getUserId()));

        // Boolean value indicates if user wants to change the password, or leave it unchanged
        // If the user wants to change the password, he must first enter the old password, and if it matches only then change
        if(userUpdateDTO.getChangePassword() && !userUpdateDTO.getOldPassword().isEmpty() && !passwordEncoder.matches(userUpdateDTO.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(userUpdateDTO.getNewPassword()));
        }

        if(userUpdateDTO.getUsername()!=null){
            user.setUsername(user.getUsername());
        }
        if(userUpdateDTO.getFirstName()!=null){
            user.setFirstName(userUpdateDTO.getFirstName());
        }
        if(userUpdateDTO.getLastName()!=null){
            user.setLastName(userUpdateDTO.getLastName());
        }
        if(userUpdateDTO.getEmail()!=null){
            user.setEmail(userUpdateDTO.getEmail());
        }
        if(userUpdateDTO.getAddress()!=null){
            user.setAddress(userUpdateDTO.getAddress());
        }
        user.setAddress(userUpdateDTO.getAddress());

        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User whoami(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req))).get();
    }

    @Override
    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).get().getAppUserRoles());
    }
}
