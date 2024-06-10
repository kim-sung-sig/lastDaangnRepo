package com.demo.daangn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing // jpa config
public class DaangnApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaangnApplication.class, args);
	}

}
