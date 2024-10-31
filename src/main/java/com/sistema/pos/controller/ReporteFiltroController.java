package com.sistema.pos.controller;

import com.sistema.pos.dto.ReporteFiltroDTO;
import com.sistema.pos.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reporte")
public class ReporteFiltroController {
    @Autowired
    private ReporteService reporteService;

    @PostMapping
    public List<?> generarReporte(@RequestBody ReporteFiltroDTO filtros) {
        return reporteService.generarReporte(filtros);
    }
}
