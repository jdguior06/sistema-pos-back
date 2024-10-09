package com.sistema.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.pos.dto.UsuarioDTO;
import com.sistema.pos.response.AuthResponse;
import com.sistema.pos.response.LoginRequest;
import com.sistema.pos.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class RegistroController {
	
	@Autowired
	private UsuarioService userService;

	@PostMapping(value = "login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		return ResponseEntity.ok(userService.login(request));
	}
	
	@PostMapping(value = "register")
	public ResponseEntity<AuthResponse> register(@RequestBody UsuarioDTO userDto) {
		return ResponseEntity.ok(userService.createUser(userDto));
	}
	
	@PostMapping(value = "registerAdmin")
	public ResponseEntity<AuthResponse> registerAdmin(@RequestBody UsuarioDTO userDto) {
		return ResponseEntity.ok(userService.createUserAdmin(userDto));
	}
	
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AuthResponse> getCurrentUser(Authentication authentication) {
        return ResponseEntity.ok(userService.loader(authentication));
    }

}
