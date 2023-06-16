package br.com.project.wish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WishApplication {

	public static void main(String[] args) {
		SpringApplication.run(WishApplication.class, args);
	}

}
