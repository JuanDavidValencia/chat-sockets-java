package com.example.chat.persistenceLayer.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Participa")
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
