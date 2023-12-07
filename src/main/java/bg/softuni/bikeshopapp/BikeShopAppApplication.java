package bg.softuni.bikeshopapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BikeShopAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BikeShopAppApplication.class, args);
	}

}
