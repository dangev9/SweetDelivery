package mk.ukim.finki.uiktp.sweet_delivery;

import mk.ukim.finki.uiktp.sweet_delivery.config.JwtTokenProvider;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.UsernameNotFoundException;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.repository.UserRepository;
import mk.ukim.finki.uiktp.sweet_delivery.service.UserService;
import mk.ukim.finki.uiktp.sweet_delivery.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService service;

    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private AuthenticationManager authenticationManager;


    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);
        User user=new User("emaivanovska", "Ema", "Ivanovska", "ema_ivanovska@gmail.com",
                "Partizanski odredi", "password", OffsetDateTime.now());

        when(this.userRepository.save(Mockito.any(User.class))).thenReturn(user);

        this.service = Mockito.spy(new UserServiceImpl(this.userRepository, this.passwordEncoder, this.jwtTokenProvider,
                this.authenticationManager));

    }


    @Test
    public void testSuccessRegister() {
        Optional<User> user = this.service.register("emaivanovska", "password", "Ema", "Ivanovska", "ema_ivanovska@gmail.com", "Partizanski odredi");

        Mockito.verify(this.service).register("emaivanovska", "password", "Ema", "Ivanovska", "ema_ivanovska@gmail.com", "Partizanski odredi");


        Assert.assertNotNull("User is null", user);
        Assert.assertEquals("username do not match", "emaivanovska", user.get().getUsername());
        Assert.assertEquals("firstname do not match", "Ema", user.get().getFirstName());
        Assert.assertEquals("lastname do not match", "Ivanovska", user.get().getLastName());
        Assert.assertEquals("password do not match", "password", user.get().getPassword());
        Assert.assertEquals("email do not match", "ema_ivanovska@gmail.com", user.get().getEmail());
        Assert.assertEquals("address do not match", "Partizanski odredi", user.get().getAddress());
    }

    @Test
    public void testNullUsernameRegister() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register(null, "password", "Ema", "Ivanovska",
                        "ema_ivanovska@gmail.com", "Partizanski odredi"));
        Mockito.verify(this.service).register(null, "password", "Ema", "Ivanovska",
                "ema_ivanovska@gmail.com", "Partizanski odredi");
    }

    @Test
    public void testSuccessLogin() {
        Assert.assertThrows("InvalidUsernameOrPasswordException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.login("emaivanovska", "password"));
        Mockito.verify(this.service).login("emaivanovska", "password");
    }

    @Test
    public void testEmptyPasswordLogin() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.login("emaivanovska", ""));
        Mockito.verify(this.service).login("emaivanovska", "");
    }

	@Test
	public void deleteUsernameTest() {
        User user=new User("emaivanovska", "Ema", "Ivanovska", "ema_ivanovska@gmail.com",
                "Partizanski odredi", "password", OffsetDateTime.now());

				this.service.delete(user.getUsername());
		        Mockito.verify(this.service).delete(user.getUsername());
	}

    @Test
    public void findByUsernameTest() {
        User user=new User("", "Ema", "Ivanovska", "ema_ivanovska@gmail.com",
                "Partizanski odredi", "password", OffsetDateTime.now());

        Assert.assertThrows("UsernameNotFoundException expected",
                UsernameNotFoundException.class,
                () -> this.service.findByUsername(user.getUsername()));

        Mockito.verify(this.service).findByUsername(user.getUsername());
    }




}
