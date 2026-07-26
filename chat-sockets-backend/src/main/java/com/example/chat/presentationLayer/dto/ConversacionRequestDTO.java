package com.example.chat.presentationLayer.dto;

import com.example.chat.persistenceLayer.entity.enums.TipoConversacion;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConversacionRequestDTO {

    @NotNull
    private TipoConversacion tipo;

}
