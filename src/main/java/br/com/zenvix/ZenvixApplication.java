package br.com.zenvix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ZenvixApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZenvixApplication.class, args);
	}

}
