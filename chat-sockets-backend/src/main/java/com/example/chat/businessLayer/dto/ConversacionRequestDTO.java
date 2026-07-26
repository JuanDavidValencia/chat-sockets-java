package com.example.chat.businessLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.example.chat.persistenceLayer.entity.enums.TipoConversacion;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
public class ConversacionRequestDTO {

    @NotNull
    private TipoConversacion tipo;

}
