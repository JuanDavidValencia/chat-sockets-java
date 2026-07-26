package com.example.chat.presentationLayer.controller;

import com.example.chat.presentationLayer.dto.ConversacionResponseDTO;
import com.example.chat.presentationLayer.dto.ConversacionRequestDTO;
import com.example.chat.businessLayer.service.ConversacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//NOTA: EL ENDPOINT ESTÁ COMENTADO YA QUE FALTA TERMINAR EL METODO listarConversaciones() EN EL SERVICE.

@RestController
@RequestMapping("/conversacion")
@RequiredArgsConstructor
@Slf4j
public class ConversacionController {

    private final ConversacionService conversacionService;

    @PostMapping
    public ResponseEntity<ConversacionResponseDTO> crearConversacion (
            @RequestBody ConversacionRequestDTO conversacion
    ) {

        try{

            ConversacionResponseDTO resultado = conversacionService.crearConversacion(conversacion);
            log.info("Conversacion creada correctamente.");
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

        } catch (IllegalArgumentException e){

            log.warn("Error de validación al crear usuario: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();

        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<ConversacionResponseDTO> buscarConversacion(
            @PathVariable Integer id
    ){

        try{

            ConversacionResponseDTO resultado = conversacionService.buscarConversacion(id);
            log.info("Conversacion {} encontrada exitosamente.", id);
            return ResponseEntity.status(HttpStatus.OK).body(resultado);

        } catch (IllegalArgumentException e) {

            log.warn("Error de validación al buscar la conversación: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();

        }

    }

    // Endpoint deshabilitado por el momento, falta que el servicio esté listo por completo.

    /*

    @GetMapping("/listar/{idUsuario}")
    public ResponseEntity<List<Conversacion>> listarConversaciones(
            @PathVariable Integer idUsuario
    ){

        try {

            List<Conversacion> conversaciones = conversacionService.listarConversaciones(idUsuario);
            log.info("Conversacion del usuario {} encontradas exitosamente.", idUsuario);
            return ResponseEntity.status(HttpStatus.OK).body(conversaciones);

        } catch (IllegalArgumentException e){

            log.warn("Error de validación al listar las conversaciones: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConversacion(
            @PathVariable Integer id
    ){

        try{

            conversacionService.eliminarConversacion(id);
            log.info("Conversacion {} eliminada correctamente.", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (IllegalArgumentException e){

            log.warn("Error de validación al eliminar la conversación: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();

        }

    }


}
