package com.example.chat.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.example.chat.persistenceLayer.entity.enums.TipoConversacion;

@Entity
@Table(name = "Conversacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conversacion")
    private Integer idConversacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoConversacion tipo;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @OneToMany(mappedBy = "conversacion")
    private List<Participa> participantes = new ArrayList<>();


}
