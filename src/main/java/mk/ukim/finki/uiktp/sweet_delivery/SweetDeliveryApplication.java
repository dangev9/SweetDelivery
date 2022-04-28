package mk.ukim.finki.uiktp.sweet_delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SweetDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SweetDeliveryApplication.class, args);
	}

}
