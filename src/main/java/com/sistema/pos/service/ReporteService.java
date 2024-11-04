package com.sistema.pos.service;

import com.sistema.pos.config.LoggableAction;
import com.sistema.pos.dto.ReporteFiltroDTO;
import com.sistema.pos.repository.NotaEntradaRepository;
import com.sistema.pos.repository.ProductoAlmacenRepository;
import com.sistema.pos.repository.ProductoRepository;
import com.sistema.pos.repository.ProveedorRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProveedorRespository proveedorRepository;

    @Autowired
    private ProductoAlmacenRepository  productoAlmacenRepository;

    @Autowired
    private NotaEntradaRepository notaEntradaRepository;

    @LoggableAction
    public List<?>generarReporte(ReporteFiltroDTO filtros){
        switch (filtros.getTipoReporte()) {
            case PRODUCTO:
                return  generarReporteProductos(filtros);
            case PROVEEDOR:
                return generarReporteProveedores(filtros);
            case PRODUCTOALMACEN:
                return generarReporteProductoAlmacen(filtros);
            case NOTA_ENTRADA:
                return generarReporteNota(filtros);
            default:
                throw new IllegalArgumentException("Tipo de reporte no valido");
        }
    }

    @LoggableAction
    private List<?>generarReporteProductos(ReporteFiltroDTO filtros) {
        return productoRepository.findAll();

    }

    @LoggableAction
    private List<?> generarReporteProveedores(ReporteFiltroDTO filtros) {
        // Aplicar filtros en el repositorio de proveedores
        return proveedorRepository.findAll();  // Agrega los filtros necesarios
    }
    
    @LoggableAction
    public List<?>generarReporteProductoAlmacen(ReporteFiltroDTO filtros) {
        Long almacenId = filtros.getAlmacen() != null ? Long.parseLong(filtros.getAlmacen()) : null;
        Long productoId = filtros.getProducto() != null ? Long.parseLong(filtros.getProducto()) : null;

        return productoAlmacenRepository.findByAlmacenAndProductoAndStockRange(
                almacenId, productoId, filtros.getStockMin(), filtros.getStockMax()
        );
    }
    
    @LoggableAction
    public List<?>generarReporteNota(ReporteFiltroDTO filtros) {
        return notaEntradaRepository.findByProveedorAndFechaAndTotal(
                Long.parseLong(String.valueOf(filtros.getProveedor())), filtros.getFechaInicio(), filtros.getFechaFin(), filtros.getTotalMin(), filtros.getTotalMax());
    }
}
