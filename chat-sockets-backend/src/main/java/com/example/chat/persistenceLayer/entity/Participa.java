package com.example.chat.persistenceLayer.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Participa")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participa {

    @EmbeddedId
    private ParticipaId id;


    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @MapsId("idUsuario")
    private Usuario usuario;


    @ManyToOne
    @JoinColumn(name = "id_conversacion")
    @MapsId("idConversacion")
    private Conversacion conversacion;

}
