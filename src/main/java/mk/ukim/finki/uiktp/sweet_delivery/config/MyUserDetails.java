package mk.ukim.finki.uiktp.sweet_delivery.config;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
        User appUser = optionalUser.get();

        return org.springframework.security.core.userdetails.User//
                .withUsername(username)//
                .password(appUser.getPassword())//
                .authorities(appUser.getAppUserRoles())//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }

}
