package com.example.chat.businessLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.example.chat.persistenceLayer.entity.enums.TipoConversacion;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ConversacionResponseDTO {

    private Integer idConversacion;

    private TipoConversacion tipo;

    private LocalDate fechaCreacion;

}
