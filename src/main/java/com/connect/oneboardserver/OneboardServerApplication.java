package com.connect.oneboardserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OneboardServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneboardServerApplication.class, args);
	}

}
