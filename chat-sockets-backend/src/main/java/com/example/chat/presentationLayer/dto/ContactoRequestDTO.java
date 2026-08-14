package com.example.chat.presentationLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactoRequestDTO {

    private Integer idUsuario;

    private Integer idUsuarioContacto;

    private String nombrePersonalizado;

}
