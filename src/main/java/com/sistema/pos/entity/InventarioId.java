package com.sistema.pos.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@EqualsAndHashCode
public class InventarioId implements Serializable {
    private Long sucursalId;
    private Long productoId;

    // Constructores, si es necesario
    public InventarioId() {}

    public InventarioId(Long sucursalId, Long productoId) {
        this.sucursalId = sucursalId;
        this.productoId = productoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventarioId that = (InventarioId) o;
        return sucursalId.equals(that.sucursalId) && productoId == that.productoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sucursalId, productoId);
    }
}