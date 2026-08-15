package com.example.chat.presentationLayer.dto;

import com.example.chat.persistenceLayer.entity.enums.EstadoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Integer idUsuario;

    private String nombre;

    private String telefono;

    private String email;

    private EstadoUsuario estado;

    private LocalDate fechaRegistro;

}
