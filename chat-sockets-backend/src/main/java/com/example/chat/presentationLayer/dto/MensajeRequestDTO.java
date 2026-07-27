package com.example.chat.presentationLayer.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensajeRequestDTO {

    private String contenido;

    private Integer idRemitente;

    private Integer idConversacion;

}
