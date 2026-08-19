package com.example.chat.presentationLayer.dto;

import com.example.chat.persistenceLayer.entity.enums.EstadoMensaje;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class MensajeResponseDTO {

    private Integer idMensaje;

    private LocalDate fecha;

    private LocalTime hora;

    private String contenido;

    private EstadoMensaje estado;

    private Integer idRemitente;

    private Integer idConversacion;
}
