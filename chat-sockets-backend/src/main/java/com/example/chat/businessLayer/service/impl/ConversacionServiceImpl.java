package com.example.chat.businessLayer.service.impl;

import com.example.chat.businessLayer.businessExceptions.OperacionNoAutorizadaException;
import com.example.chat.businessLayer.service.ConversacionService;
import com.example.chat.persistenceLayer.entity.Conversacion;
import com.example.chat.persistenceLayer.entity.Participa;
import com.example.chat.persistenceLayer.entity.ParticipaId;
import com.example.chat.persistenceLayer.entity.Usuario;
import com.example.chat.persistenceLayer.entity.enums.TipoConversacion;
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
    public ConversacionResponseDTO crearConversacion(ConversacionRequestDTO conversacion, Integer idUsuario){
        log.info("Creando conversación");

        Usuario creador = usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new IllegalArgumentException("El creador no existe.")
                );

        Conversacion entidadConversacion = new Conversacion();

        entidadConversacion.setTipo(conversacion.getTipo());

        entidadConversacion.setFechaCreacion(LocalDate.now());

        entidadConversacion.setCreador(creador);

        Conversacion guardada = conversacionRepository.save(entidadConversacion);

        List<Integer> participantes = conversacion.getParticipantes();

        guardarParticipantes(participantes, idUsuario, guardada);

        return new ConversacionResponseDTO(
                guardada.getIdConversacion(),
                guardada.getTipo(),
                guardada.getFechaCreacion()
        );

    }

    @Override
    public ConversacionResponseDTO buscarConversacion(Integer id, Integer idUsuario){

        Conversacion guardada = conversacionRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("La conversación no existe.")
                );

        if (guardada.getTipo().equals(TipoConversacion.GLOBAL)){

            return new ConversacionResponseDTO(
                    guardada.getIdConversacion(),
                    guardada.getTipo(),
                    guardada.getFechaCreacion()
            );

        }

        if (!participaRepository.existsById_IdUsuarioAndId_IdConversacion(idUsuario, id)){
            throw new OperacionNoAutorizadaException("Usuario no autorizado, no pertenece a la conversación.");
        }


        return new ConversacionResponseDTO(
                guardada.getIdConversacion(),
                guardada.getTipo(),
                guardada.getFechaCreacion()
        );

    }

    @Override
    public void eliminarConversacion(Integer idConversacion, Integer idUsuario){

        Conversacion conversacion = conversacionRepository.findById(idConversacion)
                .orElseThrow(() ->
                        new IllegalArgumentException("La conversación no existe.")
                );

        if (!idUsuario.equals(conversacion.getCreador().getIdUsuario())){
            throw new OperacionNoAutorizadaException("Usuario no autorizado para eliminar la conversación.");
        }

        conversacionRepository.deleteById(idConversacion);

    }

    private void guardarParticipantes(List<Integer> participantes, Integer idUsuario, Conversacion conversacion){

        if (participantes == null || participantes.isEmpty()) {

            throw new IllegalArgumentException("Debe existir al menos un participante.");

        }

        guardarParticipacion(idUsuario, conversacion);

        for (int i = 0; i < participantes.size(); i++){

            if (!idUsuario.equals(participantes.get(i))){

                guardarParticipacion(participantes.get(i), conversacion);

            }


        }
    }

    private void guardarParticipacion(Integer id, Conversacion conversacion){

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El usuario no existe."));

        ParticipaId idParticipa = new ParticipaId(
                id,
                conversacion.getIdConversacion()
        );

        Participa participante = new Participa(
                idParticipa,
                usuario,
                conversacion
        );

        participaRepository.save((participante));


    }




}
