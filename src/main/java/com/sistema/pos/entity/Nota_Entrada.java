package com.sistema.pos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nota_entrada")
public class Nota_Entrada  {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDateTime fecha;
    
    private Double total;

    @ManyToOne
    @JoinColumn(name = "id_almacen")
    private  Almacen almacen;

    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;
    
    @ManyToOne
    @JoinColumn(name = "id_personal")
    private Usuario usuario;
    
    @OneToMany(mappedBy = "notaEntrada", cascade = CascadeType.ALL)
    private List<DetalleNotaE> detalleNotaEntrada;
    
    @PrePersist
    public void prePersist() {
    	fecha = LocalDateTime.now(ZoneId.of("America/La_Paz"));
    }

}
