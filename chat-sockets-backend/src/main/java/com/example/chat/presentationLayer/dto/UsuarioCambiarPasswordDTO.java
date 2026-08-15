package com.example.chat.presentationLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioCambiarPasswordDTO {

    private Integer idUsuario;

    private String passwordActual;

    private String passwordNueva;
}
