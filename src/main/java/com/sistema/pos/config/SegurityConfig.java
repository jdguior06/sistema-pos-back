package com.sistema.pos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SegurityConfig {

		@Autowired
		private JwtAuthenticationFilter jwtAuthenticationFilter;

		@Autowired
		private AuthenticationProvider authenticationProvider;

		@Bean
		protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			return http
					.csrf(csrf -> csrf.disable()) // Desactivar CSRF para APIs REST; habilitarlo para formularios si es necesario
					.authorizeHttpRequests(authorize -> authorize
							.requestMatchers(
									"/pos/v3/api-docs/**",  // Documentación OpenAPI
									"/pos/swagger-ui/**",   // Recursos de Swagger UI
									"/pos/swagger-ui.html", // Página principal de Swagger UI
									"/pos/swagger-resources/**",
									"/pos/webjars/**",
									"/pos/plan",
									"/pos/suscriptor/crear",
									"/pos/auth/**",
									"/v3/api-docs/**",
									"/swagger-ui/**",
									"/swagger-ui.html",
									"/swagger-resources/**",
									"/webjars/**"
							).permitAll()             // Permitir acceso público a estas rutas
							.requestMatchers(
									"/pos/registro**", "/pos/js/**", "/pos/css/**", "/pos/img/**"
							).permitAll()
							.requestMatchers(HttpMethod.OPTIONS).permitAll()
							.anyRequest().authenticated() // Requerir autenticación para todo lo demás
					)
					.sessionManagement(sessionManager -> sessionManager
							.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless para JWT
					.authenticationProvider(authenticationProvider)
					.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
					.build();
		}
	}