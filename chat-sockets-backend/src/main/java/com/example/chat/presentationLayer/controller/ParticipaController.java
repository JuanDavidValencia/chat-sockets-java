package com.example.chat.presentationLayer.controller;


import com.example.chat.businessLayer.service.ParticipaService;
import com.example.chat.presentationLayer.dto.ConversacionResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Endpoint faltantes en este Controller

@RestController
@RequestMapping("/participa")
@RequiredArgsConstructor
@Slf4j
public class ParticipaController {

    private final ParticipaService participaService;

    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<ConversacionResponseDTO>> listarConversacionesUsuario(
            @PathVariable Integer idUsuario
    ){

        try{

            List<ConversacionResponseDTO> resultado = participaService.listarConversacionesUsuario(idUsuario);
            log.info("Conversaciones listadas correctamente.");
            return ResponseEntity.status(HttpStatus.OK).body(resultado);

        }catch (IllegalArgumentException e){

            log.warn("Error al listar las conversaciones del usuario {}.", idUsuario);
            return ResponseEntity.badRequest().build();

        }

    }
}
