package mk.ukim.finki.uiktp.sweet_delivery.web;

import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rest/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Optional<User> login(@RequestParam String username, @RequestParam String password)
    {
        return this.userService.login(username, password);
    }

    @PostMapping("/register")
    public Optional<User> register(@RequestParam String username, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String address)
    {
        return this.userService.register(username, password, firstName, lastName, email, address);
    }

    @DeleteMapping("delete")
    public void delete(String username)
    {
        this.userService.delete(username);
    }

    @PostMapping("/update")
    public Optional<User> update(Long userId, String username, Boolean changePassword, String oldPassword, String newPassword, String firstName, String lastName, String email, String address)
    {
        return this.userService.update(userId, username, changePassword, oldPassword, newPassword, firstName, lastName, email, address);
    }

    @GetMapping("findById/{id}")
    public Optional<User> findById(@PathVariable Long userId)
    {
        return this.userService.findById(userId);
    }

    @GetMapping("findByUsername")
    public Optional<User> findByUsername(String username)
    {
        return this.userService.findByUsername(username);
    }
}
