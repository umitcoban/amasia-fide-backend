package com.umityasincoban.amasia_fide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
@EnableWebSecurity
@EnableCaching
@EnableJpaRepositories
@EnableAsync
public class AmasiaFideApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmasiaFideApplication.class, args);
	}

}
