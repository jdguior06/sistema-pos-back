package com.sistema.pos;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.sistema.pos.config.WebConfig;

@SpringBootApplication
@Import(WebConfig.class)
public class SistemaPosBackApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("America/La_Paz"));
		SpringApplication.run(SistemaPosBackApplication.class, args);
	}

}
