package com.example.chat.presentationLayer.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MensajeEditarRequestDTO {

    private Integer idMensaje;

    private String contenido;

}
