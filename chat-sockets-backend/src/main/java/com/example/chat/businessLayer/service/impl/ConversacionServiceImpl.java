package com.example.chat.businessLayer.service.impl;

import com.example.chat.businessLayer.service.ConversacionService;
import com.example.chat.persistenceLayer.entity.Conversacion;
import com.example.chat.persistenceLayer.entity.Participa;
import com.example.chat.persistenceLayer.entity.ParticipaId;
import com.example.chat.persistenceLayer.entity.Usuario;
import com.example.chat.persistenceLayer.repository.ParticipaRepository;
import com.example.chat.persistenceLayer.repository.UsuarioRepository;
import com.example.chat.presentationLayer.dto.ConversacionRequestDTO;
import com.example.chat.presentationLayer.dto.ConversacionResponseDTO;
import java.time.LocalDate;
import com.example.chat.persistenceLayer.repository.ConversacionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ConversacionServiceImpl implements ConversacionService {


    private final ConversacionRepository conversacionRepository;
    private final ParticipaRepository participaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public ConversacionResponseDTO crearConversacion(ConversacionRequestDTO conversacion){
        log.info("Creando conversación");

        Conversacion entidadConversacion = new Conversacion();

        entidadConversacion.setTipo(conversacion.getTipo());

        entidadConversacion.setFechaCreacion(LocalDate.now());

        Conversacion guardada = conversacionRepository.save(entidadConversacion);

        List<Integer> participantes = conversacion.getParticipantes();

        guardarParticipantes(participantes, guardada);

        return new ConversacionResponseDTO(
                guardada.getIdConversacion(),
                guardada.getTipo(),
                guardada.getFechaCreacion()
        );

    }

    @Override
    public ConversacionResponseDTO buscarConversacion(Integer id){

        Conversacion guardada = conversacionRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("La conversación no existe.")
                );

        return new ConversacionResponseDTO(
                guardada.getIdConversacion(),
                guardada.getTipo(),
                guardada.getFechaCreacion()
        );

    }

    @Override
    public void eliminarConversacion(Integer idConversacion){

        buscarConversacion(idConversacion);

        conversacionRepository.deleteById(idConversacion);

    }

    private void guardarParticipantes(List<Integer> participantes, Conversacion conversacion){

        if (participantes == null || participantes.isEmpty()){
            throw new IllegalArgumentException("Debe existir al menos un participante.");
        }

        for (int i = 0; i < participantes.size(); i++){

            Usuario usuario = usuarioRepository.findById(participantes.get(i))
                    .orElseThrow(() ->
                            new IllegalArgumentException("El usuario no existe."));

            Integer idUsuario = participantes.get(i);

            ParticipaId id = new ParticipaId(
                    idUsuario,
                    conversacion.getIdConversacion()
            );

            Participa participante = new Participa(
                    id,
                    usuario,
                    conversacion
            );

            participaRepository.save((participante));

        }
    }




}
