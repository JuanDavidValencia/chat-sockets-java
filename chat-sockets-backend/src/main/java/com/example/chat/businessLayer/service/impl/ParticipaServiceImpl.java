package com.example.chat.businessLayer.service.impl;

import com.example.chat.businessLayer.service.ParticipaService;
import com.example.chat.persistenceLayer.entity.Conversacion;
import com.example.chat.persistenceLayer.entity.Participa;
import com.example.chat.persistenceLayer.repository.ParticipaRepository;
import com.example.chat.persistenceLayer.repository.UsuarioRepository;
import com.example.chat.presentationLayer.dto.ConversacionResponseDTO;
import com.example.chat.presentationLayer.dto.ParticipaRequestDTO;
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

    @Override
    public List<ConversacionResponseDTO> listarConversacionesUsuario(Integer idUsuario){

        log.info("Listando conversaciones del usuario {}.", idUsuario);

        if (!usuarioRepository.existsById(idUsuario)){
            throw new IllegalArgumentException("El usuario no existe");
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
    public void agregarParticipante(ParticipaRequestDTO participa) {

    }

    @Override
    public void eliminarParticipante(Integer idUsuario, Integer idConversacion) {

    }



}
