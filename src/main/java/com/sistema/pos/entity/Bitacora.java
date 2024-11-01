package com.sistema.pos.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bitacora {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 128)
	private String ip;

	@Column(nullable = false, length = 128)
	private String evento;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(name = "fecha", updatable = false)
	private LocalDateTime fecha;

    private String usuario;

	@Column(nullable = false, length = 128)
	private String dispositivo;
	
    @PrePersist
    public void prePersist() {
        fecha = LocalDateTime.now(ZoneId.of("America/La_Paz"));
    }

}
