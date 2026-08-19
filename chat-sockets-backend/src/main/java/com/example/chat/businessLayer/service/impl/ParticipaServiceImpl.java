package com.example.chat.businessLayer.service.impl;

import com.example.chat.businessLayer.businessExceptions.OperacionNoAutorizadaException;
import com.example.chat.businessLayer.service.ParticipaService;
import com.example.chat.persistenceLayer.entity.Conversacion;
import com.example.chat.persistenceLayer.entity.Participa;
import com.example.chat.persistenceLayer.entity.ParticipaId;
import com.example.chat.persistenceLayer.entity.Usuario;
import com.example.chat.persistenceLayer.repository.ConversacionRepository;
import com.example.chat.persistenceLayer.repository.ParticipaRepository;
import com.example.chat.persistenceLayer.repository.UsuarioRepository;
import com.example.chat.presentationLayer.dto.ConversacionResponseDTO;
import com.example.chat.presentationLayer.dto.ParticipaRequestDTO;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipaServiceImpl implements ParticipaService {

    private final ParticipaRepository participaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConversacionRepository conversacionRepository;

    @Override
    public List<ConversacionResponseDTO> listarConversacionesUsuario(Integer idUsuario, Integer idUsuarioAutenticado){

        log.info("Listando conversaciones del usuario {}.", idUsuario);

        if (!usuarioRepository.existsById(idUsuario)){
            throw new IllegalArgumentException("El usuario no existe");
        }

        if (!idUsuario.equals(idUsuarioAutenticado)){

            throw new OperacionNoAutorizadaException("El usuario no está autorizado para listar las conversaciones del usuario.");

        }

        List<ConversacionResponseDTO> conversaciones = new ArrayList<>();

        List<Participa> participantes = participaRepository.findByUsuarioIdUsuario(idUsuario);

        for (int i = 0; i < participantes.size(); i++){

            Conversacion conversacion = participantes.get(i).getConversacion();

            ConversacionResponseDTO participacion = new ConversacionResponseDTO(
                conversacion.getIdConversacion(),
                conversacion.getTipo(),
                conversacion.getFechaCreacion()
            );

            conversaciones.add(participacion);

        }

        return conversaciones;

    }

    @Override
    public void agregarParticipante(ParticipaRequestDTO participa, Integer idUsuarioAutenticado) {

        log.info("Agregando participación de usuario {}.", participa.getIdUsuario());

        Integer idUsuario = participa.getIdUsuario();
        Integer idConversacion = participa.getIdConversacion();

        Conversacion conversacion = conversacionRepository.findById(idConversacion)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La conversación no existe"
                        ));

        if (!conversacion.getCreador().getIdUsuario().equals(idUsuarioAutenticado)){

            throw new OperacionNoAutorizadaException("El usuario no está autorizado para agregar un participante.");

        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "El usuario no existe."
                        ));

        if (participaRepository.existsById_IdUsuarioAndId_IdConversacion(idUsuario, idConversacion)){
            throw new IllegalArgumentException("El usuario ya está en la conversación.");
        }

        ParticipaId idParticipa = new ParticipaId(idUsuario, idConversacion);

        Participa participacion = new Participa(
                idParticipa,
                usuario,
                conversacion
        );

        participaRepository.save(participacion);

    }

    @Override
    public void eliminarParticipante(Integer idUsuario, Integer idConversacion, Integer idUsuarioAutenticado) {

        log.info("Eliminando participación de usuario {}.", idUsuario);

        usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new IllegalArgumentException("El usuario no existe.")
                );

        Conversacion conversacion = conversacionRepository.findById(idConversacion)
                .orElseThrow(() ->
                        new IllegalArgumentException("La conversación no existe.")
                );

        validaciones(idUsuario, conversacion, idUsuarioAutenticado);

        ParticipaId participaId = new ParticipaId(
                idUsuario,
                idConversacion
        );

        participaRepository.deleteById(participaId);



    }

    private void validaciones(Integer idUsuario, Conversacion conversacion, Integer idUsuarioAutenticado){


        if (!participaRepository.existsById_IdUsuarioAndId_IdConversacion(idUsuario, conversacion.getIdConversacion())) {

            throw new IllegalArgumentException("El usuario no pertenece a la conversación.");

        }

        if (!participaRepository.existsById_IdUsuarioAndId_IdConversacion(idUsuarioAutenticado, conversacion.getIdConversacion())) {

            throw new IllegalArgumentException("El usuario no pertenece a la conversación.");

        }

        Integer creadorId = conversacion.getCreador().getIdUsuario();


        if (creadorId.equals(idUsuario)){

            throw new OperacionNoAutorizadaException("El creador no puede ser eliminado de la conversación");

        }

        if (idUsuario.equals(idUsuarioAutenticado)){
            return;
        }

        if (!creadorId.equals(idUsuarioAutenticado)){

            throw new OperacionNoAutorizadaException("Un participante no puede eliminar a otro participante");

        }



    }



}
