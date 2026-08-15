package com.example.chat.presentationLayer.dto;

import com.example.chat.persistenceLayer.entity.enums.EstadoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioEditarDTO {

    private Integer idUsuario;

    private String nombre;

    private String telefono;

    private String email;
}
