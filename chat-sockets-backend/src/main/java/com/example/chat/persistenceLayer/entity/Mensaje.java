package com.example.chat.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import com.example.chat.persistenceLayer.entity.enums.EstadoMensaje;


@Entity
@Table(name = "Mensaje")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mensaje {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private Integer idMensaje;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    private LocalTime hora;

    @Column(name = "contenido")
    private String contenido;

    @ManyToOne
    @JoinColumn(name = "remitente_id")
    private Usuario remitente;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoMensaje estado;

    @ManyToOne
    @JoinColumn(name = "id_conversacion")
    private Conversacion conversacion;


}
