package com.sistema.pos.controller;

import com.sistema.pos.dto.CategoriaDTO;
import com.sistema.pos.entity.Categoria;
import com.sistema.pos.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping(path = "/read")
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Categoria> getCategoria(@PathVariable Long id) {
        return categoriaService.findById(id)
                .map(categoria -> ResponseEntity.ok(categoria))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/crear")
    private ResponseEntity<Categoria> store(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        try {
            // Crear una nueva instancia de Categoria usando el DTO
            Categoria categoria = new Categoria();
            categoria.setNombre(categoriaDTO.getNombre());
            categoria.setDescripcion(categoriaDTO.getDescripcion()); // Si es necesario

            // Guardar la nueva categoría
            Categoria nuevo = categoriaService.save(categoria);

            // Retornar la respuesta con el nuevo recurso creado
            return ResponseEntity.created(new URI("/categoria/crear/" + nuevo.getId())).body(nuevo);
        } catch (Exception e) {
            // Manejo de excepciones y retorno de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (categoriaService.existsById(id)) {
            categoriaService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody Categoria categoriaActualizado) {
        return categoriaService.findById(id)
                .map(categoria -> {
                    categoria.setNombre(categoriaActualizado.getNombre());
                    categoria.setDescripcion(categoriaActualizado.getDescripcion());
                    categoriaService.save(categoria);
                    return ResponseEntity.ok(categoria);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
