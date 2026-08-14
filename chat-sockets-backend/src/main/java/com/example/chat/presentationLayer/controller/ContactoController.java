package com.example.chat.presentationLayer.controller;

import com.example.chat.businessLayer.service.ContactoService;
import com.example.chat.presentationLayer.dto.ContactoEditarDTO;
import com.example.chat.presentationLayer.dto.ContactoRequestDTO;
import com.example.chat.presentationLayer.dto.ContactoResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacto")
@RequiredArgsConstructor
@Slf4j
public class ContactoController {

    private final ContactoService contactoService;

    @PostMapping
    public ResponseEntity<ContactoResponseDTO> crearContacto(
            @RequestBody ContactoRequestDTO contacto
    ){

        try{

            ContactoResponseDTO resultado = contactoService.crearContacto(contacto);
            log.info("El contacto {} ha sido creado exitosamente.", resultado.getIdContacto());
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

        } catch (IllegalArgumentException e) {

            log.warn("Error de validación al crear el contacto: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();

        }

    }

    @GetMapping("/{idContacto}")
    public ResponseEntity<ContactoResponseDTO> buscarContacto(
            @PathVariable Integer idContacto
    ){

        try{

            ContactoResponseDTO resultado = contactoService.buscarContacto(idContacto);
            log.info("El contacto {} ha sido encontrado exitosamente.", idContacto);
            return ResponseEntity.status(HttpStatus.OK).body(resultado);

        } catch (IllegalArgumentException e){

            log.warn("Error de validación al buscar el contacto: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();

        }
    }

    @PutMapping
    public ResponseEntity<ContactoResponseDTO> editarContacto(
            @RequestBody ContactoEditarDTO contacto
    ){

        try{

            ContactoResponseDTO resultado = contactoService.editarContacto(contacto);
            log.info("El contacto {} ha sido modificado exitosamente.", contacto.getIdContacto());
            return ResponseEntity.status(HttpStatus.OK).body(resultado);

        } catch (IllegalArgumentException e){

            log.warn("Error de validación al editar el contacto: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();

        }

    }

    @DeleteMapping("/{idContacto}")
    public ResponseEntity<Void> eliminarContacto(
            @PathVariable Integer idContacto
    ){

        try{

            contactoService.eliminarContacto(idContacto);
            log.info("El contacto {} ha sido eliminado exitosamente.", idContacto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (IllegalArgumentException e){

            log.warn("Error de validación al eliminar el contacto: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();

        }
    }


}
