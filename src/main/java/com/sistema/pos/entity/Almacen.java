package com.sistema.pos.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "almacen")
@Entity
public class Almacen {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    private Integer numero;
    
    private String descripcion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;
    
    @OneToMany(mappedBy = "almacen", cascade = CascadeType.ALL)
    private List<ProductoAlmacen> productoAlmacen;
    
    private boolean activo;

}
