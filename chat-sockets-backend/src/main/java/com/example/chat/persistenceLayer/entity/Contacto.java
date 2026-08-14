package com.example.chat.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Contacto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contacto")
    private Integer idContacto;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "usuario_contacto_id")
    private Usuario contacto;

    @Column(name = "nombre_personalizado")
    private String nombrePersonalizado;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

}
