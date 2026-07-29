package com.example.chat.presentationLayer.dto;

import com.example.chat.persistenceLayer.entity.enums.TipoConversacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ConversacionResponseDTO {

    private Integer idConversacion;

    private TipoConversacion tipo;

    private LocalDate fechaCreacion;

}
