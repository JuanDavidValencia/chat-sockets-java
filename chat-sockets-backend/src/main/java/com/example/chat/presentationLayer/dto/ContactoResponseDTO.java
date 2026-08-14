package com.example.chat.presentationLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ContactoResponseDTO {

    private Integer idContacto;

    private Integer idUsuarioContacto;

    private String nombrePersonalizado;

    private LocalDate fechaCreacion;

}
