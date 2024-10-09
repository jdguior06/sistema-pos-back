package com.sistema.pos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.sistema.pos.config.WebConfig;

@SpringBootApplication
@Import(WebConfig.class)
public class SistemaPosBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaPosBackApplication.class, args);
	}

}
