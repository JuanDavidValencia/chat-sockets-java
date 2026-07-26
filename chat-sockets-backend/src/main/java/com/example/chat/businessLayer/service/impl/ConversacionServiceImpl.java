package com.example.chat.businessLayer.service.impl;

import com.example.chat.businessLayer.service.ConversacionService;
import com.example.chat.persistenceLayer.entity.Conversacion;
import com.example.chat.presentationLayer.dto.ConversacionRequestDTO;
import com.example.chat.presentationLayer.dto.ConversacionResponseDTO;
import java.time.LocalDate;
import com.example.chat.persistenceLayer.repository.ConversacionRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

//NOTA: FALTA IMPLEMENTAR EL METODO listarConversaciones()

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversacionServiceImpl implements ConversacionService {


    private final ConversacionRepository conversacionRepository;

    @Override
    public ConversacionResponseDTO crearConversacion(ConversacionRequestDTO conversacion){
        log.info("Creando conversación");

        Conversacion entidadConversacion = new Conversacion();

        entidadConversacion.setTipo(conversacion.getTipo());

        entidadConversacion.setFechaCreacion(LocalDate.now());

        Conversacion guardada = conversacionRepository.save(entidadConversacion);

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
    public List<Conversacion> listarConversaciones(Integer idUsuario){

        return null;

    }

    @Override
    public void eliminarConversacion(Integer idConversacion){

        buscarConversacion(idConversacion);

        conversacionRepository.deleteById(idConversacion);

    }




}
