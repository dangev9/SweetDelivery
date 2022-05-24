package mk.ukim.finki.uiktp.sweet_delivery;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.AppUserRole;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;
import mk.ukim.finki.uiktp.sweet_delivery.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class SweetDeliveryApplication implements CommandLineRunner {
	private final UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SweetDeliveryApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... params) throws Exception {
		// Run only once, avoid creating duplicate users
//		User admin = new User();
//		admin.setUsername("admin");
//		admin.setPassword("admin");
//		admin.setEmail("admin@email.com");
//		admin.setAppUserRoles(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_ADMIN)));
//
//		userService.signUp(admin);
//
//		User client = new User();
//		client.setUsername("client");
//		client.setPassword("client");
//		client.setEmail("client@email.com");
//		client.setAppUserRoles(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_CLIENT)));
//
//		userService.signUp(client);
	}

}
