package com.example.chat.presentationLayer.controller;

import com.example.chat.businessLayer.service.MensajeService;
import com.example.chat.presentationLayer.dto.MensajeEditarDTO;
import com.example.chat.presentationLayer.dto.MensajeRequestDTO;
import com.example.chat.presentationLayer.dto.MensajeResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensaje")
@RequiredArgsConstructor
@Slf4j
public class MensajeController {

    private final MensajeService mensajeService;

    @PostMapping
    public ResponseEntity<MensajeResponseDTO> enviarMensaje(
            @RequestBody MensajeRequestDTO mensaje
    ){

        try{

            MensajeResponseDTO resultado = mensajeService.enviarMensaje(mensaje);
            log.info("Mensaje enviado exitosamente.");
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

        } catch (IllegalArgumentException e){

            log.warn("Error de validación al crear usuario: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();

        }

    }

    @GetMapping("conversacion/{idConversacion}")
    public ResponseEntity<List<MensajeResponseDTO>> listarMensajes(
            @PathVariable Integer idConversacion
    ){

        try{

            List<MensajeResponseDTO> resultado = mensajeService.listarMensajes(idConversacion);
            log.info("Listando mensajes de la conversación: {}.", idConversacion);
            return ResponseEntity.status(HttpStatus.OK).body(resultado);

        } catch (IllegalArgumentException e){

            log.warn("Error al listar los mensajes de la conversación {}: {}.",
                    idConversacion,
                    e.getMessage());
            return ResponseEntity.badRequest().build();

        }

    }

    @DeleteMapping("/{idMensaje}")
    public ResponseEntity<Void> eliminarMensaje(
            @PathVariable Integer idMensaje
    ){

        try{

            mensajeService.eliminarMensaje(idMensaje);
            log.info("Mensaje {} eliminado exitosamente.", idMensaje);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (IllegalArgumentException e){

            log.warn("Error al eliminar el mensaje {}: {}", idMensaje, e.getMessage());
            return ResponseEntity.badRequest().build();

        }

    }

    @PutMapping("/{idMensaje}")
    public ResponseEntity<MensajeResponseDTO> editarMensaje(
            @PathVariable Integer idMensaje,
            @RequestBody MensajeEditarDTO contenido
    ){

        try{

            MensajeResponseDTO resultado = mensajeService.editarMensaje(idMensaje, contenido);
            log.info("Mensaje {} editado exitosamente.", idMensaje);
            return ResponseEntity.status(HttpStatus.OK).body(resultado);

        }catch (IllegalArgumentException e){

            log.warn("Error al editar el mensaje {}: {}", idMensaje, e.getMessage());
            return ResponseEntity.badRequest().build();

        }

    }


}
