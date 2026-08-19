package com.example.chat.presentationLayer.controller;

import com.example.chat.businessLayer.service.MensajeService;
import com.example.chat.persistenceLayer.entity.Conversacion;
import com.example.chat.persistenceLayer.entity.enums.TipoConversacion;
import com.example.chat.persistenceLayer.repository.ConversacionRepository;
import com.example.chat.presentationLayer.dto.MensajeRequestDTO;
import com.example.chat.presentationLayer.dto.MensajeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MensajeService mensajeService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ConversacionRepository conversacionRepository;

    @MessageMapping("/chat")
    public void enviarMensaje(MensajeRequestDTO mensaje, Principal principal){

        String destino;

        Integer idUsuario = Integer.parseInt(principal.getName());

        MensajeResponseDTO resultado = mensajeService.enviarMensaje(mensaje, idUsuario);

        Conversacion conversacion = conversacionRepository.findById(resultado.getIdConversacion())
                .orElseThrow(() ->
                        new IllegalArgumentException("La conversación no existe.")
                );

        TipoConversacion tipoConversacion = conversacion.getTipo();


        if (tipoConversacion.equals(TipoConversacion.GLOBAL)){

            destino = "/topic/chat";

        } else {

            destino = "/topic/conversacion/" + resultado.getIdConversacion();

        }

        simpMessagingTemplate.convertAndSend(destino, resultado);

    }
}
