package com.sistema.pos.controller;
import com.sistema.pos.dto.CajaDTO;
import com.sistema.pos.entity.Caja;


import com.sistema.pos.service.SucursalService;
import com.sistema.pos.response.ApiResponse;
import com.sistema.pos.service.CajaService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sistema.pos.service.SucursalService;
import com.sistema.pos.util.HttpStatusMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;




//@PreAuthorize("hasRole('caja')")


@RestController
@RequestMapping("/caja")
public class CajaController {
    @Autowired
    private CajaService cajaService;

    @Autowired
    private SucursalService sucursalService;

    //  @GetMapping(path = "/read")

   /*
    @GetMapping
    public ResponseEntity<ApiResponse<List<Caja>>> getAllCajas() {
        List<Caja> cajas = cajaService.findAll();
        return new ResponseEntity<>(
                ApiResponse.<List<Caja>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(cajas)
                        .build(),
                HttpStatus.OK
        );
    }
*/

    @GetMapping
    public ResponseEntity<ApiResponse<List<Caja>>> listarCaja(@RequestParam(value = "search", required = false) String searchTerm) {
        List<Caja> cajas;
        if (searchTerm != null && !searchTerm.isEmpty()) {
            cajas = cajaService.buscarCajas(searchTerm);
        } else {
            // Si no hay término de búsqueda, se pueden devolver todos los cajas
            cajas = cajaService.findAll();
        }
        return new ResponseEntity<>(
                ApiResponse.<List<Caja>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(cajas)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Caja>> getCaja(@PathVariable Long id) {
        try {
            Caja caja = cajaService.findById(id);
            return new ResponseEntity<>(
                    ApiResponse.<Caja>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                            .data(caja)
                            .build(),
                    HttpStatus.OK
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<Caja>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }

  /**
    // @GetMapping(path = "/{id}")
    @GetMapping("/{id}")
    private ResponseEntity<Caja> get(@PathVariable Long id) {
        try {
            Caja caja = cajaService.findById(id).orElseThrow(() -> new IllegalArgumentException("Caja with ID: " + id + " not found."));
            return ResponseEntity.ok(caja); // Response 200 OK with the found Caja
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Response 404 Not Found if Caja not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Response 500 if any unexpected error occurs
        }
    }
**/
    // @PostMapping(path = "/crear")

  /**
    @PostMapping
    private ResponseEntity<Caja> store(@RequestBody CajaDTO caja){
        try {
            Caja creado= cajaService.store(caja);
            return ResponseEntity.created(new URI("/caja/crear/"+creado.getId())).body(creado);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
**/

  @PostMapping
//	@PreAuthorize("hasAuthority('GESTIONAR_CLIENTE')")
  public ResponseEntity<ApiResponse<Caja>> guardarCaja(@Valid @RequestBody CajaDTO caja, BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
          List<String> errors = bindingResult.getAllErrors().stream()
                  .map(DefaultMessageSourceResolvable::getDefaultMessage)
                  .collect(Collectors.toList());
          return new ResponseEntity<>(
                  ApiResponse.<Caja>builder()
                          .errors(errors)
                          .build(),
                  HttpStatus.BAD_REQUEST
          );
      }
      try {
          Caja creado = cajaService.store(caja);
          return new ResponseEntity<>(
                  ApiResponse.<Caja>builder()
                          .statusCode(HttpStatus.CREATED.value())
                          .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                          .data(creado)
                          .build(),
                  HttpStatus.CREATED
          );
      } catch (ResponseStatusException e) {
          return new ResponseEntity<>(
                  ApiResponse.<Caja>builder()
                          .statusCode(e.getStatusCode().value())
                          .message(e.getReason())
                          .build(),
                  e.getStatusCode()
          );
      }
  }
  /**
    @DeleteMapping("/{id}/desactivar")
    // "/{id}/desactivar"
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            cajaService.delete(id); // Elimina el si existe
            return ResponseEntity.ok().build(); // Respuesta 200 OK
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Respuesta 404 Not Found si no existe
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respuesta 500 si ocurre algún error inesperado
        }
    }
**/

  @PatchMapping("/{id}/desactivar")
  public ResponseEntity<ApiResponse<Void>> desactivarCliente(@PathVariable Long id) {
      try {
          cajaService.eliminarCaja(id);
          return new ResponseEntity<>(
                  ApiResponse.<Void>builder()
                          .statusCode(HttpStatus.OK.value())
                          .message("Caja desactivado correctamente")
                          .build(),
                  HttpStatus.OK
          );
      } catch (ResponseStatusException e) {
          return new ResponseEntity<>(
                  ApiResponse.<Void>builder()
                          .statusCode(e.getStatusCode().value())
                          .message(e.getReason())
                          .build(),
                  e.getStatusCode()
          );
      }
  }


   /**
    @PutMapping("/{id}")
    public ResponseEntity<Caja> update(@PathVariable Long id, @RequestBody CajaDTO cajaActualizado) {
        try {
            Caja updatedCaja = cajaService.update(id, cajaActualizado);
            return ResponseEntity.ok(updatedCaja); // Response 200 OK with updated Caja
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Response 404 Not Found if Caja not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Response 500 if any unexpected error occurs
        }
    }
    **/
   @PutMapping("/{id}")
   public ResponseEntity<ApiResponse<Caja>>update(@PathVariable Long id, @Valid @RequestBody  CajaDTO caja, BindingResult bindingResult) {
       if (bindingResult.hasErrors()) {
           List<String> errors = bindingResult.getAllErrors().stream()
                   .map(DefaultMessageSourceResolvable::getDefaultMessage)
                   .collect(Collectors.toList());
           return new ResponseEntity<>(
                   ApiResponse.<Caja>builder()
                           .errors(errors)
                           .build(),
                   HttpStatus.BAD_REQUEST
           );
       }
       try {
           Caja updatedcaja = cajaService.update(id, caja);
           return new ResponseEntity<>(
                   ApiResponse.<Caja>builder()
                           .statusCode(HttpStatus.OK.value())
                           .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                           .data(updatedcaja)
                           .build(),
                   HttpStatus.OK
           );
       } catch (ResponseStatusException e) {
           return new ResponseEntity<>(
                   ApiResponse.<Caja>builder()
                           .statusCode(e.getStatusCode().value())
                           .message(e.getReason())
                           .build(),
                   e.getStatusCode()
           );
       }
   }



}


