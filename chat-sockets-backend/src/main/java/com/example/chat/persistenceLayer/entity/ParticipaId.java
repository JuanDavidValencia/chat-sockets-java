package com.example.chat.persistenceLayer.entity;

import java.io.Serializable;
import lombok.*;
import jakarta.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ParticipaId implements Serializable{

    private Integer idUsuario;

    private Integer idConversacion;
}
