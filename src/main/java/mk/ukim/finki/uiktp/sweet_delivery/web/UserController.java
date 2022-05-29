package mk.ukim.finki.uiktp.sweet_delivery.web;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.UserDataDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.UserResponseDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.UserUpdateDTO;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/rest/users")
@Api(tags = "users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/signin")
    @ApiOperation(value = "${UserController.signin}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    public String login(//
                        @ApiParam("Username") @RequestParam String username, //
                        @ApiParam("Password") @RequestParam String password) {
        return userService.login(username, password);
    }

    @PostMapping("/signup")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 422, message = "Username is already in use")})
    public String signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
        return userService.signUp(modelMapper.map(user, User.class));
    }

    @DeleteMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${UserController.delete}", authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 404, message = "The user doesn't exist"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public String delete(@ApiParam("Username") @PathVariable String username) {
        userService.delete(username);
        return username;
    }

    @GetMapping(value = "/me")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${UserController.me}", response = UserResponseDTO.class,
            authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public UserResponseDTO whoami(HttpServletRequest req) {
        return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public String refresh(HttpServletRequest req) {
        return userService.refresh(req.getRemoteUser());
    }

    /*@PostMapping("/register")
    public Optional<User> register(@RequestParam String username, @RequestParam String password,
    @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String address)
    {
        return this.userService.register(username, password, firstName, lastName, email, address);
    }*/

    /*@DeleteMapping("delete")
    public void delete(String username)
    {
        this.userService.delete(username);
    }*/

    //TODO: postman
    @PostMapping("/update")
    public User update(@RequestBody UserUpdateDTO userUpdateDTO)
    {
        return this.userService.update(userUpdateDTO);
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
