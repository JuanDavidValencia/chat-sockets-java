package com.example.chat.presentationLayer.controller;


import com.example.chat.businessLayer.businessExceptions.OperacionNoAutorizadaException;
import com.example.chat.businessLayer.service.ParticipaService;
import com.example.chat.presentationLayer.dto.ConversacionResponseDTO;
import com.example.chat.presentationLayer.dto.ParticipaRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/participa")
@RequiredArgsConstructor
@Slf4j
public class ParticipaController {

    private final ParticipaService participaService;

    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<ConversacionResponseDTO>> listarConversacionesUsuario(
            @PathVariable Integer idUsuario,
            Principal principal
    ){

        Integer idUsuarioAutenticado = Integer.parseInt(principal.getName());

        try{

            List<ConversacionResponseDTO> resultado = participaService.listarConversacionesUsuario(idUsuario, idUsuarioAutenticado);
            log.info("Conversaciones listadas correctamente.");
            return ResponseEntity.status(HttpStatus.OK).body(resultado);

        } catch (OperacionNoAutorizadaException e){

            log.warn(
                    "El usuario {} no está autorizado para listar las conversaciones del usuario {}.",
                    idUsuarioAutenticado,
                    idUsuario
            );
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        } catch (IllegalArgumentException e){

            log.warn("Error al listar las conversaciones del usuario {}.", idUsuario);
            return ResponseEntity.badRequest().build();

        }

    }

    @PostMapping
    public ResponseEntity<Void> agregarParticipante(
            @RequestBody ParticipaRequestDTO participa,
            Principal principal
    ) {

        Integer idUsuario = Integer.parseInt(principal.getName());

        try {

            participaService.agregarParticipante(participa, idUsuario);
            log.info(
                    "Usuario {} agregó al usuario {} a la conversación {}.",
                    idUsuario,
                    participa.getIdUsuario(),
                    participa.getIdConversacion()
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();


        } catch (OperacionNoAutorizadaException e){

            log.warn(
                    "El usuario {} no está autorizado para agregar al usuario {} a la conversación {}.",
                    idUsuario,
                    participa.getIdUsuario(),
                    participa.getIdConversacion()
            );
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        }catch (IllegalArgumentException e){

            log.warn("Error al agregar participante de la conversación del usuario: {}.", idUsuario);
            return ResponseEntity.badRequest().build();

        }

    }

    @DeleteMapping("/{idUsuario}/{idConversacion}")
    public ResponseEntity<Void> eliminarParticipante(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idConversacion,
            Principal principal
    ){

        Integer idUsuarioAutenticado = Integer.parseInt(principal.getName());

        try{

            participaService.eliminarParticipante(idUsuario, idConversacion, idUsuarioAutenticado);
            log.info(
                    "Usuario {} eliminó al usuario {} de la conversación {}.",
                    idUsuarioAutenticado,
                    idUsuario,
                    idConversacion
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (OperacionNoAutorizadaException e){

            log.warn(
                    "El usuario {} no está autorizado para eliminar al usuario {} de la conversación {}.",
                    idUsuarioAutenticado,
                    idUsuario,
                    idConversacion
            );
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        } catch (IllegalArgumentException e){

            log.warn(
                    "Error al eliminar al usuario {} de la conversación {}: {}.",
                    idUsuario,
                    idConversacion,
                    e.getMessage()
            );
            return ResponseEntity.badRequest().build();

        }


    }
}
