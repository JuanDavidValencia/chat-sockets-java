package com.example.chat.presentationLayer.controller;

import com.example.chat.businessLayer.businessExceptions.OperacionNoAutorizadaException;
import com.example.chat.presentationLayer.dto.ConversacionResponseDTO;
import com.example.chat.presentationLayer.dto.ConversacionRequestDTO;
import com.example.chat.businessLayer.service.ConversacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/conversacion")
@RequiredArgsConstructor
@Slf4j
public class ConversacionController {

    private final ConversacionService conversacionService;

    @PostMapping
    public ResponseEntity<ConversacionResponseDTO> crearConversacion (
            @RequestBody ConversacionRequestDTO conversacion,
            Principal principal
    ) {

        Integer idUsuario = Integer.parseInt(principal.getName());

        try{

            ConversacionResponseDTO resultado = conversacionService.crearConversacion(conversacion, idUsuario);
            log.info("Conversacion creada correctamente.");
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

        } catch (IllegalArgumentException e){

            log.warn("Error de validación al crear conversación: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();

        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<ConversacionResponseDTO> buscarConversacion(
            @PathVariable Integer id,
            Principal principal
    ){

        Integer idUsuario = Integer.parseInt(principal.getName());

        try{

            ConversacionResponseDTO resultado = conversacionService.buscarConversacion(id, idUsuario);
            log.info("Conversacion {} encontrada exitosamente.", id);
            return ResponseEntity.status(HttpStatus.OK).body(resultado);

        } catch (OperacionNoAutorizadaException e){

            log.warn("Usuario {} no está autorizado para buscar la conversación: {}.", idUsuario, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        } catch (IllegalArgumentException e) {

            log.warn("Error de validación al buscar la conversación: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();

        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConversacion(
            @PathVariable Integer id,
            Principal principal
    ){

        Integer idUsuario = Integer.parseInt(principal.getName());

        try{

            conversacionService.eliminarConversacion(id, idUsuario);
            log.info("Conversacion {} eliminada correctamente.", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (OperacionNoAutorizadaException e){

            log.warn("Usuario {} no está autorizado para eliminar la conversación: {}.", idUsuario, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        } catch (IllegalArgumentException e){

            log.warn("Error de validación al eliminar la conversación: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();

        }

    }


}
